package gendev.it.serenity.hotel.controller;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.hotel.application.ActivityService;
import gendev.it.serenity.hotel.domain.dto.ActivityDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hotel/activity")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class ActivityController extends CommonController<ActivityDTO,ActivityService> {

    public ActivityController(ActivityService service) {
        super(service);
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAll(@RequestParam(name = "status", required = false) Integer status, @RequestParam String company) {
        try {
            return ResponseEntity.ok(getService().findAllByCompany(company,status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }
    @GetMapping("/all/{page}/{size}")
    public ResponseEntity<?> findAllpaginateModel(@PathVariable("page") int page, @PathVariable("size") int size,
            @RequestParam(name = "field", defaultValue = "name", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam String company
        ) {
        try {
            return ResponseEntity.ok(getService().paginateAllByCompany(page, size, field, sort, status,company));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

}
