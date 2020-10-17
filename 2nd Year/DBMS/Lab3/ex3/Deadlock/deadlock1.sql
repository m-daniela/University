use Museum
go

-- deadlock 1
-- 2 transactions locking the same resource -> deadlock and
-- one of them will be chosen as victim

select * from Exhibits
select * from Owners


update Owners set contact = 'deadlock test owners' where name = 'abc'
update Exhibits set title = 'deadlock test exhibits' where id_ex = 8

begin tran
	update Exhibits set title = 'success exhibits 1' where id_ex = 8
	waitfor delay '00:00:10'
	update Owners set contact = 'success owners 1' where name = 'abc'
commit tran

