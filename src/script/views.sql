create or replace view v_users as 
select u.userid,u.name,u.profilid,u.phone,u.joineddate,u.status,p.companyid,p.name profil,p.authority,p.status profilStatus from users u join profil p on u.profilid=p.profilid;
