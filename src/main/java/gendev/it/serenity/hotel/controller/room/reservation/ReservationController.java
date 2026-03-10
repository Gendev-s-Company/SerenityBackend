package gendev.it.serenity.hotel.controller.room.reservation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.hotel.application.room.reservation.ReservationService;
import gendev.it.serenity.hotel.domain.dto.room.reservation.ReservationDTO;

@RestController
@RequestMapping("hotel/room/reservation")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class ReservationController extends CommonController<ReservationDTO, ReservationService>{

    public ReservationController(ReservationService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }
    
}
