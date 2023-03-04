SELECT * FROM dmdw.weather;

-- frequency table
select
	weather,
    sum(case when play = "yes" then 1 else 0 end) as yes,
    sum(case when play = "no" then 1 else 0 end) as no
from weather
group by weather;

with frequency_table as (
	select
		weather,
		sum(case when play = "yes" then 1 else 0 end) as yes,
		sum(case when play = "no" then 1 else 0 end) as no
	from weather
	group by weather
)
select 
	weather , yes , no ,
    yes / sum(yes) over (partition by "") as  `weather/yes`,
    no / sum(no) over (partition by "") as `weather/no`
from frequency_table;

--  if weather is sunny then player should play or not ?

-- P(Yes|Sunny)= P(Sunny|Yes)*P(Yes)/P(Sunny)
-- P(Sunny|Yes)= total_sunny_yes / total_yes
--  P(Yes)= total_yes/total_yes+total_no

-- P(No|Sunny)= P(Sunny|No)*P(No)/P(Sunny)

set @current_weather = "rainy";
with frequency_likelihood_table as (
	select 
		weather , yes , no ,
		yes / sum(yes) over (partition by "") as  `p(weather/yes)` ,
		no / sum(no) over (partition by "") as `p(weather/no)`,
        (yes+no) / (sum(yes) over (partition by "") + sum(no) over (partition by "")) as `p(weather)`,
        (sum(yes) over (partition by "") ) / (sum(yes) over (partition by "") + sum(no) over (partition by "")) as `p(yes)`,
        (sum(no) over (partition by "") ) / (sum(yes) over (partition by "") + sum(no) over (partition by "")) as `p(no)`
	from (
		select
			weather,
			sum(case when play = "yes" then 1 else 0 end) as yes,
			sum(case when play = "no" then 1 else 0 end) as no
		from weather
		group by weather
	) as frequency_table
)
-- select * from frequency_likelihood_table where weather = @current_weather;
select
	(`p(weather/yes)` * `p(yes)`)/`p(weather)` as `p(yes/weather)` , 
	(`p(weather/no)` * `p(no)`)/`p(weather)` as `p(no/weather)` ,
	case 
		when 
			(`p(weather/yes)` * `p(yes)`)/`p(weather)` > 
			(`p(weather/no)` * `p(no)`)/`p(weather)`
        then 
			"player can play the game"
		else
			"player cannot play the game"
    end as desicion
from frequency_likelihood_table
where weather = @current_weather;

-- select @current_weather;
-- select * from frequency_likelihood_table where weather = @current_weather;
-- select 
-- 	(`p(weather/yes)` * `p(yes)`)/`p(weather)` as `p(yes/weather)` , 
-- 	(`p(weather/no)` * `p(no)`)/`p(weather)` as `p(no/weather)`
-- from frequency_likelihood_table
-- where weather = @current_weather;
