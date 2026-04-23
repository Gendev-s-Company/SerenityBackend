package gendev.it.serenity.restaurant.application.dish;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.restaurant.domain.dto.dish.DishDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.Dish;
import gendev.it.serenity.restaurant.infrastructure.repository.dish.DishRepo;

@Service
public class DishService extends CommonService<Dish, DishDTO, String, DishRepo>{

    public DishService(DishRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

    public void updateState(String id, Integer state) throws Exception{
        Dish dishToUpdate = findById(id, 0).dtoToEntity();
        if (state == null)
            throw new Exception("veuillez indiquer le state");
        dishToUpdate.setState(state);
        getJpa().save(dishToUpdate);
    }
    
}
