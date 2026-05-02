package gendev.it.serenity.restaurant.controller.dish;

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
import gendev.it.serenity.restaurant.application.dish.DishOrderService;
import gendev.it.serenity.restaurant.domain.dto.dish.DishOrderDTO;

@RestController
@RequestMapping("restaurant/order")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class DishOrderController extends CommonController<DishOrderDTO, DishOrderService> {

    public DishOrderController(DishOrderService service) {
        super(service);
        // TODO Auto-generated constructor stub
    }

    // endpoint pour modifier le statut d'une commande
    @PutMapping("/update/state/{id}")
    public ResponseEntity<?> updateState(@PathVariable String id,
            @RequestParam(name = "state", required = false) Integer state) {
        try {
            getService().updateState(id, state);
            return ResponseEntity.ok("Modification réussi");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    @Override
    public ResponseEntity<?> findEntityByID(String id, Integer status) {
        // TODO Auto-generated method stub
        
        try {
            return ResponseEntity.ok(getService().findOneById(id, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/table/{tableid}")
    public ResponseEntity<?> findOrderByTable(@PathVariable String tableid,
         @RequestParam LocalDateTime start,@RequestParam LocalDateTime end,
         @RequestParam(required = false) Integer state ) {
        // TODO Auto-generated method stub    
        try {
            return ResponseEntity.ok(getService().findOneByTableAndDate(tableid, start, end, state));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // récupération par table occupation id
    @GetMapping("/table_occupation/{occupationid}")
    public ResponseEntity<?> findOrderByTableOccupation(@PathVariable String occupationid ) {
        // TODO Auto-generated method stub    
        try {
            return ResponseEntity.ok(getService().findByTableOccupation(occupationid));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    
    @GetMapping("/bystate")
    public ResponseEntity<?> findAllByCompanyAndState(@RequestParam(name = "status", required = false) Integer status,
     @RequestParam String company, @RequestParam( required = false) List<Integer> states) {
        try {
            return ResponseEntity.ok(getService().findAllByCompanyAndState(company, status, states));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }
    @GetMapping("/bystate/{page}/{size}")
    public ResponseEntity<?> findAllpaginateModelByCompanyAndState(@PathVariable("page") int page, @PathVariable("size") int size,
            @RequestParam(name = "field", defaultValue = "name", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam String company, @RequestParam List<Integer> states
        ) {
        try {
            return ResponseEntity.ok(getService().findAllByCompanyAndState(page, size, field, sort, status, company, states));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }
    



}
