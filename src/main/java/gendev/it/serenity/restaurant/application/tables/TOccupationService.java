package gendev.it.serenity.restaurant.application.tables;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.restaurant.domain.dto.tables.TOccupationDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableOccupation;
import gendev.it.serenity.restaurant.infrastructure.repository.tables.TOccupationRepo;

@Service
public class TOccupationService extends CommonService<TableOccupation, TOccupationDTO, String, TOccupationRepo>{

    public TOccupationService(TOccupationRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

}
