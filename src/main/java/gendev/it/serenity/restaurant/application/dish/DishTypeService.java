package gendev.it.serenity.restaurant.application.dish;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.restaurant.domain.dto.dish.DishTypeDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishType;
import gendev.it.serenity.restaurant.infrastructure.repository.dish.DishTypeRepo;

@Service
public class DishTypeService extends CommonService<DishType, DishTypeDTO, String, DishTypeRepo>{

    public DishTypeService(DishTypeRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }
    
}
