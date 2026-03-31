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

alter table customer add column cin varchar(30) default null;
alter table customer add column companyID varchar(10) references company(companyID);
alter table customer add column address varchar(50) default null;

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
alter table activityOrder add column state integer default 0 check (state >= 0);


--////////Partie chambre\\\\\\\\\


CREATE SEQUENCE roomType_seq START 1;
create table roomType(
    typeID VARCHAR(10) primary key,
    companyID varchar(10) not null references company(companyID),
    name varchar(100) not null,
    description text,
    status integer check (status >= 0)
);

CREATE SEQUENCE room_seq START 1;
create table room(
    roomID VARCHAR(10) primary key,
    typeID varchar(10) not null references roomType(typeID),
    name varchar(100) not null,
    description text,
    peoples integer default 1,
    bed integer default 1,
    status integer check (status >= 0),
    state integer default 0 check (state >= 0)
);

CREATE SEQUENCE roomPhoto_seq START 1;
create table roomPhoto(
    photoID  varchar(10) primary key,
    roomID varchar(10) not null references room(roomID),
    path varchar(500) not null,
    status integer check (status >= 0)
);

create table roomPrice(
    priceID  serial primary key,
    roomID varchar(10) not null references room(roomID),
    nightPrice NUMERIC(15, 2) not null DEFAULT 0,
    hourPrice NUMERIC(15, 2) not null DEFAULT 0,
    datechanged date default current_date,
    accountRate NUMERIC(15, 2) not null DEFAULT 0,
    status integer check (status >= 0)
);

create sequence reservation_seq start with 1;
create table reservation(
    reservationID varchar(10) primary key,
    roomID varchar(10) not null references room(roomID),
    startTime timestamp not null,
    endTime timestamp check (startTime < endTime),
    customerID varchar(10) not null references customer(customerID),
    price numeric(15,2) not null DEFAULT 0,
    accountRated float not null DEFAULT 0,
    accountPaid numeric(15,2) not null DEFAULT 0,
    AccountPaimentDeadline timestamp,
    userID varchar(10) not null references users(userID),
    state integer default 0 check (state >= 0),
    status integer check (status >= 0)
);

-- voir la disponibilité des chambres
-- select * from reservation where state in () and status=0 and starttime >= ? and endTime <= ?

-- and a.room.type.company.id = ? 

create table reservationHistory(
    historyid serial primary key,
    reservationID varchar(10) not null references reservation(reservationID),
    dateHistory timestamp not null default current_timestamp    
);
alter table reservationHistory add column state integer default 0 check (state >= 0);
alter table reservationHistory add column status integer default 0 check (status >= 0);


-- ////////////////////// RESTAURATION \\\\\\\\\\\\\\\\\\\\

create sequence table_type_seq start with 1;
create table tabletype(
    tabletypeid varchar(10) not null primary key,
    name varchar(100) not null,
    description text,
    companyID  varchar(10) not null references company(companyID),
    status integer check (status >= 0)
);

create sequence table_seq start with 1;
create table restaurant_table(
    tableID varchar(10) not null primary key,
    name varchar(100) not null,
    description text,
    tabletypeid  varchar(10) not null references tabletype(tabletypeid),
    capacity integer check (capacity >= 0),
    status integer check (status >= 0)
);

CREATE SEQUENCE tablePhoto_seq START 1;
create table tablePhoto(
    photoID  varchar(10) primary key,
    tableID varchar(10) not null references restaurant_table(tableID),
    path varchar(500) not null,
    status integer check (status >= 0)
);

create sequence tableoccupation_seq start with 1;
create table table_occupation(
    occupationID varchar(10) not null primary key,
    tableID varchar(10) not null references restaurant_table(tableID),
    customerID  varchar(10) not null references customer(customerID),
    userID varchar(10) not null references users(userID),
    startTime timestamp not null,
    endTime timestamp check (startTime < endTime),
    state integer check (state >= 0),
    status integer check (status >= 0)
);