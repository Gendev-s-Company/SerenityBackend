package gendev.it.serenity.hotel.application.room.reservation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.utils.State;
import gendev.it.serenity.common.utils.Utils;
import gendev.it.serenity.hotel.domain.dto.room.reservation.ReservationDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.reservation.Reservation;
import gendev.it.serenity.hotel.infrastructure.repository.room.reservation.ReservationRepo;

@Service
public class ReservationService extends CommonService<Reservation, ReservationDTO, String, ReservationRepo> {

    public ReservationService(ReservationRepo jpa) {
        super(jpa);
        // TODO Auto-generated constructor stub
    }

    public void updateState(String id, Integer state) throws Exception {
        if (state == null)
            throw new Exception("veuillez indiquer le state");
        ReservationDTO resa = findById(id, 0);
        if (resa == null) {
            throw new Exception("Reservation ID " + id + " introuvable");
        }
        Reservation toUpdate = resa.dtoToEntity();
        toUpdate.setState(state);
        getJpa().save(toUpdate);

    }

    public List<ReservationDTO> findDisponibility(Integer status, String company, List<Integer> state,
            LocalDateTime start, LocalDateTime end) {
         status = status != null ? status : State.ACTIVE;
         state = state == null ? List.of(Utils.roomState) : state;
        List<Reservation> list = getJpa().findDisponibility(status, company, state, start, end);
        return conversion(list);
    }

    public Page<ReservationDTO> findDisponibility(
            String company, List<Integer> state,
            LocalDateTime start, LocalDateTime end, int pageNumber, int pageSize, String field, String sort,
            Integer status) {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int statut = status != null ? status : State.ACTIVE;
         state = state == null ? List.of(Utils.roomState) : state;
        // Page<T> list = jpa.findAllByStatus(state, pageable);
        Page<Reservation> list = getJpa().findDisponibility(statut, company, state, start, end, pageable);
        Page<ReservationDTO> result = list.map(entity -> {
            return entity.entityToDTO();
        });
        return result;
    }
}
