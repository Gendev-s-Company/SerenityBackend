package gendev.it.serenity.restaurant.controller.dish;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.hotel.domain.dto.ActivityPhotoCreateDTO;
import gendev.it.serenity.restaurant.application.dish.DishPhotoService;
import gendev.it.serenity.restaurant.domain.dto.dish.DishPhotoDTO;

@RestController
@RequestMapping("restaurant/dish/photo")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class DishPhotoController extends CommonController<DishPhotoDTO, DishPhotoService>{

    public DishPhotoController(DishPhotoService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }

    
    @PostMapping("/save")
    public ResponseEntity<?> saveDishPhoto(@ModelAttribute ActivityPhotoCreateDTO model) {
        try {
            return new ResponseEntity<>(getService().saves(model), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("/bydish")
    public ResponseEntity<?> findAllByActivity(@RequestParam(name = "status", required = false) Integer status,
            @RequestParam String dishid) {
        try {
            return ResponseEntity.ok(getService().findAllByDish(dishid, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    // endpoint a utiliser pour l'affichage des prix d'activité
    @GetMapping("/bydish/{page}/{size}")
    public ResponseEntity<?> findAllpaginateModelByDish(@PathVariable("page") int page,
            @PathVariable("size") int size,
            @RequestParam(name = "field", defaultValue = "photoID", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam String dishid) {
        try {
            return ResponseEntity.ok(getService().paginateAllByDish(page, size, field, sort, status, dishid));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }
}
