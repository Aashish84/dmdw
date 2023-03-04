SELECT * FROM dmdw.classification;

select
age,buyer,
count(buyer)
from classification group by age , buyer;


select
	age ,
    sum(case when buyer = "yes" then 1 else 0 end) as buyer,
	sum( case when buyer= "no" then 1 else 0 end) as non_buyer,
    count(buyer) total_record
from classification 
group by age;


with count_summary as (
	select
		age ,
		sum(case when buyer = "yes" then 1 else 0 end) as buyer,
		sum( case when buyer= "no" then 1 else 0 end) as non_buyer,
		count(buyer) total_record
	from classification 
	group by age
)
select 
	age ,
    -((buyer/total_record  * log10(buyer / total_record)) + (non_buyer/total_record * log10(non_buyer / total_record))) epilogi
from count_summary;
