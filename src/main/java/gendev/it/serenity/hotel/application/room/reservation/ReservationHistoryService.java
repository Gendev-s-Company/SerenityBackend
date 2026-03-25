package gendev.it.serenity.hotel.application.room.reservation;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.room.reservation.ReservationHistoryDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.reservation.ReservationHistory;
import gendev.it.serenity.hotel.infrastructure.repository.room.reservation.ReservationHIstoryRepo;

@Service
public class ReservationHistoryService extends CommonService<ReservationHistory, ReservationHistoryDTO, Integer, ReservationHIstoryRepo>{

    public ReservationHistoryService(ReservationHIstoryRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

}
