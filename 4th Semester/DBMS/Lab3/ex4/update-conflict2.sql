use Museum
go

-- update conflict 2

select * from Exhibits

set transaction isolation level snapshot
begin tran
	update Exhibits set title = 'update conflict 2' where id_ex = 9
	waitfor delay '00:00:10'
commit tran