create or replace view v_users as 
select u.userid,u.name,u.profilid,u.phone,u.joineddate,u.status,p.companyid,p.name profil,p.authority,p.status profilStatus from users u join profil p on u.profilid=p.profilid;

create or replace view v_room as
select r.*, rt.status type_room_status, rt.name rt_name, rt.companyID from room r join roomType rt on rt.typeID = r.typeID;

create or replace view v_table as
select rt.*, tt.status type_table_status, tt.name tt_name, tt.companyID from restaurant_table rt join tabletype tt on tt.tabletypeID = rt.tabletypeID;