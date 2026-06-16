package gendev.it.serenity.pack.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.pack.application.PackService;
import gendev.it.serenity.pack.dto.PackDTO;

@RestController
@RequestMapping("pack")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class PackController extends CommonController<PackDTO, PackService> {

    public PackController(PackService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id, @RequestBody PackDTO toDelete, @RequestParam Boolean isDetail) {
        try {
            return ResponseEntity.ok(getService().deletePack(toDelete, id, isDetail));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Suppression impossible : "+e.getMessage());

        }
    }
}
