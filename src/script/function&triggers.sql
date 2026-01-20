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
