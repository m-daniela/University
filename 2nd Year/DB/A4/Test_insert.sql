use Museum
go

create or alter procedure insert_1pk
as
	declare @table int
	select top 1 @table = TableID from TestTables where TestID = 2
	select * from TestTables where TestID = 2
	declare @n int
	set @n = 0

	declare @id int
	
	declare @rows int
	select @rows = NoOfRows from TestTables where TableID = @table

	if @table = 1
	begin
		select @id = max(id_event) from EventsTest



		while @n <  @rows
		begin
			set @n = @n + 1
			set @id = @id + 1
			insert into EventsTest(id_event, start_date, end_date) values (@id, CAST(GETDATE() AS DATE), CAST(GETDATE() AS DATE))

			
		end
		print('Insert into '+ convert(varchar(3), @n) +' EventsTest')
	end
	if @table = 4
	begin
		select @id = max(id_staff) from StaffTest
		
		while @n <  @rows
		begin
			set @n = @n + 1
			set @id = @id + 1
			insert into StaffTest(id_staff, first_name, last_name, cnp, salary) values (@id, 'e', 'e', '0', 0)

		end
		print('Insert into '+ convert(varchar(3), @rows) +' StaffTest')
	end
go

exec insert_1pk

create or alter procedure insert_1pk1fk
as
	declare @table int
	select top 1 @table = TableID from TestTables where TestID = 3
	declare @n int
	set @n = 0

	declare @id1 int
	select @id1 = max(id_event) from EventsTest


	declare @rows int
	select @rows = NoOfRows from TestTables where TableID = @table

	declare @id2 int
	select @id2 = max(id_expo) from ExpositionsTest


	while @n < @rows
	begin
		set @n = @n + 1
		set @id2 = @id2 + 1
		insert into ExpositionsTest(id_expo, theme, description, id_event) values(@id2, 'e', 'e', @id1)
	end
	print('Insert into '+ convert(varchar(3), @rows) +' StaffTest')
go

exec insert_1pk1fk

create or alter procedure insert_2pk
as
	declare @table int
	select top 1 @table = TableID from TestTables where TestID = 4
	declare @n int
	set @n = 0

	declare @id1 int
	select @id1 = max(id_staff) from StaffTest
	declare @id2 int
	
	declare @rows int
	select @rows = NoOfRows from TestTables where TableID = @table


	select top (@rows) id_expo
	into Help
	from ExpositionsTest


	while @n < @rows
	begin
		set @n = @n + 1
		select top 1 @id2 = id_expo from Help
		insert into TimetableTest(id_staff, id_expo, descripption) values (@id1, @id2, 'e')
		delete from Help where id_expo = @id2
	end
	drop table Help
go

exec insert_2pk

select * from TimetableTest