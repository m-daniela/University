use Museum
go

-- phantom reads 2

SET TRANSACTION ISOLATION LEVEL REPEATABLE READ

begin tran
	select * from Exhibits
	waitfor delay '00:00:10'
	select * from Exhibits
commit tran
