use Museum
go

-- non-repeatable reads 3 - solution

SET TRANSACTION ISOLATION LEVEL REPEATABLE READ

begin tran
	select * from Exhibits
	waitfor delay '00:00:10'
	select * from Exhibits
commit tran
