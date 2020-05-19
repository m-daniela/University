use Museum
go

-- deadlock 2

set deadlock_priority high
begin tran
	update Owners set contact = 'success owners 2' where name = 'abc'
	waitfor delay '00:00:10'
	update Exhibits set title = 'success exhibits 2' where id_ex = 8
commit tran