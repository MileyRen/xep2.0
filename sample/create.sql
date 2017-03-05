create table flowGroupInfo (id int primary key auto_increment, name varchar(50) not null, intro text) DEFAULT CHARSET=utf8;
insert into flowGroupInfo (name, intro) values('无分组', '无分组');
create table flowBasicInfo (id int primary key auto_increment, name varchar(50) not null, userId int not null, groupId int default 1, auth smallint not null default 0, flowNum int, flow text) DEFAULT CHARSET=utf8;
-- auth 0 私有 1共享 2系统
create table jobInfo (id int primary key auto_increment, userId int not null, flowBasicInfoId int not null, bpmn text, processInfo text) DEFAULT CHARSET=utf8;

create table user (id int primary key auto_increment, name varchar(50) not null, password varchar(50) not null) DEFAULT CHARSET=utf8;
create table fileupload_library_folder (id int primary key auto_increment, parent_id_id int default null, create_time varchar(50), update_time varchar(50), name varchar(50), deleted smallint default 0) DEFAULT CHARSET=utf8;
create table fileupload_library (id int primary key auto_increment, user_id_id int not null unique, root_folder_id int not null unique, create_time varchar(50), update_time varchar(50), deleted smallint default 0) DEFAULT CHARSET=utf8;
create table fileupload_library_dataset (id int primary key auto_increment, folder_id_id int, dataset_id varchar(20), create_time varchar(50), update_time varchar(50), name varchar(50), user_id int, size int, type varchar(20), deleted smallint default 0) DEFAULT CHARSET=utf8;

create table tools (id int primary key auto_increment, tool_id int, tool_name varchar(50), user_id int, is_shared smallint, tool_type_id int, added_time varchar(50), is_created_by_user smallint, description varchar(512), xml_path varchar(1024)) DEFAULT CHARSET=utf8;
create table tool_types (id int primary key auto_increment, type_name varchar(50), added_user_id int, show_color varchar(20), description varchar(512)) DEFAULT CHARSET=utf8;
