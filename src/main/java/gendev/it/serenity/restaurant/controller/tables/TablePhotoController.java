package gendev.it.serenity.restaurant.controller.tables;

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
import gendev.it.serenity.restaurant.application.tables.TablePhotoService;
import gendev.it.serenity.restaurant.domain.dto.tables.TablePhotoDTO;

@RestController
@RequestMapping("restaurant/tablephoto")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class TablePhotoController extends CommonController<TablePhotoDTO, TablePhotoService> {

    public TablePhotoController(TablePhotoService service) {
        super(service);
        // TODO Auto-generated constructor stub
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRoomPhoto(@ModelAttribute ActivityPhotoCreateDTO model) {
        try {
            return new ResponseEntity<>(getService().saves(model), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("/bytable")
    public ResponseEntity<?> findAllByRoom(@RequestParam(name = "status", required = false) Integer status,
            @RequestParam String tableid) {
        try {
            return ResponseEntity.ok(getService().findAllByTable(tableid, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    // endpoint a utiliser pour l'affichage des détail de chambre
    @GetMapping("/bytable/{page}/{size}")
    public ResponseEntity<?> findAllpaginateModelBytable(@PathVariable("page") int page,
            @PathVariable("size") int size,
            @RequestParam(name = "field", defaultValue = "photoID", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam String tableid) {
        try {
            return ResponseEntity.ok(getService().paginateAllByTable(page, size, field, sort, status, tableid));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }
}
