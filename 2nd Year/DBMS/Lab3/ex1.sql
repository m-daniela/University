use Museum
go

-- 1. create a stored procedure that inserts data in tables that are in a m:n relationship; 
-- if one insert fails, all the operations performed by the procedure must be rolled back (grade 3);

-- procedures to add a new exhibit, author and entry in the catalogue
-- if an error is raised during any of the inserts, everything is rolled back and the new
-- data is not saved in the tables (there are no savepoints)

create or alter procedure addToExhibits(@id_ex int, @year int, @title varchar(100), 
@country_of_origin varchar(100), @id_expo int, @col_name varchar(200))
as
	set nocount on
	if exists (select * from Exhibits where id_ex = @id_ex)
	begin
		RAISERROR('Exhibit with this id already existing', 5, 1)
	end
	if @year < 1
	begin
		RAISERROR('Invalid year', 5, 1)
	end
	if not exists (select * from Collections where col_name = @col_name)
	begin
		RAISERROR('Collection name not found', 5, 1)
	end
	if not exists (select * from Expositions where id_expo = @id_expo)
	begin
		RAISERROR('Exposition id not found', 5, 1)
	end
	insert into Exhibits values(@id_ex, @year, @title, @country_of_origin, 
	@id_expo, @col_name)
	print 'Exhibits: exhibit added'
go


create or alter procedure addToAuthors(@id_author int, @first_name varchar(20), 
@last_name varchar(20), @country_of_origin varchar(100), @birth date, @death date)
as
	set nocount on
	if exists (select * from Authors where id_author = @id_author)
	begin
		RAISERROR('Author with this id already existing', 5, 1)
	end
	insert into Authors values (@id_author, @first_name, @last_name, 
	@country_of_origin, @birth, @death)
	print 'Authors: author added'
go

create or alter procedure addToCatalogue(@id_ex int, @id_author int, @description varchar(120))
as
	set nocount on
	if exists (select * from Catalogue where id_author = @id_author) and 
		exists (select * from Catalogue where id_ex = @id_ex)
			begin
				RAISERROR('Entry already existing', 5, 1)
			end
	if not exists (select * from Exhibits where id_ex = @id_ex)
		begin
			RAISERROR('Exhibit not found', 5, 1)
		end
	if not exists (select * from Authors where id_author = @id_author)
		begin
			RAISERROR('Author not found', 5, 1)
		end
		
	insert into Catalogue values(@id_ex, @id_author, @description)
	print 'Catalogue: entry added'
go


create or alter procedure testMNRelationship(@example int)
as
	set nocount on
	begin try
		if @example = 1
		begin
			begin tran
				exec addToExhibits 1, 2000, 'tran1', 'Romania', 953, 'Film'
				exec addToAuthors 1, 'John', 'Jackson', 'USA', '12-03-1980', null
				exec addToCatalogue 1, 1, 'abcd'
			commit tran
		end
		if @example = 2
		begin
			begin tran
				exec addToExhibits 1, 2000, 'tran1', 'Romania', 953, 'ee'
				exec addToAuthors 1, 'John', 'Jackson', 'USA', '12-03-1980', null
				exec addToCatalogue 1, 1, 'abcd'
			commit tran
		end
		if @example = 3
		begin
			begin tran
				exec addToExhibits 1, 2000, 'tran1', 'Romania', 953, 'Film'
				exec addToAuthors 1, 'John', 'Jackson', 'USA', '1uy2-03-1980', null
				exec addToCatalogue 1, 1, 'abcd'
			commit tran
		end
		if @example = 4
		begin
			begin tran
				exec addToExhibits 1, 2000, 'tran1', 'Romania', 953, 'Film'
				exec addToAuthors 1, 'John', 'Jackson', 'USA', '12-03-1980', null
				exec addToCatalogue 2, 1, 'abcd'
			commit tran
		end
	end try
	begin catch
		rollback tran
		print error_message()
		print 'All transactions rolled back'
	end catch
go


select * from Exhibits
select * from Authors
select * from Catalogue

-- working
exec testMNRelationship 1

-- fail on first insert
exec testMNRelationship 2

-- fail on second insert
exec testMNRelationship 3

-- fail on last insert
exec testMNRelationship 4

delete from Catalogue where id_author = 1 and id_ex = 1
delete from Exhibits where id_ex = 1
delete from Authors where id_author = 1

