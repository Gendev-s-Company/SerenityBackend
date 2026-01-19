--Création user avec password
--Se connecter d'abord en tant que user postgres
create database serenity;
create user serenity with password 'serenity';
GRANT ALL PRIVILEGES ON DATABASE serenity TO serenity;

\c postgres
ALTER DATABASE serenity OWNER TO serenity;

\c serenity
GRANT ALL ON SCHEMA public TO serenity;



