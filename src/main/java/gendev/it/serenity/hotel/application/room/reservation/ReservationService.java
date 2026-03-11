package gendev.it.serenity.hotel.application.room.reservation;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.room.reservation.ReservationDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.reservation.Reservation;
import gendev.it.serenity.hotel.infrastructure.repository.room.reservation.ReservationRepo;

@Service
public class ReservationService extends CommonService<Reservation, ReservationDTO, String, ReservationRepo>{

    public ReservationService(ReservationRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }
    public void updateState(String id, Integer state) throws Exception{
        if(state == null)
            throw new Exception("veuillez indiquer le state");
        ReservationDTO resa = findById(id, 0);
        if (resa == null) {
            throw new Exception("Reservation ID "+id+" introuvable");
        }
        Reservation toUpdate= resa.dtoToEntity();
        toUpdate.setState(state);
        getJpa().save(toUpdate);

    }
    
}
