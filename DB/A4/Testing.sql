use Museum
go

-- testing tables

create table EventsTest(
	id_event int primary key,
	start_date date not null,
	end_date date not null
)
delete from EventsTest
insert into EventsTest(id_event, start_date, end_date)
values(123, '2019-04-17' ,'2019-06-16'),
(155, '2019-09-08' ,'2019-09-09'),
(550, '2019-06-22' ,'2019-06-24'),
(586, '2019-01-09' ,'2019-02-17'),
(776, '2019-06-23' ,'2019-07-04'),
(375, '2019-06-05' ,'2019-09-19'),
(10, '2019-02-24' ,'2019-04-23'),
(475, '2019-08-25' ,'2019-09-11'),
--(1, '2019-05-25' ,'2019-07-12'),
(261, '2019-03-21' ,'2019-10-23')

delete from  ExpositionsTest
create table ExpositionsTest(
	id_expo int primary key,
	theme varchar(20) not null,
	description varchar(500),
	id_event int foreign key references EventsTest(id_event)
	on delete cascade
)

insert into ExpositionsTest(id_expo, theme, description, id_event)
values(900, 'History', 'World Wars', 1)

insert into ExpositionsTest(id_expo, theme, description, id_event)
values(782, 'Art', 'Art from everywhere', 261)

insert into ExpositionsTest(id_expo, theme, description, id_event)
values(953, 'War', 'Life before and after', 1)

insert into ExpositionsTest(id_expo, theme, description, id_event)
values(877, 'History', 'Evolution of different machines', 375)

insert into ExpositionsTest(id_expo, theme, description, id_event)
values(459, 'Biology', 'Glass art', 586)

insert into ExpositionsTest(id_expo, theme, description, id_event)
values(375, 'History', 'Modern istory', 261)

insert into ExpositionsTest(id_expo, theme, description, id_event)
values(644, 'Animals', 'Animals: past and modern life', 155)

insert into ExpositionsTest(id_expo, theme, description, id_event)
values(259, 'Biology', 'Art', 586)

insert into ExpositionsTest(id_expo, theme, description, id_event)
values(342, 'History', 'Postcards', 155)

create table StaffTest(
	id_staff int primary key,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	address varchar(100) not null,
	cnp varchar(13) not null,
	salary float
)

insert into StaffTest(id_staff, first_name, last_name, address, cnp, salary)
values(121, 'John', 'Doe', 'addr1', '1231321', 12000)

create table TimetableTest(
	id_staff int foreign key references StaffTest(id_staff)
	on delete cascade,
	id_expo int foreign key references ExpositionsTest(id_expo)
	on delete cascade,
	descripption varchar(150) not null,
	constraint pk_TimetableTest primary key(id_staff, id_expo)
)

drop table EventsTest
drop table ExpositionsTest
drop table StaffTest
drop table TimetableTest


-- tables

insert into Tables(Name)
values('EventsTest')
insert into Tables(Name)
values('ExpositionsTest')
insert into Tables(Name)
values('TimetableTest')
insert into Tables(Name)
values('StaffTest')


select * from Tables


-- tests

insert into Tests(Name)
values('get_view')
insert into Tests(Name)
values('insert_1pk')
insert into Tests(Name)
values('insert_1pk1fk')
insert into Tests(Name)
values('insert_2pk')
insert into Tests(Name)
values('delete_1pk')
insert into Tests(Name)
values('delete_1pk1fk')
insert into Tests(Name)
values('delete_2fk')


select * from Tests


-- test tables


insert into TestTables(TestID, TableID, NoOfRows, Position) -- insert into EventsTest 1000 lines
values(2, 1, 1000, 1)
insert into TestTables(TestID, TableID, NoOfRows, Position) -- insert into ExpositionsTest 1000 lines
values(3, 2, 1000, 2)
insert into TestTables(TestID, TableID, NoOfRows, Position) -- insert into TimetableTest 1000 lines
values(4, 3, 1000, 3)
insert into TestTables(TestID, TableID, NoOfRows, Position) -- delete from StaffTest
values(5, 4, 5, 4)
insert into TestTables(TestID, TableID, NoOfRows, Position) -- delete from ExpositionsTest
values(6, 2, 5, 5)
insert into TestTables(TestID, TableID, NoOfRows, Position) -- delete from TimetableTest
values(7, 3, 5, 6)

select * from TestTables


-- views

create or alter view ViewEvents
as
	select e.id_event, e.start_date
	from EventsTest e
	where e.end_date > '2019-06-01'
go

create or alter view ViewTimetable
as
	select ex.id_expo, ev.id_event, ex.description, ev.start_date
	from ExpositionsTest ex 
	inner join
	EventsTest ev
	on ex.id_event = ev.id_event
	where ex.theme = 'Art' or ex.theme = 'History'
go

create or alter view ViewStaff
as
	select s.id_staff, s.last_name, avg(s.salary) as salary
	from ExpositionsTest ex
	full outer join
	TimetableTest t
	on ex.id_expo = t.id_expo
	full outer join
	StaffTest s
	on s.id_staff = t.id_staff
	group by s.id_staff, s.last_name
go

insert into Views(Name)
values('ViewEvents')

insert into Views(Name)
values('ViewTimetable')

insert into Views(Name)
values('ViewStaff')

select * from Views

-- test views

insert into TestViews(TestID, ViewID)
values(1, 1)
insert into TestViews(TestID, ViewID)
values(1, 2)
insert into TestViews(TestID, ViewID)
values(1, 3)


select * from TestViews

-- the procedures for testing the views

create or alter procedure TestViewEvents
as
	select * from ViewEvents
go
create or alter procedure TestViewTimetable
as
	select * from ViewTimetable
go
create or alter procedure TestViewStaff
as
	select * from ViewStaff
go


-- test runs

insert into TestRuns(Description, StartAt, EndAt)
values('get_view', null, null)
insert into TestRuns(Description, StartAt, EndAt)
values('insert_1pk', null, null)
insert into TestRuns(Description, StartAt, EndAt)
values('insert_1pk1fk', null, null)
insert into TestRuns(Description, StartAt, EndAt)
values('insert_2pk', null, null)
insert into TestRuns(Description, StartAt, EndAt)
values('delete_1pk', null, null)
insert into TestRuns(Description, StartAt, EndAt)
values('delete_1pk1fk', null, null)
insert into TestRuns(Description, StartAt, EndAt)
values('delete_2pk', null, null)

-- the procedure to run the tests

create or alter procedure TestTheTests
@what_to_test varchar(10),
@keys varchar(10)
as
begin
	declare @proc varchar(20)
	declare @date1 Date
	declare @date2 Date

	set @proc = @what_to_test + '_' + @keys
	
	if @what_to_test = 'insert'
	begin
		
		set @date1 = CURRENT_TIMESTAMP  
		exec @proc
		set @date2 = CURRENT_TIMESTAMP  
		print(@proc)

		if @keys = '1pk'
		begin
			INSERT INTO TestRunTables(TestRunID, TableID, StartAT, EndAt) VALUES (2, 1, @date1, @date2)
			
		end
		if @keys = '1pk1fk'
		begin
			INSERT INTO TestRunTables(TestRunID, TableID, StartAT, EndAt) VALUES (3, 2, @date1, @date2)
		end
		if @keys = '2pk'
		begin
			INSERT INTO TestRunTables(TestRunID, TableID, StartAT, EndAt) VALUES (4, 3, @date1, @date2)
		end
	end

	if @what_to_test = 'delete'
	begin
		
		set @date1 = CURRENT_TIMESTAMP  
		exec @proc

		if @keys = '1pk'
		begin
			
			INSERT INTO TestRunTables(TestRunID, TableID, StartAT, EndAt) VALUES (5, 4, @date1, @date2)
		end
		if @keys = '1pk1fk'
		begin
			INSERT INTO TestRunTables(TestRunID, TableID, StartAT, EndAt) VALUES (6, 2, @date1, @date2)
		end
		if @keys = '2pk'
		begin
			INSERT INTO TestRunTables(TestRunID, TableID, StartAT, EndAt) VALUES (7, 3, @date1, @date2)
		end
	end
		

end


delete from TestRunTables


exec TestTheTests 'insert', '1pk'
exec TestTheTests 'insert', '1pk1fk'
exec TestTheTests 'insert', '2pk'
exec TestTheTests 'delete', '1pk'
exec TestTheTests 'delete', '1pk1fk'
exec TestTheTests 'delete', '2pk'


select * from TestRunTables

select * from EventsTest
select * from ExpositionsTest
select * from TimetableTest
select * from StaffTest

-- test run views

create or alter procedure RunViews
as
	declare @view1 varchar(20)
	select @view1 = Name from Views where ViewID = 1
	
	declare @date1 date
	declare @date2 date

	declare @tv1 varchar(20)
	set @tv1 = 'Test' + @view1

	set @date1 = CURRENT_TIMESTAMP  
	exec @tv1
	set @date2 = CURRENT_TIMESTAMP  

	insert into TestRunViews(TestRunID, ViewID, StartAt, EndAt) values(1, 1, @date1, @date2)

	declare @view2 varchar(20)
	select @view2 = Name from Views where ViewID = 2
	

	declare @tv2 varchar(20)
	set @tv2 = 'Test' + @view2

	set @date1 = CURRENT_TIMESTAMP  
	exec @tv2
	set @date2 = CURRENT_TIMESTAMP  

	insert into TestRunViews(TestRunID, ViewID, StartAt, EndAt) values(1, 2, @date1, @date2)

	declare @view3 varchar(20)
	select @view3 = Name from Views where ViewID = 3

	declare @tv3 varchar(20)
	set @tv3 = 'Test' + @view3

	set @date1 = CURRENT_TIMESTAMP 
	exec @tv3
	set @date2 = CURRENT_TIMESTAMP

	insert into TestRunViews(TestRunID, ViewID, StartAt, EndAt) values(1, 3, @date1, @date2)
go

exec RunViews

delete from TestRunViews

select * from TestRunTables
select * from TestRunViews
select * from TestRuns


delete from TestRunTables


exec TestTheTests 'insert', '1pk'
exec TestTheTests 'insert', '1pk1fk'
exec TestTheTests 'insert', '2pk'
exec TestTheTests 'delete', '1pk'
exec TestTheTests 'delete', '1pk1fk'
exec TestTheTests 'delete', '2pk'

select * from TestRunTables

select * from EventsTest
select * from ExpositionsTest
select * from TimetableTest
select * from StaffTest

select * from TestRuns


exec RunTheTests 1 -- views
exec RunTheTests 2 -- insert 1pk
exec RunTheTests 3 -- insert 1pk 1fk 
exec RunTheTests 4 -- insert 2pk -- will work only after 2 and 3
exec RunTheTests 5 -- delete 1pk
exec RunTheTests 6 -- delete 1pk fk
exec RunTheTests 7 -- delete 2pk


exec RunTheTests2

-- run all tests
create or alter procedure RunTheTests2

as
	-- delete the previous tests
	delete from TestRunViews
	delete from TestRunTables
	
	declare @date1 datetime
	declare @date2 datetime

	
		-- get view
		set @date1 = GETDATE()
		exec RunViews
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 1
	

		-- insert_1pk
		set @date1 = GETDATE()
		exec TestTheTests 'insert', '1pk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 2


		-- insert_1pk1fk
		set @date1 = GETDATE()
		exec TestTheTests 'insert', '1pk1fk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 3
	

		-- insert_2pk
		set @date1 = GETDATE()
		exec TestTheTests 'insert', '2pk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 4
	

		-- delete_1pk
		set @date1 = GETDATE()
		exec TestTheTests 'delete', '1pk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 5

	
		-- delete_1pk1fk
		set @date1 = GETDATE()
		exec TestTheTests 'delete', '1pk1fk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 6
	
	
		-- delete_2pk
		set @date1 = GETDATE()
		exec TestTheTests 'delete', '2pk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 7
go

-- run the given test
create or alter procedure RunTheTests
@test_number int
as
	
	declare @date1 datetime
	declare @date2 datetime

	if @test_number = 1
	begin
		-- get view
		set @date1 = GETDATE()
		exec RunViews
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 1
	end


	if @test_number = 2
	begin
		-- insert_1pk
		set @date1 = GETDATE()
		exec TestTheTests 'insert', '1pk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 2
	end

	if @test_number = 3
	begin
		-- insert_1pk1fk
		set @date1 = GETDATE()
		exec TestTheTests 'insert', '1pk1fk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 3
	end

	if @test_number = 4
	begin
		-- insert_2pk
		set @date1 = GETDATE()
		exec TestTheTests 'insert', '2pk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 4
	end

	if @test_number = 5
	begin
		-- delete_1pk
		set @date1 = GETDATE()
		exec TestTheTests 'delete', '1pk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 5
	end


	if @test_number = 6
	begin
		-- delete_1pk1fk
		set @date1 = GETDATE()
		exec TestTheTests 'delete', '1pk1fk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 6
	end

	if @test_number = 7
	begin
		-- delete_2pk
		set @date1 = GETDATE()
		exec TestTheTests 'delete', '2pk'
		set @date2 = GETDATE()

		update TestRuns
		set StartAt = @date1, EndAt = @date2
		where TestRunID = 7
	end
go