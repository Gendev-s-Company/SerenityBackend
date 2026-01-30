--Création user avec password
--Se connecter d'abord en tant que user postgres
create database serenity;
create user serenity with password 'serenity';
GRANT ALL PRIVILEGES ON DATABASE serenity TO serenity;

\c postgres
ALTER DATABASE serenity OWNER TO serenity;



\c serenity
GRANT ALL ON SCHEMA public TO serenity;

psql -U serenity -d serenity
---CREATION DE TABLE ET SEQUENCE

CREATE SEQUENCE company_seq START 1;
--COMP000001
create table company(
    companyID varchar(10) primary key,
    name varchar(100) not null,
    phone varchar(12) unique,
    mail varchar(50) unique  
);


CREATE SEQUENCE profil_seq START 1;
--PROF000001
create table profil(
    profilID varchar(10) primary key,
    companyID varchar(10) not null references company(companyID),
    name varchar(30) not null,
    authority integer check (authority > 0 )
);


CREATE SEQUENCE users_seq START 1;
--USER000001
create table users(
    userID varchar(10) primary key,
    name varchar(100) not null,
    profilID varchar(10) not null references profil(profilID),
    phone varchar(12) unique,
    joinedDate date default current_date,
    password varchar(100) not null,
    status integer check (status > 0 )
);



create table workschedule(
    scheduleID SERIAL PRIMARY KEY,
    userID varchar(10) not null references users(userID),
    startTime timestamp not null,
    endTime timestamp check (startTime < endTime)
);



