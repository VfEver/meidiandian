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
####商品表
####店铺表
create table store (
	store_id int auto_increment primary key not null,
	user_id int,
	store_name varchar(30),
	store_hours varchar(20),
	store_address varchar(50),
	cost double default 0.0
)
