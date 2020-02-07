
use Museum
go

-- Events 1/4

insert into Events(id_event, start_date, end_date)
values(123, '2019-04-17' ,'2019-06-16'),
(155, '2019-09-08' ,'2019-09-09'),
(550, '2019-06-22' ,'2019-06-24'),
(586, '2019-01-09' ,'2019-02-17'),
(776, '2019-06-23' ,'2019-07-04'),
(375, '2019-06-05' ,'2019-09-19'),
(10, '2019-02-24' ,'2019-04-23'),
(475, '2019-08-25' ,'2019-09-11'),
(1, '2019-05-25' ,'2019-07-12'),
(261, '2019-03-21' ,'2019-10-23'),
(155, '2019-06-16' ,'2019-08-10')
-- duplicate primary key

-- update 1/3

update Events
set end_date='2018-03-10'
where id_event=30

-- delete 1/2

delete
from Events 
where start_date > '2019-07-01'

-- Expositions 2/4

insert into Expositions(id_expo, theme, description, id_event)
values(900, 'History', 'World Wars', 1)

insert into Expositions(id_expo, theme, description, id_event)
values(782, 'Art', 'Art from everywhere', 261)

insert into Expositions(id_expo, theme, description, id_event)
values(953, 'War', 'Life before and after', 1)

insert into Expositions(id_expo, theme, description, id_event)
values(877, 'History', 'Evolution of different machines', 375)

insert into Expositions(id_expo, theme, description, id_event)
values(459, 'Biology', 'Glass art', 586)

insert into Expositions(id_expo, theme, description, id_event)
values(375, 'History', 'Modern istory', 261)

insert into Expositions(id_expo, theme, description, id_event)
values(644, 'Animals', 'Animals: past and modern life', 155)

insert into Expositions(id_expo, theme, description, id_event)
values(259, 'Biology', 'Art', 586)

insert into Expositions(id_expo, theme, description, id_event)
values(342, 'History', 'Postcards', 155)

-- delete 2/2

delete
from Expositions 
where theme in ('Biology', 'Animals')

update Expositions
set description = 'history'
where description like '%istory%'

-- Owners 5/4
insert into Owners(name, contact)
values('National Museum of American History', '1 844 750 3012')

insert into Owners(name, contact)
values('Harvard University', '1 617 495 9400')

insert into Owners(name, contact)
values('MoMA', '1 212 708 9400')



-- Collections 3/4

insert into Collections(col_name, name)
values('Surrealism', 'Museum of Modern Art')

insert into Collections(col_name, name)
values('Blaschka Glass Invertebrates', 'Harvard University')

insert into Collections(col_name, name)
values('Painting', 'useum of Modern Art')

insert into Collections(col_name, name)
values('Adding machines', 'National Museum of American History')

insert into Collections(col_name, name)
values('Glass Flowers', 'Harvard University')

insert into Collections(col_name, name)
values('The Rockefeller Beetles', 'Harvard University')

insert into Collections(col_name, name)
values('California Mission Postcards', 'National Museum of American History')

insert into Collections(col_name, name)
values('Zoology', 'Harvard University')

alter table Collections
alter column name varchar(200) not null

insert into Collections(col_name, name)
values('Film', NULL)

-- update 2/3
update Owners 
set contact = '213762432'
where name = 'MoMA' or name = 'Museum of Modern Art'
select *
from Owners


-- Exhibits 4/4

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values (122, 1953, 'Golconda', 'Belgium', 782, 'Surrealism')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(10, 1931, 'The Peristence of Memory', 'Spain', 782, 'Surrealism')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(301, 2018, 'Cenozoic Mammals', NULL, 139, 'Zoology')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(34, 1880, 'Glass Nevertebrates', 'Germany', 459, 'Blaschka Glass Invertebrates')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(222, 1798, 'Mission San Luis Rey de Francia', 'USA', 342, 'California Mission Postcards')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(223, 1817, 'Mission San Rafael, Arcangel', 'USA', 342, 'California Mission Postcards')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(224, 1797, 'Mission San Jose de Guadalupe, San Jose', 'USA', 342, 'California Mission Postcards')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(225, NULL, 'San Diego Mission Palm, Serra Monument in background, Old Town, San Diego', 'USA', 342, 'California Mission Postcards')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(226, 1798, 'San Louis Rey Mission near Oceanside', 'USA', 342, 'California Mission Postcards')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(227, 1772, 'San Luis Obispo Mission San Luis Obispo, ', 'USA', 342, 'California Mission Postcards')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(201, 1920, 'Felt & Tarrant Comptometer', 'USA', 877, 'Adding machines')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(202, 1919, 'Victor 1800', 'USA', 877, 'Adding machines')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(203, 1954, 'Resulta bs7', 'Germany', 877, 'Adding machines')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(420, NULL, 'Blue Flag iris ', 'Germany', 459, 'Blaschka Glass Invertebrates')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(421, NULL, 'Lily', 'Germany', 459, 'Blaschka Glass Invertebrates')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(422, NULL, 'Echinocereus engelmannii', 'Germany', 459, 'Blaschka Glass Invertebrates')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(423, NULL, 'Panicum boreale', 'Germany', 459, 'Blaschka Glass Invertebrates')

insert into Exhibits(id_ex, year, title, country_of_origin, id_expo, col_name)
values(424, NULL, 'Nepenthes Sanguinea', 'Germany', 459, 'Blaschka Glass Invertebrates')

-- delete 3/2

-- delete all artworks with year btw 1750 and 1799
delete from Exhibits
where year between 1750 and 1799
select *
from Exhibits

-- delete all artworks with country of origin 5 letters long
delete from Exhibits
where country_of_origin like '_____'

-- delete all artworks that can be dated
delete from Exhibits
where year is not null

-- update 3/3
update Exhibits
set title = 'Victor'
where title like '%1800%'

update Exhibits
set title = 'The Persistence of Memory'
where title like '%ence%'

-- redoing the tables

select *
from Exhibits

delete
from Events

delete
from Expositions

delete 
from Collections

delete
from Exhibits

-- a. Expositions with the biology theme or the event id's greater than 500
-- sorted in ascending order by theme

select *
from Expositions Ex
where Ex.theme = 'Biology'
union -- or id_event > 500
select *
from Expositions Ex
where Ex.id_event > 500
order by Ex.theme

-- Show the name and contact info of the owners that have the name 'Harvard University'
-- or the contact info '1 844 750 3012'

select C.name, C.contact
from Owners C 
where C.name = 'Harvard University' -- or contact = '1 844 750 3012'
union 
select C2.name, C2.contact
from Owners C2
where C2.contact = '1 844 750 3012'



-- b. Show the events that start on 2019-06-06 and end on 2019-12-30
select *, datediff(day, E.start_date, E.end_date) as period
from Events E
where E.start_date > '2019-06-06'
intersect
select *, datediff(day, E2.start_date, E2.end_date) as period
from Events E2
where E2.end_date < '2019-12-30' 


-- Show everything from Expositions where the event ID is greater than
-- 1 and the description contains the word 'War'
select *
from Expositions
where id_event >= 1
intersect
select *
from Expositions
where description like '%War%'

-- c. Except

-- Show the event ids that start after 2019-06-06 and do not end before 2019-01-30

select E.id_event
from Events E
where E.start_date > '2019-06-06'
except
select E2.id_event
from Events E2
where E2.end_date <= '2019-01-30' 

-- Show the expositions wich have the event ID greater than 100
-- but do not have 'Art' in their description
select *
from Expositions
where id_event >= 100
except
select *
from Expositions
where description like '%Art%'

-- d. 

-- Show the exhibits and epositions that are from Germany and end before 2019-12-12

select *
from Exhibits e
inner join Expositions ex
--on e.id_expo = ex.id_expo 
on e.country_of_origin like '%Germany%' 
inner join Events ev 
on ev.end_date < '2019-12-12'

-- show the owners and collections that have the same owner's name
select * 
from Owners o 
left outer join 
Collections c
on o.name = c.name

-- Show the exhibits and colletions that are from USA or Spain

select * 
from Exhibits e 
right outer join 
Collections c
on e.country_of_origin in ('USA', 'Spain') and e.col_name = c.col_name

-- the exhibit in the catalogue is part of the exposition cured by a staff member with his timetable
select distinct *
from Catalogue c
full outer join
Exhibits e
on c.id_ex = e.id_ex 
full outer join
Expositions ex
on ex.id_expo = e.id_expo
full outer join
Timetable t
on t.id_expo = ex.id_expo
full outer join
Staff s
on s.id_staff = t.id_staff

-- e.

-- Show the descriptions of the expositions that have the themes 'War' or 'Art'

select e.description
from Expositions e
where e.theme in(
	select e2.theme
	from Expositions e2
	where e2.theme in('War', 'Art')
)


-- Show the exhibits which are part of the collections that have the names of the owners longer than 25 characters
select *
from Exhibits ex
where ex.col_name in (
	select c.col_name
	from Collections c
	where c.name in(
		select o.name
		from Owners o
		where len(o.name) > 25
	)
)

-- f. 

-- Show the exhibits which were created before 1800 and the expositions
-- that they are part of have their event id lower than 200
select *
from Exhibits e
where e.year < 1800 and exists(
	select ex.id_expo
	from Expositions ex
	where ex.id_event in(
		select ev.id_event
		from Events ev
		where ev.id_event <= 200
	)
)
order by e.year desc


-- select the different collections that have the name longer
-- than 13 characters and their owners have exhibits displayed
select distinct *
from Collections c
where len(c.col_name) > 13 and exists(
	select o.name
	from Owners o
	where exists(
		select ex.col_name
		from Exhibits ex
	)
)

-- g.

-- Show the top 5 titles of the exhibits that are either 
-- from USA or Spain

select top 5 t.title
from (
select *
from Exhibits e
where e.country_of_origin in ('USA', 'Spain')
) t

-- Show the title and collection name of the exhibitions 
-- that have their collection name longer than 20 characters

select t.title, t.col_name
from(
	select *
	from Exhibits ex
	where ex.col_name in (
		select c.col_name
		from Collections c
		where len(c.name) < 20
	)
) t

-- h.

-- Show the maximum id, theme and description of the expositions
-- that contain the letter 'e', grouped by theme and description

select max(e.id_event) as 'Event id', e.theme, e.description
from Expositions e
group by e.theme, e.description
having e.description like '%e%'


-- Show the average salary, last name and the raise of each staff member
-- that have their average salary greater than 1000, grouped by last name and raise
select avg(s.salary) as average, s.last_name, (s.salary + 0.05 * salary) as raise
from Staff s
group by s.last_name, (s.salary + 0.05 * salary)
having avg(s.salary) > 1000


-- Show the name of the collectioneers' name and their 
-- apparitions if it is greater than 3 and grouped by name
select c.name, count(c.col_name) as apparitions
from Collections c
group by c.name
having count(c.col_name) > 3

-- Show the top 10 staff members' last, first name, id, max salary and the raise
-- with the minimum salary lower than 23320, grouped by last, first name, id and raise

select top 10 s.last_name, s.first_name, s.id_staff, max(s.salary) as salary, (s.salary + 0.1*salary) as raise
from Staff s
group by s.last_name, s.first_name, s.id_staff, (s.salary + 0.1*salary)
having min(s.salary) <= 23320

-- Show the floor, sum of sizes, remaining size of the rooms that have 
-- the sum of sized greater than 200 and grouped by floor and remaining size

select r.floor, sum(r.size) as size, (r.size - 10) / 4 as remaining
from Rooms r
group by r.floor, (r.size - 10) / 4
having sum(r.size) > 200

-- i.

-- Show the expositions id, description, theme of the expositions 
-- that have all their id's greater than the event ids that are
-- equal to the event id's that contain the expositions

select ex.id_expo, ex.description, ex.theme
from Expositions ex
where ex.id_event > all(
	select ev.id_event
	from Events ev
	where ev.id_event = ex.id_event
)

select ex.id_expo, ex.description, ex.theme
from Expositions ex
where ex.id_event > (
	select max(ev.id_event)
	from Events ev
	where ev.id_event = ex.id_event
)

-- Show the title, year, time error of the exhibits that
-- have at least a record is lower than the minimum year existing

select e.title, e.year, (e.year + 4) as time_error
from Exhibits e
where e.year < any(
	select e2.year
	from Exhibits e2
	where e2.year = e.year
)

select e.title, e.year, (e.year + 4) as time_error
from Exhibits e
where e.year< (
	select min(e2.year)
	from Exhibits e2
	where e2.year = e.year
)

-- Show the owners the do not have their name different from each collections' owners

select *
from Owners o
where o.name <>all(
	select c.name
	from Collections c
	where c.name != o.name
)

select *
from Owners o
where o.name not in(
	select c.name
	from Collections c
	where c.name != o.name
)

-- SHow the country of origin of the exhibits that have their expositions id similar to
-- any exposition id that do not have the event id greater than 400, ordered by country of origin
select distinct e.country_of_origin
from Exhibits e
where e.id_expo = any(
	select ex.id_expo
	from Expositions ex
	where ex.id_event <>all(
		select ev.id_event
		from Events ev
		where ev.id_event > 400
	)
)
order by e.country_of_origin

select distinct e.country_of_origin
from Exhibits e
where e.id_expo in (
	select ex.id_expo
	from Expositions ex
	where ex.id_event <>all(
		select ev.id_event
		from Events ev
		where ev.id_event > 400
	)
)
order by e.country_of_origin