use Museum
go

-- non-repeatable reads 1

select * from Exhibits

insert into Exhibits values(6, 1957, 'non-repeatable test', 'Romania', 782, 'Surrealism')
begin tran
	waitfor delay '00:00:05'
	update Exhibits set title = 'non-repeatable changed' where id_ex = 6
commit tran

delete from Exhibits where id_ex = 6