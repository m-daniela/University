use Museum
go

-- phantom reads 3 - solution
-- the select does not read the inserted data until
-- 

SET TRANSACTION ISOLATION LEVEL SERIALIZABLE

begin tran
	select * from Exhibits
	waitfor delay '00:00:10'
	select * from Exhibits
commit tran
