package gendev.it.serenity.hotel.controller;

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
import gendev.it.serenity.hotel.application.ActivityPriceService;
import gendev.it.serenity.hotel.domain.dto.ActivityPriceDTO;

@RestController
@RequestMapping("api/hotel/activityPrice")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class ActivityPriceController extends CommonController<ActivityPriceDTO,ActivityPriceService> {

    public ActivityPriceController(ActivityPriceService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }
     @GetMapping("/lastPrice")
    public ResponseEntity<?> findLastPrice(@RequestParam(name = "status", required = false) Integer status, @RequestParam String activityid) {
        try {
            return ResponseEntity.ok(getService().findLastPrice(activityid, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    @GetMapping("/byActivity")
    public ResponseEntity<?> findAllByCompany(@RequestParam(name = "status", required = false) Integer status, @RequestParam String activityid) {
        try {
            return ResponseEntity.ok(getService().findAllByActivity(activityid, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }
    // endpoint a utiliser pour l'affichage des prix d'activité
    @GetMapping("/byActivity/{page}/{size}")
    public ResponseEntity<?> findAllpaginateModelByCompany(@PathVariable("page") int page, @PathVariable("size") int size,
            @RequestParam(name = "field", defaultValue = "dateChanged", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam String activityid
        ) {
        try {
            return ResponseEntity.ok(getService().paginateAllByACtivity(page, size, field, sort, status,activityid));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }
}
