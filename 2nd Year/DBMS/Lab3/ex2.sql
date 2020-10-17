use Museum
go

-- 2. create a stored procedure that inserts data in tables that are in a m:n relationship; 
-- if an insert fails, try to recover as much as possible from the entire operation: 
-- for example, if the user wants to add a book and its authors, succeeds creating the authors, 
-- but fails with the book, the authors should remain in the database (grade 5);

-- procedures to add a new exhibit, author and entry in the catalogue
-- if an error is raised during any of the inserts, everything is rolled back 
-- to the latest savepoint


create or alter procedure testMNRelationshipSavepoints(@example int)
as
	set nocount on
	declare @trancounter int
	set @trancounter = 0
	begin try
		if @example = 1
		begin
			begin tran
				exec addToExhibits 1, 2000, 'tran1', 'Romania', 953, 'Film'
				save tran MySavepoint
				set @trancounter = @@TRANCOUNT
				exec addToAuthors 1, 'John', 'Jackson', 'USA', '12-03-1980', null
				save tran MySavepoint
				set @trancounter = @@TRANCOUNT
				exec addToCatalogue 1, 1, 'abcd'
			commit tran
		end
		if @example = 2
		begin
			begin tran
				exec addToExhibits 1, 2000, 'tran1', 'Romania', 953, 'ee'
				save tran MySavepoint
				set @trancounter = @@TRANCOUNT
				exec addToAuthors 1, 'John', 'Jackson', 'USA', '12-03-1980', null
				save tran MySavepoint
				set @trancounter = @@TRANCOUNT
				exec addToCatalogue 1, 1, 'abcd'
			commit tran
		end
		if @example = 3
		begin
			begin tran
				exec addToExhibits 1, 2000, 'tran1', 'Romania', 953, 'Film'
				save tran MySavepoint
				set @trancounter = @@TRANCOUNT
				exec addToAuthors 1, 'John', 'Jackson', 'USA', '1uy2-03-1980', null
				save tran MySavepoint
				set @trancounter = @@TRANCOUNT
				exec addToCatalogue 1, 1, 'abcd'
			commit tran
		end
		if @example = 4
		begin
			begin tran
				exec addToExhibits 1, 2000, 'tran1', 'Romania', 953, 'Film'
				save tran MySavepoint
				set @trancounter = @@TRANCOUNT
				exec addToAuthors 1, 'John', 'Jackson', 'USA', '12-03-1980', null
				save tran MySavepoint
				set @trancounter = @@TRANCOUNT
				exec addToCatalogue 2, 1, 'abcd'
			commit tran
		end
	end try
	begin catch
		if @trancounter = 0
		begin
			rollback tran
			print 'All transactions rolled back'
		end
		else
		begin
			rollback tran MySavepoint
			print error_message()
			print 'Rolled back to the latest savepoint'
			commit tran
		end
	end catch
go


select * from Exhibits
select * from Authors
select * from Catalogue

-- working
exec testMNRelationshipSavepoints 1

-- fail on first insert
exec testMNRelationshipSavepoints 2

-- fail on second insert
exec testMNRelationshipSavepoints 3

-- fail on third insert
exec testMNRelationshipSavepoints 4

delete from Catalogue where id_author = 1 and id_ex = 1
delete from Exhibits where id_ex = 1
delete from Authors where id_author = 1

