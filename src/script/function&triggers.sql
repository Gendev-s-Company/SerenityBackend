----POUR LA TABLE COMPANY
CREATE OR REPLACE FUNCTION generate_company_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.companyID := 'COMP' || LPAD(nextval('company_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_company_id
BEFORE INSERT ON company
    FOR EACH ROW
        EXECUTE FUNCTION generate_company_id();

----POUR LA TABLE PROFIL
CREATE OR REPLACE FUNCTION generate_profil_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.profilID := 'PROF' || LPAD(nextval('profil_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_profil_id
BEFORE INSERT ON profil
    FOR EACH ROW
        EXECUTE FUNCTION generate_profil_id();

----POUR LA TABLE USERS
CREATE OR REPLACE FUNCTION generate_users_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.userID := 'USER' || LPAD(nextval('users_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_users_id
BEFORE INSERT ON users
    FOR EACH ROW
        EXECUTE FUNCTION generate_users_id();

-- \\\\\\\\\\\\\\\\\PARTIE HOTEL/////////////////
----POUR LA TABLE activity
CREATE OR REPLACE FUNCTION generate_activity_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.activityID := 'ACTI' || LPAD(nextval('activity_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_activity_id
BEFORE INSERT ON activity
    FOR EACH ROW
        EXECUTE FUNCTION generate_activity_id();

----POUR LA TABLE activityPhoto
CREATE OR REPLACE FUNCTION generate_activityPhoto_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.photoID := 'ACTP' || LPAD(nextval('activityPhoto_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_activityPhoto_id
BEFORE INSERT ON activityPhoto
    FOR EACH ROW
        EXECUTE FUNCTION generate_activityPhoto_id();

----POUR LA TABLE customer
CREATE OR REPLACE FUNCTION generate_customer_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.customerid := 'CUST' || LPAD(nextval('customer_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_customer_id
BEFORE INSERT ON customer
    FOR EACH ROW
        EXECUTE FUNCTION generate_customer_id();

----POUR LA TABLE activityOrder
CREATE OR REPLACE FUNCTION generate_activityOrder_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.acOrderID := 'ACTO' || LPAD(nextval('activityOrder_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_activityOrder_id
BEFORE INSERT ON activityOrder
    FOR EACH ROW
        EXECUTE FUNCTION generate_activityOrder_id();
        
        
        --///////PARTIE CHAMBRE\\\\\\
        
CREATE OR REPLACE FUNCTION generate_roomType_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.typeID := 'TYPR' || LPAD(nextval('roomType_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_roomType_id
BEFORE INSERT ON roomType
    FOR EACH ROW
        EXECUTE FUNCTION generate_roomType_id();

CREATE OR REPLACE FUNCTION generate_room_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.roomID := 'ROOM' || LPAD(nextval('room_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_room_id
BEFORE INSERT ON room
    FOR EACH ROW
        EXECUTE FUNCTION generate_room_id();


CREATE OR REPLACE FUNCTION generate_roomPhoto_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.photoID := 'RPIC' || LPAD(nextval('roomPhoto_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_room_id
BEFORE INSERT ON roomPhoto
    FOR EACH ROW
        EXECUTE FUNCTION generate_roomPhoto_id();


CREATE OR REPLACE FUNCTION generate_reservation_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.reservationID := 'RESA' || LPAD(nextval('reservation_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_reservation_id
BEFORE INSERT ON reservation
    FOR EACH ROW
        EXECUTE FUNCTION generate_reservation_id();


-- reservation disponibility
CREATE OR REPLACE FUNCTION get_rooms_disponibility(
    search_start timestamp, 
    search_end timestamp, 
    filter_status integer[] -- On accepte un tableau d'entiers ex: '{1,2,3}'
)
RETURNS TABLE (
    roomID varchar,
    name varchar,
    room_state integer,
    reservation_state integer
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        r.roomID,
        r.name,
        COALESCE(MAX(r.state), 0)::integer AS room_state,
        COALESCE(MAX(res.state), 0)::integer AS reservation_state
    FROM room r
    LEFT JOIN reservation res 
        ON r.roomID = res.roomID
        -- AND res.state = ANY(filter_status)
        AND res.startTime < search_end
        AND res.endTime > search_start
    -- where res.state = ANY(filter_status)
    GROUP BY r.roomID, r.name
    ORDER BY r.roomID;
END;
$$ LANGUAGE plpgsql;


-- détail disponibilité pour une chambre
CREATE OR REPLACE FUNCTION get_room_calendar_with_hours(
    search_start timestamp, 
    search_end timestamp, 
    filter_status integer[]
)
RETURNS TABLE (
    day timestamp,
    roomID varchar,
    room_name varchar,
    room_state integer,
    reservation_state integer,
    actual_arrival timestamp,  -- Précision de l'heure d'arrivée
    actual_departure timestamp -- Précision de l'heure de départ
) AS $$
BEGIN
    RETURN QUERY
    WITH date_range AS (
        SELECT generate_series(
            date_trunc('day', search_start), 
            date_trunc('day', search_end), 
            '1 day'::interval
        )::timestamp AS day_date
    )
    SELECT 
        d.day_date,
        r.roomID,
        r.name,
        COALESCE(r.state, 0)::integer room_state,
        COALESCE(res.state, 0)::integer reservation_state,
        res.startTime, -- Heure réelle en base
        res.endTime    -- Heure réelle en base
    FROM date_range d
    CROSS JOIN room r
    LEFT JOIN reservation res 
        ON r.roomID = res.roomID
       -- AND res.state = ANY(filter_status)
        -- Logique de chevauchement : la réservation touche ce jour
        AND res.startTime < (d.day_date + interval '1 day')
        AND res.endTime > d.day_date
    ORDER BY d.day_date, r.roomID;
END;
$$ LANGUAGE plpgsql;

-- disponibilité global
SELECT * FROM get_rooms_disponibility(
    '2026-03-11 00:00:00', -- Début de l'affichage
    '2026-03-14 23:59:59', -- Fin de l'affichage
    ARRAY[0, 3]            -- On cherche les réservations et occupations
) d 
where exists (select roomid from v_room where companyID='COMP000001' and v_room.status=0 and v_room.roomID = d.roomID)
order by roomid asc;


-- disponibilité détaillé
SELECT * FROM get_room_calendar_with_hours(
    '2026-03-11 00:00:00', -- Début de l'affichage
    '2026-03-14 23:59:59', -- Fin de l'affichage
    ARRAY[2, 3]            -- On cherche les réservations et occupations
) d
where exists (select roomid from v_room where companyID='COMP000001' and v_room.status=0 and v_room.roomID = d.roomID)
 order by roomid asc;


-- ////////////////////// RESTAURATION \\\\\\\\\\\\\\\\\\\\

-- type de table trigger
CREATE OR REPLACE FUNCTION generate_table_type_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.tabletypeid := 'TTYP' || LPAD(nextval('table_type_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER table_type_id
BEFORE INSERT ON tabletype
    FOR EACH ROW
        EXECUTE FUNCTION generate_table_type_id();


-- table trigger
CREATE OR REPLACE FUNCTION generate_table_seq_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.tableID := 'TTAB' || LPAD(nextval('table_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER table_seq_id
BEFORE INSERT ON restaurant_table
    FOR EACH ROW
        EXECUTE FUNCTION generate_table_seq_id();


-- table photo
CREATE OR REPLACE FUNCTION generate_table_photo_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.photoID := 'TPIC' || LPAD(nextval('tablePhoto_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tablePhoto
BEFORE INSERT ON tablePhoto
    FOR EACH ROW
        EXECUTE FUNCTION generate_table_photo_id();

-- table occupation
CREATE OR REPLACE FUNCTION generate_table_occupation_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.occupationID := 'TOCC' || LPAD(nextval('tableoccupation_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tableOccupation
BEFORE INSERT ON table_occupation
    FOR EACH ROW
        EXECUTE FUNCTION generate_table_occupation_id();

-- //////////table function\\\\\\\\\


CREATE OR REPLACE FUNCTION get_table_disponibility(
    search_start timestamp, 
    search_end timestamp, 
    filter_status integer[] -- On accepte un tableau d'entiers ex: '{1,2,3}'
)
RETURNS TABLE (
    tableid varchar,
    name varchar,
    table_state integer,
    reservation_state integer
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        r.tableID,
        r.name,
        COALESCE(MAX(r.status), 0)::integer AS table_state,
        COALESCE(MAX(res.state), 0)::integer AS reservation_state
    FROM restaurant_table r
    LEFT JOIN table_occupation res 
        ON r.tableID = res.tableID
        -- AND res.state = ANY(filter_status)
        AND res.startTime < search_end
        AND res.endTime > search_start
    -- where res.state = ANY(filter_status)
    GROUP BY r.tableID, r.name
    ORDER BY r.tableID;
END;
$$ LANGUAGE plpgsql;


-- détail disponibilité pour une table
-- CREATE OR REPLACE FUNCTION get_table_calendar_with_hours(
--     search_start timestamp, 
--     search_end timestamp, 
--     filter_status integer[]
-- )
-- RETURNS TABLE (
--     day timestamp,
--     tableID varchar,
--     table_name varchar,
--     table_state integer,
--     reservation_state integer,
--     actual_arrival timestamp,  -- Précision de l'heure d'arrivée
--     actual_departure timestamp -- Précision de l'heure de départ
-- ) AS $$
-- BEGIN
--     RETURN QUERY
--     WITH date_range AS (
--         SELECT generate_series(
--             date_trunc('day', search_start), 
--             date_trunc('day', search_end), 
--             '1 day'::interval
--         )::timestamp AS day_date
--     )
--     SELECT 
--         d.day_date,
--         r.tableID,
--         r.name,
--         COALESCE(r.status, 0)::integer table_state,
--         COALESCE(res.state, 0)::integer reservation_state,
--         res.startTime, -- Heure réelle en base
--         res.endTime    -- Heure réelle en base
--     FROM date_range d
--     CROSS JOIN restaurant_table r
--     LEFT JOIN table_occupation res 
--         ON r.tableID = res.tableID
--        -- AND res.state = ANY(filter_status)
--         -- Logique de chevauchement : la réservation touche ce jour
--         AND res.startTime < (d.day_date + interval '1 day')
--         AND res.endTime > d.day_date
--     ORDER BY d.day_date, r.tableID;
-- END;
-- $$ LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION get_table_day_with_hours(
    search_start timestamp, 
    search_end timestamp, 
    filter_status integer[]
)
RETURNS TABLE (
    day timestamp,
    tableID varchar,
    table_name varchar,
    table_state integer,
    reservation_state integer,
    actual_arrival timestamp,
    actual_departure timestamp
) AS $$
BEGIN
    RETURN QUERY
    WITH processed_reservations AS (
        SELECT 
            date_trunc('day', search_start)::timestamp AS day_date, 
            r.tableID,
            r.name,
            COALESCE(r.status, 0)::integer AS t_state,
            COALESCE(res.state, 0)::integer AS r_state,
            GREATEST(res.startTime, search_start) AS start_t,
            LEAST(res.endTime, search_end) AS end_t
        FROM restaurant_table r
        LEFT JOIN table_occupation res 
            ON r.tableID = res.tableID
            AND res.startTime < search_end
            AND res.endTime > search_start
        WHERE (filter_status IS NULL OR r.status = ANY(filter_status))
    ),
    timeline AS (
        SELECT * FROM processed_reservations WHERE start_t IS NOT NULL
        
        UNION ALL
        SELECT 
            date_trunc('day', search_start)::timestamp AS day_date, sub.tableID, sub.name, sub.t_state, 0, sub.gap_start, sub.gap_end
        FROM (
            SELECT 

            date_trunc('day', search_start)::timestamp AS day_date, 
                t.tableID,
                t.name,
                COALESCE(t.status, 0)::integer AS t_state,
                COALESCE(LAG(p.end_t) OVER (PARTITION BY t.tableID ORDER BY p.start_t), search_start) AS gap_start,
                COALESCE(p.start_t, search_end) AS gap_end
            FROM restaurant_table t
            LEFT JOIN processed_reservations p ON t.tableID = p.tableID
            WHERE (filter_status IS NULL OR t.status = ANY(filter_status))
        ) sub
        WHERE sub.gap_start < sub.gap_end
        
        UNION ALL
        SELECT
        date_trunc('day', search_start)::timestamp AS day_date,  
            t.tableID, t.name, COALESCE(t.status, 0)::integer, 0, MAX(p.end_t), search_end
        FROM restaurant_table t
        JOIN processed_reservations p ON t.tableID = p.tableID
        GROUP BY t.tableID, t.name, t.status
        HAVING MAX(p.end_t) < search_end
    )
    SELECT * FROM timeline
    ORDER BY tableID, actual_arrival;
END;
$$ LANGUAGE plpgsql;


-- disponibilité global
SELECT * FROM get_table_disponibility(
    '2026-03-11 00:00:00', -- Début de l'affichage
    '2026-03-14 23:59:59', -- Fin de l'affichage
    ARRAY[0, 3]            -- On cherche les réservations et occupations
) d 
where exists (select tableid from v_table where companyID='COMP000001' and v_table.status=0 and v_table.tableID = d.tableID)
order by tableid asc;


-- disponibilité détaillé par jour
SELECT * FROM get_table_calendar_with_hours(
    '2026-03-11 00:00:00', -- Début de l'affichage
    '2026-03-14 23:59:59', -- Fin de l'affichage
    ARRAY[2, 3]            -- On cherche les réservations et occupations
) d
where exists (select tableid from v_table where companyID='COMP000001' and v_table.status=0 and v_table.tableID = d.tableID)
 order by tableid asc;


 --disponibilité détaillé pour un jour donnée
 SELECT * FROM get_table_day_with_hours(
    '2026-04-20 00:00:00', -- Début de l'affichage
    '2026-04-20 23:59:59', -- Fin de l'affichage
    ARRAY[0, 3]            -- On cherche les réservations et occupations
) d 
where exists (select tableid from v_table where companyID='COMP000001' and v_table.status=0 and v_table.tableID = d.tableID)
and d.reservation_state in (0,1,4,5) order by tableid,actual_arrival asc;


--trigger table dish et company


-- Dish type
CREATE OR REPLACE FUNCTION generate_dishType_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.typeID := 'DTYP' || LPAD(nextval('dishType_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER dishType_trigger
BEFORE INSERT ON dishType
    FOR EACH ROW
        EXECUTE FUNCTION generate_dishType_id();

-- Dish 
CREATE OR REPLACE FUNCTION generate_dish_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.dishID := 'DISH' || LPAD(nextval('dish_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER dish_trigger
BEFORE INSERT ON dish
    FOR EACH ROW
        EXECUTE FUNCTION generate_dish_id();

-- DishOrder 
CREATE OR REPLACE FUNCTION generate_dishorder_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.orderID := 'DORD' || LPAD(nextval('dishOrder_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER dishorder_trigger
BEFORE INSERT ON dishOrder
    FOR EACH ROW
        EXECUTE FUNCTION generate_dishorder_id();



-- DishOrder Details
CREATE OR REPLACE FUNCTION generate_dishorderdetails_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.orderDetailsID := 'DETO' || LPAD(nextval('dishOrderDetails_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER dishorderdetails_trigger
BEFORE INSERT ON dishOrderDetails
    FOR EACH ROW
        EXECUTE FUNCTION generate_dishorderdetails_id();



CREATE OR REPLACE FUNCTION generate_pack_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.packID := 'PACK' || LPAD(nextval('pack_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER pack_trigger
BEFORE INSERT ON pack
    FOR EACH ROW
        EXECUTE FUNCTION generate_pack_id();



CREATE OR REPLACE FUNCTION generate_bill_id()
RETURNS TRIGGER AS $$
BEGIN
    -- LPAD complète avec des '0' jusqu'à 6 caractères (10 total - 4 de "COMP")
    NEW.billID := 'BILL' || LPAD(nextval('bill_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER bill_trigger
BEFORE INSERT ON billing
    FOR EACH ROW
        EXECUTE FUNCTION generate_bill_id();