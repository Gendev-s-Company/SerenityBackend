package gendev.it.serenity.users.application;

import java.util.List;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.users.domain.dto.ProfilDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import gendev.it.serenity.users.infrastructure.entity.Profil;
import gendev.it.serenity.users.infrastructure.repository.CompanyRepo;
import gendev.it.serenity.users.infrastructure.repository.ProfilRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class ProfilService extends CommonService<Profil,ProfilDTO,String, ProfilRepo> {

    public ProfilService(ProfilRepo profilRepository) {
        super(profilRepository); 
    }

   

}
