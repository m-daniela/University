use Museum
go

-- phantom reads 1

select * from Exhibits


begin tran
	waitfor delay '00:00:05'
	insert into Exhibits values(7, 1958, 'phantom reads test', 'Romania', 782, 'Surrealism')
commit tran

delete from Exhibits where id_ex = 7