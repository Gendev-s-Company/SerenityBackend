--Création user avec password
--Se connecter d'abord en tant que user postgres
create database serenity;

create user serenity with password 'serenity';

GRANT ALL PRIVILEGES ON DATABASE serenity TO serenity;

\ c postgres 
ALTER DATABASE serenity OWNER TO serenity;

\ c serenity
 GRANT ALL ON SCHEMA public TO serenity;

psql - U serenity - d serenity 

-- \\\\\\\\\\\\\\\\\PARTIE UTILISATEUR/////////////////
---CREATION DE TABLE ET SEQUENCE
CREATE SEQUENCE company_seq START 1;

--COMP000001
create table company(
    companyID varchar(10) primary key,
    name varchar(100) not null,
    phone varchar(12) unique,
    mail varchar(50) unique,
    status integer check (status >= 0)
);

CREATE SEQUENCE profil_seq START 1;

--PROF000001
create table profil(
    profilID varchar(10) primary key,
    companyID varchar(10) not null references company(companyID),
    name varchar(30) not null,
    authority integer check (authority >= 0),
    status integer check (status >= 0)
);

-- si statut = 0 -> actif
--si statut =1 -> deleted
CREATE SEQUENCE users_seq START 1;

--USER000001
create table users(
    userID varchar(10) primary key,
    name varchar(100) not null,
    profilID varchar(10) not null references profil(profilID),
    phone varchar(12) unique,
    joinedDate date default current_date,
    password varchar(100) not null,
    status integer check (status >= 0)
);

create table workschedule(
    scheduleID SERIAL PRIMARY KEY,
    userID varchar(10) not null references users(userID),
    startTime timestamp not null,
    endTime timestamp check (startTime < endTime),
    status integer check (status >= 0)
);
alter table workschedule add COLUMN status integer check (status >= 0);


ALTER TABLE workschedule
ADD COLUMN color VARCHAR(7) NOT NULL DEFAULT '#2196F3';


-- \\\\\\\\\\\\\\\\\PARTIE HOTEL/////////////////
CREATE SEQUENCE activity_seq START 1;
create table activity(
    activityID varchar(10) primary key,
    companyID varchar(10) not null references company(companyID),
    name varchar(100) not null,
    description text,
    status integer check (status >= 0)
);

CREATE SEQUENCE activityPhoto_seq START 1;
create table activityPhoto(
    photoID  varchar(10) primary key,
    activityID varchar(10) not null references activity(activityID),
    path varchar(500) not null,
    status integer check (status >= 0)
);


create table activityPrice(
    priceID  serial primary key,
    activityID varchar(10) not null references activity(activityID),
    hourPrice INTEGER not null DEFAULT 0,
    price NUMERIC(15, 2) not null DEFAULT 0,
    dateChanged date default current_date,
    status integer check (status >= 0)
);

CREATE SEQUENCE customer_seq START 1;
create table customer(
    customerID VARCHAR(10) primary key,
    name varchar(100) not null,
    phone varchar(12) unique,
    mail varchar(50) unique,
    status integer check (status >= 0)
);

CREATE SEQUENCE activityOrder_seq START 1;
create table activityOrder(
    acOrderID  VARCHAR(10) primary key,
    activityID varchar(10) not null references activity(activityID),
    customerID varchar(10) not null references customer(customerID),
    price NUMERIC(15, 2) not null DEFAULT 0,
    duration INTEGER not null DEFAULT 1,
    dateOrder date default current_date,
    status integer check (status >= 0)
);

-- code à effectuer
alter table activityOrder drop column dateOrder;
alter table activityOrder add COLUMN dateOrder timestamp default current_timestamp;