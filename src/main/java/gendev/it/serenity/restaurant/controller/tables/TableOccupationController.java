package gendev.it.serenity.restaurant.controller.tables;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

}
