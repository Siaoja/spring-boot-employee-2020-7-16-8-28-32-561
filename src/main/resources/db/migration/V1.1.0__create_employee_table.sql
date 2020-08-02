create table employee(
    id int auto_increment primary key,
    name varchar(255) not null,
    age int not null,
    gender varchar(255) not null,
    salary int not null,
    companyId int,
    foreign key(companyId) references company(id)
);