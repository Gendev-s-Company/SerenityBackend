package gendev.it.serenity.hotel.controller.room;

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
import gendev.it.serenity.hotel.application.room.RoomPhotoService;
import gendev.it.serenity.hotel.domain.dto.ActivityPhotoCreateDTO;
import gendev.it.serenity.hotel.domain.dto.room.RoomPhotoDTO;

@RestController
@RequestMapping("hotel/room/photo")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class RoomPhotoController extends CommonController<RoomPhotoDTO, RoomPhotoService> {

    public RoomPhotoController(RoomPhotoService service) {
        super(service);
        //TODO Auto-generated constructor stub
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

    @GetMapping("/byroom")
    public ResponseEntity<?> findAllByRoom(@RequestParam(name = "status", required = false) Integer status,
            @RequestParam String roomid) {
        try {
            return ResponseEntity.ok(getService().findAllByRoom(roomid, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    // endpoint a utiliser pour l'affichage des prix d'activité
    @GetMapping("/byroom/{page}/{size}")
    public ResponseEntity<?> findAllpaginateModelByRoom(@PathVariable("page") int page,
            @PathVariable("size") int size,
            @RequestParam(name = "field", defaultValue = "photoID", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam String roomid) {
        try {
            return ResponseEntity.ok(getService().paginateAllByRoom(page, size, field, sort, status, roomid));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }
}
