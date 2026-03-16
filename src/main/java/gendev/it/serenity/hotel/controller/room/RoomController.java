package gendev.it.serenity.hotel.controller.room;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.hotel.application.room.RoomService;
import gendev.it.serenity.hotel.domain.dto.room.RoomDTO;

@RestController
@RequestMapping("hotel/room")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class RoomController extends CommonController<RoomDTO,RoomService> {

    public RoomController(RoomService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }
    @GetMapping("/avalaible")
    public ResponseEntity<?> findAvalaibleByCompany(@RequestParam(name = "status", required = false) Integer status, @RequestParam String company,
        @RequestParam(required = false, name = "state") Integer[] state, 
        @RequestParam(required = false, name = "type") String type,
        @RequestParam LocalDateTime start, @RequestParam LocalDateTime end
    ) {
        try {

            return ResponseEntity.ok(type == null || type.equals("global")
                ? getService().findRoomAvalaibility(state, status, company, start, end)
                : getService().findRoomDetailAvalaibility(state, status, company, start, end)
        );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }
    // @GetMapping("/avalaible/{}")
    // public ResponseEntity<?> findAvalaibleRoomByCompany(@RequestParam(name = "status", required = false) Integer status, @RequestParam String company,
    //     @RequestParam(required = false, name = "state") Integer[] state, 
    //     @RequestParam(required = false, name = "type") String type,
    //     @RequestParam LocalDateTime start, @RequestParam LocalDateTime end
    // ) {
    //     try {

    //         return ResponseEntity.ok(type == null || type.equals("global")
    //             ? getService().findRoomAvalaibility(state, status, company, start, end)
    //             : getService().findRoomDetailAvalaibility(state, status, company, start, end)
    //     );
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

    //     }
    // }
}
