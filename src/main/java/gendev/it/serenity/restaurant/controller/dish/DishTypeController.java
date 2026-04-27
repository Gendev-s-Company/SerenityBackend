package gendev.it.serenity.restaurant.controller.dish;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.restaurant.application.dish.DishTypeService;
import gendev.it.serenity.restaurant.domain.dto.dish.DishTypeDTO;

@RestController
@RequestMapping("restaurant/dish-type")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class DishTypeController extends CommonController<DishTypeDTO, DishTypeService> {

    public DishTypeController(DishTypeService service) {
        super(service);
        // TODO Auto-generated constructor stub
    }

    @GetMapping("/group/type/{page}/{size}")
    public ResponseEntity<?> findAllpaginateModelByCompanyGroupByType(@PathVariable("page") int page,
            @PathVariable("size") int size,
            @RequestParam(name = "field", defaultValue = "name", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam String company) {
        try {
            return ResponseEntity
                    .ok(getService().paginateAllByCompanyGroupByType(page, size, field, sort, status, company));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

}
