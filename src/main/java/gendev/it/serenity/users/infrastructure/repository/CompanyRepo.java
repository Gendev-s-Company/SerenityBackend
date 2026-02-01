package gendev.it.serenity.users.infrastructure.repository;

import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.users.infrastructure.entity.Company;

@Repository
public interface CompanyRepo extends CommonRepository<Company, String>{
    
}
