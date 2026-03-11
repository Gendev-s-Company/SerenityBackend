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
        COALESCE(MAX(res.state), 1)::integer AS reservation_state
    FROM room r
    LEFT JOIN reservation res 
        ON r.roomID = res.roomID
        AND res.state = ANY(filter_status)
        AND res.startTime < search_end
        AND res.endTime > search_start
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
        COALESCE(r.state, 1)::integer room_state,
        COALESCE(res.state, 1)::integer reservation_state,
        res.startTime, -- Heure réelle en base
        res.endTime    -- Heure réelle en base
    FROM date_range d
    CROSS JOIN room r
    LEFT JOIN reservation res 
        ON r.roomID = res.roomID
        AND res.state = ANY(filter_status)
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
    ARRAY[2, 3]            -- On cherche les réservations et occupations
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
