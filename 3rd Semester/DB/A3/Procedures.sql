
use Museum
go

-- a. modify the type of a column;

create or alter procedure V1
as
begin
	alter table Authors
	alter column last_name varchar(50)
	print('V1 - modify the type of a column')
end
go
exec V1;

create or alter procedure RV1
as
begin
	alter table Authors
	alter column last_name varchar(20)
	print('RV1 - modify back the type of a column')
end
go
exec RV1

-- b. add / remove a column;

create or alter procedure V2
as
begin
	alter table Authors
	add min_age int
	print('V2 - add a column')
end
go
exec V2

create or alter procedure RV2
as
begin
	alter table Authors
	drop column min_age
	print('RV2 - remove a column')
end
go
exec RV2

-- c. add / remove a DEFAULT constraint;

create or alter procedure V3
as
begin
	alter table Authors
	add constraint df_5 default 5 for min_age
	print('V3 - add default constraint')
end
go

create or alter procedure RV3
as
begin
	
	alter table Authors
	drop constraint df_5
	print('RV3 - remove default constraint')
end
go

-- d. add / remove a primary key;

create or alter procedure V4
as
begin
	create table Authors2(
		id2 int not null, 
		origin varchar(30) not null
	)
	alter table Authors2
	add constraint pk_Authors2 primary key (id2, origin)
	print('V4 - add primary key')
end
go

create or alter procedure RV4
as
begin
	alter table Authors2
	drop constraint pk_Authors2
	drop table Authors2
	print('RV4 - remove primary key')
end
go

-- e. add / remove a candidate key;

create or alter procedure V5
as
begin
	alter table Authors
	add constraint uni_Authors unique(id_author, country_of_origin)
	print('V5 - add candidate key')
end
go

create or alter procedure RV5
as
begin
	alter table Authors
	drop constraint uni_Authors
	print('RV5 - remove candidate key')
end
go

-- f. add / remove a foreign key;

create or alter procedure V6
as
begin
	alter table Authors
	add fk_event int not null

	alter table Authors
	add constraint FK_Authors_Events foreign key (fk_event) references Events(id_event)
	print('V6 - add foreign key')
end
go

select *
from Authors

create or alter procedure RV6
as
begin
	alter table Authors
	drop constraint FK_Authors_Events
	print('RV6 - remove foreign key')

	alter table Authors
	drop column fk_event
end
go
-- g. create / remove a table.

create or alter procedure V7
as
begin
	create table Contracts2(
		id_contract int primary key,
		start_date date not null,
		end_date date
	)
	print('V7 - create table')
end
go
create or alter procedure RV7
as
begin
	drop table Contracts2
	print('RV7 - remove table')
end
go


create table Versions(
	vid int primary key,
	no_ver int not null
)

insert into Versions
values(0, 0)

drop table Versions



-- version: current version (given as param)
-- vfrom: version from which we do the changes

create or alter procedure main
	@version int 
as
begin
	declare @vfrom int

	set @vfrom = (select V.no_ver from Versions V)
	declare @do varchar(50)
	if @version <= 7 and @version >= 0
		if @version > @vfrom
		begin
			while @version > @vfrom
			begin
				set @vfrom = @vfrom + 1
				set @do = 'V' + cast(@vfrom as varchar(2))
				exec @do
			end
		end

		else
			while @version < @vfrom
			begin
				if @vfrom != 0
				begin
					set @do = 'RV' + cast(@vfrom as varchar(2))
					exec @do
				end
				set @vfrom = @vfrom - 1
			end

		else
		begin
			print('Version out of bounds')
			return
		end

		update Versions
		set no_ver = @version
		
end
go

exec main 0;
select * 
from Versions