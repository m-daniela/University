use Museum
go

create or alter procedure delete_1pk
as
	declare @table int
	select top 1 @table = TableID from TestTables where TestID = 5

	if @table = 1
	begin
		delete from EventsTest where id_event <> (select top 1 id_event from EventsTest)
		
	end
	if @table = 4
	begin
		delete from StaffTest where id_staff <> (select top 1 id_staff from StaffTest)
		
	end
go

create or alter procedure delete_1pk1fk
as
	delete from EventsTest where id_event <> (select top 1 id_event from EventsTest)
	delete from TimetableTest
	delete from ExpositionsTest where id_expo <> (select top 1 id_expo from ExpositionsTest)
go


create or alter procedure delete_2pk
as
	delete from TimetableTest
go


