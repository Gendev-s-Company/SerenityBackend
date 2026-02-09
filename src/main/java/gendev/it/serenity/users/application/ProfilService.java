package gendev.it.serenity.users.application;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.users.domain.dto.ProfilDTO;
import gendev.it.serenity.users.infrastructure.entity.Profil;
import gendev.it.serenity.users.infrastructure.repository.ProfilRepo;

@Service
public class ProfilService extends CommonService<Profil,ProfilDTO,String, ProfilRepo> {

    public ProfilService(ProfilRepo profilRepository) {
        super(profilRepository); 
    }

   

}
