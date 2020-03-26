
create database Museum
go

use Museum
go

use master

drop database Museum
go

create table Events(
	id_event int primary key,
	start_date date not null,
	end_date date not null
)

create table Expositions(
	id_expo int primary key,
	theme varchar(20) not null,
	description varchar(500),
	id_event int foreign key references Events(id_event)
)

create table Owners(
	name varchar(200) primary key, 
	contact varchar(200) not null

)


create table Collections(
	col_name varchar(200) primary key,
	name varchar(200) foreign key references Owners(name)
)


create table Exhibits(
	id_ex int primary key,
	year int,
	title varchar(100),
	country_of_origin varchar(100),
	id_expo int foreign key references Expositions(id_expo),
	col_name varchar(200) foreign key references Collections(col_name)
)



create table Authors(
	id_author int primary key,
	first_name varchar(20),
	last_name varchar(20),
	country_of_origin varchar(20),
	birth date,
	death date
)

create table Catalogue(
	id_ex int foreign key references Exhibits(id_ex),
	id_author int foreign key references Authors(id_author),
	description varchar(120),
	constraint pk_Catalogue primary key(id_ex, id_author)
)


create table Staff(
	id_staff int primary key,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	address varchar(100) not null,
	cnp varchar(13) not null,
	salary float
)

create table Contracts(
	id_contract int foreign key references Staff(id_staff),
	start_date date not null,
	end_date date,
	constraint pk_Contract primary key(id_contract)

)


create table Timetable(
	id_staff int foreign key references Staff(id_staff),
	id_expo int foreign key references Expositions(id_expo),
	description varchar(150) not null,
	constraint pk_Timetable primary key(id_staff, id_expo)
)

create table Guides(
	id_guide int primary key,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	id_event int foreign key references Events(id_event)
)

create table Rooms(
	id_room varchar(3) primary key,
	size float, 
	floor int,
	id_expo int foreign key references Expositions(id_expo)
)

create table Visitors(
	number int primary key,
	date_of_visit date not null,
	id_guide int foreign key references Guides(id_guide)
)