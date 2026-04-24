package gendev.it.serenity.restaurant.controller.dish;

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

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.restaurant.application.dish.DishOrderDetailsService;
import gendev.it.serenity.restaurant.domain.dto.dish.DishOrderDetailsDTO;

@RestController
@RequestMapping("restaurant/order/details")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class DishOrderDetailsController extends CommonController<DishOrderDetailsDTO, DishOrderDetailsService>{
    
    public DishOrderDetailsController(DishOrderDetailsService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }

    @PutMapping("/update/state/{id}")
    public ResponseEntity<?> updateState(@PathVariable String id, @RequestParam(name = "state", required = false) Integer state) {
        try {
            getService().updateState(id, state);
            return ResponseEntity.ok("Modification réussi");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    @GetMapping("/byOrder")
    public ResponseEntity<?> findAllByCompany(@RequestParam(name = "status", required = false) Integer status, @RequestParam String orderid) {
        try {
            return ResponseEntity.ok(getService().findAllByOrderID(orderid,status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }


}
