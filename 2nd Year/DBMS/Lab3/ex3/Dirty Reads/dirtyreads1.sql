use Museum
go

-- dirty reads 1

select * from Exhibits

begin tran
	update Exhibits set title = 'dirty reads changed' where id_ex = 5
	waitfor delay '00:00:05'
rollback tran