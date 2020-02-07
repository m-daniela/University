use Museum
go


create table Ta(
	aid int primary key, -- clustered, created automatically
	a2 int unique, -- non clustered, created automatically
	field1 varchar(5)
)

create table Tb(
	bid int primary key,
	b2 int,
	field2 varchar(5)
)

create table Tc(
	cid int primary key,
	aid int foreign key references Ta(aid),
	bid int foreign key references Tb(bid),
	field3 varchar(5)
)

select * from Ta
select * from Tb
select * from Tc

drop table Ta
drop table Tb
drop table Tc

insert into Ta(aid, a2, field1)
values(1, 1, 'a')
insert into Ta(aid, a2, field1)
values(2, 3, 'b')
insert into Ta(aid, a2, field1)
values(3, 5, 'c')
insert into Ta(aid, a2, field1)
values(4, 10, 'd')

insert into Tb(bid, b2, field2)
values(11, 15, 'x')
insert into Tb(bid, b2, field2)
values(21, 3, 'f')
insert into Tb(bid, b2, field2)
values(23, 15, 'a')
insert into Tb(bid, b2, field2)
values(4, 10, 'd')
insert into Tb(bid, b2, field2)
values(6, 10, 'a')
insert into Tb(bid, b2, field2)
values(10, 15, 'H')

insert into Tc(cid, aid, bid, field3)
values(40, 1, 23, 'v')
insert into Tc(cid, aid, bid, field3)
values(14, 2, 10, 'q')
insert into Tc(cid, aid, bid, field3)
values(19, 4, 11, 'l')
insert into Tc(cid, aid, bid, field3)
values(33, 3, 23, '3')
insert into Tc(cid, aid, bid, field3)
values(2, 2, 21, 'km')
insert into Tc(cid, aid, bid, field3)
values(15, 3, 4, 'f')


-- index scan - all rows 
-- index seek - only the rows that meet certain qualifications

-- a. on table Ta:

-- clustered index scan;

select * from Ta order by aid

-- clustered index seek;

select * 
from Ta 
where aid > 40

-- nonclustered index scan;
-- a2 unique -> non clustered index
select * from Ta order by a2

-- nonclustered index seek;

select a2 from Ta where a2 > 5

-- key lookup.

select * from Ta order by a2

-- b. 
-- Write a query on table Tb with a WHERE clause of the form WHERE 
-- b2 = value and analyze its execution plan. Create a nonclustered 
-- index that can speed up the query. Recheck the query’s execution plan 
-- (operators, SELECT’s estimated subtree cost). 


-- list the index plan

set nocount on;
go
set showplan_all on;
go
select b2
from Tb
where b2 = 15



-- with non clustered index on b2

if exists (select name from sys.indexes where name = 'non_idx_b2')
	drop index non_idx_b2 on Tb;
go

create nonclustered index non_idx_b2 on Tb(b2);
go

set showplan_all off;
go

-- c. Create a view that joins at least 2 tables. Check whether existing
-- indexes are helpful; if not, reassess existing indexes / examine the 
-- cardinality of the tables. 


create or alter view TABC
as
	select b.b2 
	from Ta a
	inner join 
	Tb b on a.aid = b.bid
	inner join 
	Tc c on c.bid = b.bid
go


select * from TABC
