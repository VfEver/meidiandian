create database meidiandian;
create table user(
	user_id int auto_increment primary key not null,
	user_account varchar(20) not null,
	user_password varchar(20) not null,
	user_name varchar(20) not null,
	createTime datetime not null,
	user_type int not null default 0
);