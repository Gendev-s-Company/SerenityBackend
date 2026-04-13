package gendev.it.serenity.restaurant.controller.tables;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.restaurant.application.tables.TOccupationService;
import gendev.it.serenity.restaurant.domain.dto.tables.TOccupationDTO;

@RestController
@RequestMapping("restaurant/table/occupation")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class TableOccupationController extends CommonController<TOccupationDTO, TOccupationService>{

    public TableOccupationController(TOccupationService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }

    @PutMapping("/update/state/{id}")
    public ResponseEntity<?> updateState(@PathVariable String id, @RequestParam(name = "state") Integer state) {
        try {
            getService().updateState(id, state);
            return ResponseEntity.ok("Modification réussi");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }


    @GetMapping("/avalaible")
    public ResponseEntity<?> findReservationByCompany(@RequestParam(name = "status", required = false) Integer status,
            @RequestParam List<Integer> state,
            @RequestParam LocalDateTime start, @RequestParam LocalDateTime end,
            @RequestParam String company) {
        try {
            return ResponseEntity.ok(getService().findDisponibility(status, company, state, start, end));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    @GetMapping("/avalaible/{page}/{size}")
    public ResponseEntity<?> findAllpaginateReservationByCompany(@PathVariable("page") int page,
            @PathVariable("size") int size,
            @RequestParam List<Integer> state,
            @RequestParam(required = false, name = "start") LocalDateTime start, @RequestParam(required = false, name = "end") LocalDateTime end,
            @RequestParam(name = "field", defaultValue = "occupationID", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam String company) {
        try {
            return ResponseEntity.ok(getService().findDisponibility(company, state, start, end, page, size, field, sort, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

}
