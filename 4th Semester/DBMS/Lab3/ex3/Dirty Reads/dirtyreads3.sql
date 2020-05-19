use Museum
go

-- dirty reads 3 - solution
-- the title of the exhibit with id 5 will not appear changed because
-- isolation level is set to read committed, so the transaction only
-- reads committed data

select * from Exhibits

SET TRANSACTION ISOLATION LEVEL READ COMMITTED

begin tran
	select * from Exhibits
	waitfor delay '00:00:10'
	select * from Exhibits
commit tran

