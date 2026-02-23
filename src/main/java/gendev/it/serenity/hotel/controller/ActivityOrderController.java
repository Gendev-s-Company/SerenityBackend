package gendev.it.serenity.hotel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.hotel.application.ActivityOrderService;
import gendev.it.serenity.hotel.domain.dto.ActivityOrderDTO;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hotel/activityorder")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class ActivityOrderController extends CommonController<ActivityOrderDTO, ActivityOrderService> {

    public ActivityOrderController(ActivityOrderService service) {
        super(service);
        // TODO Auto-generated constructor stub
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
            @RequestParam(name = "field", defaultValue = "dateChanged", required = false) String field,
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

    @GetMapping("/bycustomer")
    public ResponseEntity<?> findAllByCustomer(@RequestParam(name = "status", required = false) Integer status, @RequestParam String customerid) {
        try {
            return ResponseEntity.ok(getService().findAllByCustomer(customerid, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }
    // endpoint a utiliser pour l'affichage des prix d'activité
    @GetMapping("/bycustomer/{page}/{size}")
    public ResponseEntity<?> findAllpaginateModelByCustomer(@PathVariable("page") int page, @PathVariable("size") int size,
            @RequestParam(name = "field", defaultValue = "dateChanged", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam String customerid
        ) {
        try {
            return ResponseEntity.ok(getService().paginateAllByCustomer(page, size, field, sort, status,customerid));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }
}
