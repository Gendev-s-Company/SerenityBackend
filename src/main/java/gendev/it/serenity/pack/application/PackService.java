package gendev.it.serenity.pack.application;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.pack.dto.PackDTO;
import gendev.it.serenity.pack.infrastructure.models.Pack;
import gendev.it.serenity.pack.infrastructure.repository.PackRepo;

@Service
public class PackService extends CommonService<Pack, PackDTO, String, PackRepo> {

    public PackService(PackRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }
    
}
