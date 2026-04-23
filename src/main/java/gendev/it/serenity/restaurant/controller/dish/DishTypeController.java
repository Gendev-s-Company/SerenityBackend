package gendev.it.serenity.restaurant.controller.dish;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.restaurant.application.dish.DishTypeService;
import gendev.it.serenity.restaurant.domain.dto.dish.DishTypeDTO;

@RestController
@RequestMapping("restaurant/dish-type")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class DishTypeController extends CommonController<DishTypeDTO, DishTypeService>{

    public DishTypeController(DishTypeService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }

    
    
}
