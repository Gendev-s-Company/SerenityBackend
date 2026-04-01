package gendev.it.serenity.restaurant.application.tables;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.room.reservation.ReservationDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.reservation.Reservation;
import gendev.it.serenity.restaurant.domain.dto.tables.TOccupationDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableOccupation;
import gendev.it.serenity.restaurant.infrastructure.repository.tables.TOccupationRepo;
import jakarta.transaction.Transactional;

@Service
public class TOccupationService extends CommonService<TableOccupation, TOccupationDTO, String, TOccupationRepo>{

    public TOccupationService(TOccupationRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

    @Transactional
    public void updateState(String id, Integer state) throws Exception {
        if (state == null)
            throw new Exception("veuillez indiquer le state");
        TOccupationDTO resa = findById(id, 0);
        if (resa == null) {
            throw new Exception("Reservation ID " + id + " introuvable");
        }
        TableOccupation toUpdate = resa.dtoToEntity();
        toUpdate.setState(state);
        TableOccupation toArchive =  getJpa().save(toUpdate);
        // toArchive = toArchive.entityToDTO().dtoToEntity();
        // toArchive.setState(resa.getState());
        // archivateReservation(toArchive);
    }
}
