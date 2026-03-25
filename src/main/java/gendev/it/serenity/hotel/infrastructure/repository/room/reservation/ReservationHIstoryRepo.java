package gendev.it.serenity.hotel.infrastructure.repository.room.reservation;

import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.hotel.infrastructure.entity.room.reservation.ReservationHistory;
import java.util.List;


@Repository
public interface ReservationHIstoryRepo extends CommonRepository<ReservationHistory, Integer>{
    List<ReservationHistory> findByReservationIDAndStatus(String reservationID, int status);
}
