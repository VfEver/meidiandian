create database meidiandian;
use meidiandian;

####用户表
create table user (
	user_id int auto_increment primary key not null,
	user_count varchar(20) not null,
	user_password varchar(20) not null,
	user_name varchar(20) not null,
	user_address varchar(50),
	user_type int not null default 0
)
####
