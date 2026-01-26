package gendev.it.serenity.users.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import gendev.it.serenity.users.domain.dto.CompanyDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import gendev.it.serenity.users.infrastructure.repository.CompanyRepo;
import jakarta.transaction.Transactional;

@Service
public class CompanyService{
    private final CompanyRepo repo;

    public CompanyService(CompanyRepo repo) {
        this.repo = repo;
    }
    @Transactional
    public Company create(CompanyDTO companydto){

        return repo.save(companydto.dtoToEntity());
    }
    public List<CompanyDTO> findAll(){
        List<Company> list = repo.findAll();
        List<CompanyDTO> results = new ArrayList<CompanyDTO>();
        for (Company company : list) {
            results.add(company.EntityToDTO());
        }
        return results;
    }
    public CompanyDTO findOneById(String id) throws Exception{
        try {
            return repo.findByCompanyID(id).EntityToDTO();
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception( "Company "+id+" introuvable");
        }
        
    }
}
