use Museum
go

-- update conflict 1

ALTER DATABASE Museum SET ALLOW_SNAPSHOT_ISOLATION ON;
GO

select * from Exhibits
update Exhibits set title = 'update conflict test' where id_ex = 9

set transaction isolation level snapshot
begin tran
	update Exhibits set title = 'update conflict 1' where id_ex = 9
	waitfor delay '00:00:05'
commit tran