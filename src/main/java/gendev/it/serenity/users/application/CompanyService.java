package gendev.it.serenity.users.application;


import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import gendev.it.serenity.users.infrastructure.repository.CompanyRepo;

@Service
public class CompanyService extends CommonService<Company,CompanyDTO,String, CompanyRepo>{

    public CompanyService(CompanyRepo repo) {
        super(repo);
    }
}
