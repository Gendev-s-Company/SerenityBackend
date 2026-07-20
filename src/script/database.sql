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

-- //////PLAT RESTO\\\\\
create sequence dishType_seq start with 1;
create table dishType(
    typeID varchar(10) not null primary key,
    name varchar(100) not null,
    description text,
    companyID varchar(10) not null references company(companyID),
    status integer check (status >= 0)
);

create sequence dish_seq start with 1;
create table dish(
    dishID varchar(10) not null primary key,
    name varchar(100) not null,
    description text,
    typeID varchar(10) not null references dishType(typeID),
    state integer check (state >= 0),
    status integer check (status >= 0)
);


create table dishPhoto(
    photoID  serial primary key,
    dishID varchar(10) not null references dish(dishID),
    path varchar(500) not null,
    status integer check (status >= 0)
);

create table dishPrice(
    priceID  serial primary key,
    dishID varchar(10) not null references dish(dishID),
    price NUMERIC(15, 2) not null DEFAULT 0,
    dateChanged date default current_date,
    status integer check (status >= 0)
);

create sequence dishOrder_seq start with 1;
create table dishOrder(
    orderID varchar(10) not null primary key,
    tableOccupationID varchar(10) not null references table_occupation(occupationID),
    totalPrice NUMERIC(15, 2) not null DEFAULT 0,
    dateOrder timestamp not null default current_timestamp,
    state integer check (state >= 0),
    status integer check (status >= 0)
);


create sequence dishOrderDetails_seq start with 1;
create table dishOrderDetails(
    orderDetailsID varchar(10) not null primary key,
    dishID varchar(10) not null references dish(dishID),
    orderID varchar(10) not null references dishOrder(orderID),
    unitPrice NUMERIC(15, 2) not null DEFAULT 0,
    quantity integer,
    userID varchar(10) not null references users(userID), --employe nandray commande
    dateOrder timestamp not null default current_timestamp,
    state integer check (state >= 0),
    status integer check (status >= 0)
);


alter table activity add column isindividual boolean default true;
alter table activityOrder add column totalPerson integer default 1;


create sequence pack_seq start with 1;
create table pack(
    packID varchar(10) not null primary key,
    companyID varchar(10) not null references company(companyID),
    title varchar(50) not null,
    discount numeric(15,2) not null default 0,
    startDate timestamp default current_timestamp,
    endDate timestamp default null,
    status integer check (status >= 0)
);



create table packRestoDetails(
    id serial primary key,
    packID varchar(10) not null references pack(packID),
    dishID varchar(10) not null references dish(dishID),
    quantity integer default 0,
    status integer check (status >= 0)
);

create table packHotelDetails(
    id serial primary key,
    packID varchar(10) not null references pack(packID),
    roomID varchar(10) not null references room(roomID),
    duration integer default 0,
    status integer check (status >= 0)
);

create table packActivity(
    id serial primary key,
    packID varchar(10) not null references pack(packID),
    activityID varchar(10) not null references activity(activityID),
    duration integer default 0,
    status integer check (status >= 0)
);


---///// facturation

create table tax(
    taxID serial primary key,
    companyID varchar(10) not null references company(companyID),
    taxRate NUMERIC(15, 2) default 0,
    dateTax date default current_date,
    status integer check (status >= 0)
);

create sequence bill_seq start with 1;
create table billing(
    billID varchar(10) not null primary key,
    customerID varchar(10) not null references customer(customerID),
    billingDate timestamp default current_timestamp,
    taxe NUMERIC(15, 2) default 0,
    packID varchar(10) references pack(packID),
    status integer check (status >= 0)
);

alter table billing drop column packid;
alter table billing add column packid varchar(10) references pack(packID);
alter table billing add column state integer default 0 check (state >= 0);
alter table billing add column companyid varchar(10) references company(companyID);

create table quantityBillingDetails(
    id serial primary key,
    billID varchar(10) not null references billing(billID),
    serviceName varchar(100),
    serviceCode varchar(10) not null,
    quantity integer check (quantity >= 0),
    unitPrice numeric(15,2) check (unitPrice >= 0),
    status integer check (status >= 0)
);

create table durationBillingDetails(
    id serial primary key,
    billID varchar(10) not null references billing(billID),
    serviceName varchar(100),
    serviceCode varchar(10) not null,
    unitPrice numeric(15,2) check (unitPrice >= 0),
    typeDuration varchar(1), -- H/J,
    startTime timestamp not null,
    endTime timestamp check (startTime < endTime),
    status integer check (status >= 0)
);


