package gendev.it.serenity.users.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.users.infrastructure.entity.Company;

@Repository
public interface CompanyRepo extends JpaRepository<Company, String>{
    // function find by id ohatra
    Company findByCompanyID(String companyID);
}
