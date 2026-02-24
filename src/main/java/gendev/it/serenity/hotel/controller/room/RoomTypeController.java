package gendev.it.serenity.hotel.controller.room;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.hotel.application.room.RoomTypeService;
import gendev.it.serenity.hotel.domain.dto.room.RoomTypeDTO;

@RestController
@RequestMapping("hotel/room/type")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class RoomTypeController extends CommonController<RoomTypeDTO,RoomTypeService> {

    public RoomTypeController(RoomTypeService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }
    
}
