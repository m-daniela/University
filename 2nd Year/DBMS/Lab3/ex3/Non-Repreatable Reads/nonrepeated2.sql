use Museum
go

-- non-repeatable reads 2
-- 

SET TRANSACTION ISOLATION LEVEL READ COMMITTED

begin tran
	select * from Exhibits
	waitfor delay '00:00:10'
	select * from Exhibits
rollback tran
