package gendev.it.serenity.hotel.application.room.reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.time.Duration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.utils.State;
import gendev.it.serenity.common.utils.Utils;
import gendev.it.serenity.hotel.application.room.RoomService;
import gendev.it.serenity.hotel.domain.dto.room.RoomDTO;
import gendev.it.serenity.hotel.domain.dto.room.reservation.ResaPriceDTO;
import gendev.it.serenity.hotel.domain.dto.room.reservation.ReservationDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.Room;
import gendev.it.serenity.hotel.infrastructure.entity.room.reservation.Reservation;
import gendev.it.serenity.hotel.infrastructure.entity.room.reservation.ReservationHistory;
import gendev.it.serenity.hotel.infrastructure.repository.room.reservation.ReservationRepo;
import jakarta.transaction.Transactional;

import java.math.RoundingMode;

@Service
public class ReservationService extends CommonService<Reservation, ReservationDTO, String, ReservationRepo> {
    private final RoomService service;
    private final ReservationHistoryService history;
    public ReservationService(ReservationRepo jpa, RoomService service, ReservationHistoryService history) {
        super(jpa);
        this.service = service;
        this.history = history;
        // TODO Auto-generated constructor stub
    }

    
    @Override
    public ReservationDTO save(ReservationDTO model) throws Exception {
        // TODO Auto-generated method stub
        BigDecimal accompte = calculAccompte(model.getPrice(), BigDecimal.valueOf(model.getAccountRated()));
        if (accompte.compareTo(model.getAccountPaid()) == 0) {
            model.setState(2);
        }
        return super.save(model);
    }

    @Transactional
    public void updateState(String id, Integer state) throws Exception {
        if (state == null)
            throw new Exception("veuillez indiquer le state");
        ReservationDTO resa = findById(id, 0);
        if (resa == null) {
            throw new Exception("Reservation ID " + id + " introuvable");
        }
        Reservation toUpdate = resa.dtoToEntity();
        toUpdate.setState(state);
        toUpdate =  getJpa().save(toUpdate);
        Reservation toArchive = toUpdate;
        toArchive.setState(resa.getState());
        archivateReservation(toArchive);
    }

    private void archivateReservation(Reservation resa) throws Exception{
        ReservationHistory archive = new ReservationHistory(resa.getReservationID(), LocalDateTime.now(), resa.getState(), 0);
        history.getJpa().save(archive);
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
        Page<Reservation> list = null;
        if (end != null && start != null) {
            list = getJpa().findDisponibility(statut, company, state, start, end, pageable);
        } else if (end == null) {
            list = getJpa().findDisponibilityDateEndnull(statut, company, state, start, pageable);
        } else if (start == null) {
            list = getJpa().findDisponibilityDateStartnull(statut, company, state, end, pageable);
        }
        Page<ReservationDTO> result = list.map(entity -> {
            return entity.entityToDTO();
        });
        return result;
    }

    // récupérer le prix total, prix d'accompte à payer et la deadline pour la
    // réservation
    public ResaPriceDTO validatePriceReservation(String roomid, LocalDateTime start, LocalDateTime end)
            throws Exception {
        RoomDTO room = service.findById(roomid, 0);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now;
        BigDecimal nightPrice = room.getRoomPrice().getNightPrice();
        BigDecimal hourPrice = room.getRoomPrice().getHourPrice();
        BigDecimal result = null;
        if (now.toLocalDate().isBefore(start.toLocalDate())) {
            deadline = start.minusDays(1);
        }
        Duration duration = Duration.between(start, end);

        long hours = duration.toHours();

        if (hours >= 24) {
            long nights = hours / 24;
            result = nightPrice.multiply(BigDecimal.valueOf(nights));
        } else {
            result = hourPrice.multiply(BigDecimal.valueOf(hours));
        }

        // BigDecimal accompte = result.multiply(room.getRoomPrice().getAccountRate())
        //         .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal accompte = calculAccompte(result, room.getRoomPrice().getAccountRate());

        ResaPriceDTO res = new ResaPriceDTO(result, accompte, deadline);
        return res;
    }
    private BigDecimal calculAccompte(BigDecimal price, BigDecimal rate){
        return price.multiply(rate)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
