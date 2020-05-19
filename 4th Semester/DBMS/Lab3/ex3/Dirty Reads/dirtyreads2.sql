use Museum
go

-- dirty reads 2
-- transaction reads uncommitted data with the first select

select * from Exhibits

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
begin tran
	select * from Exhibits
	waitfor delay '00:00:10'
	select * from Exhibits
commit tran

