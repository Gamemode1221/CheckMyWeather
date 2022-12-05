create database weatherdb;

drop table member;

create table member(
	id varchar(50) primary key,
    pwd varchar(50) not null,
    mDate date not null
);

insert into member values('admin', '1234', '2022-12-01');

select * from member;

drop table weather;

create table weather(
	id varchar(50) primary key,
    location varchar(50),
    weather varchar(50),
    temperature varchar(50),
    wDate date not null
);

select * from weather;

-- insert into weather values('admin', '1234', 'location test', 'weather test', 'temperature test', '2022-12-01');