package gendev.it.serenity.hotel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.hotel.application.ActivityPhotoService;
import gendev.it.serenity.hotel.domain.dto.ActivityPhotoDTO;

@RestController
@RequestMapping("api/hotel/activityphoto")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class ActivityPhotoController extends CommonController<ActivityPhotoDTO, ActivityPhotoService> {

    public ActivityPhotoController(ActivityPhotoService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }
    
    @PostMapping("/save")
    public ResponseEntity<?> saveActivityPhoto(@ModelAttribute ActivityPhotoDTO model) {
        try {
            return new ResponseEntity<>(getService().save(model), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("/byActivity")
    public ResponseEntity<?> findAllByActivity(@RequestParam(name = "status", required = false) Integer status,
            @RequestParam String activityid) {
        try {
            return ResponseEntity.ok(getService().findAllByActivity(activityid, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    // endpoint a utiliser pour l'affichage des prix d'activité
    @GetMapping("/byActivity/{page}/{size}")
    public ResponseEntity<?> findAllpaginateModelByActivity(@PathVariable("page") int page,
            @PathVariable("size") int size,
            @RequestParam(name = "field", defaultValue = "photoID", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam String activityid) {
        try {
            return ResponseEntity.ok(getService().paginateAllByACtivity(page, size, field, sort, status, activityid));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

}
