use dmdw;
create table apriori(transactionID varchar(10) , itemID varchar(10));
select * from apriori;

select distinct itemId from apriori;

select 
	transactionId , 
	count(itemID) count ,
    json_arrayagg(itemID) as items
from apriori group by transactionID;

-- frequency table
select itemId , count(itemId) frequency from apriori group by itemId;

select itemID  as t1 from apriori where transactionID = "t1";

-- possible_association
drop function If exists possibleAssociationByTransactionID;
DELIMITER //
CREATE FUNCTION possibleAssociationByTransactionID(param_data varchar(10)) RETURNS varchar(100)
reads sql data
BEGIN
	RETURN coalesce ((
		with items as (select itemID from apriori where transactionID = param_data)
		select group_concat("(",a.itemID ,",", b.itemID,")") as possible_association from items a 
		inner join items b 
		where a.itemID = (select itemID from apriori where transactionID = param_data order by itemID limit 1) and a.itemID != b.itemID
    ), "");
END; //
DELIMITER ;
select
	transactionID ,
    possibleAssociationByTransactionID(transactionID) as possible_association
from apriori group by transactionID;
drop function possibleAssociationByTransactionID;

select transactionID , possibleAssociationByTransactionID(transactionID) as possible_association from apriori group by transactionID limit 1;


select distinct transactionID from apriori ;
select funAsis("t1") , funAsis("t2");

with items as (select itemID from apriori where transactionID = "t3")
select group_concat(a.itemID ,",", b.itemID) as possible_association from items a 
inner join items b 
where a.itemID = (select itemID from apriori where transactionID = "t3" limit 1) and a.itemID != b.itemID;

with items as (select itemID from apriori where transactionID = "t6")
select * from items a 
inner join items b 
where a.itemID = (select itemID from apriori where transactionID = "t6" limit 1) and a.itemID != b.itemID;



with tmp as (select distinct itemID from apriori order by itemID asc)
select group_concat("(" , a.itemID , b.itemID , ")") all_assotiation from tmp as a
inner join tmp as b
where a.itemID != b.itemID;


--  a , b 
select a.transactionID , a.itemID a , b.itemID b from apriori a
inner join
apriori b where a.transactionID = b.transactionID and a.itemID != b.itemID;

with tmp as (select a.transactionID , a.itemID a , b.itemID b from apriori a
inner join
apriori b where a.transactionID = b.transactionID and a.itemID != b.itemID
)
select * from tmp ;
select transactionID , concat(concat(a , b) ,"+", concat(b,a)) from tmp;

select transactionID from apriori where itemID = "a";
select transactionID from apriori where itemID = "b";

with a as (select transactionID from apriori where itemID = "b") , 
b as (select transactionID from apriori where itemID = "d") 
select 
	count(a.transactionID) frequency
from a
inner join b
where a.transactionID = b.transactionID;

select count( distinct transactionID) count from apriori;
