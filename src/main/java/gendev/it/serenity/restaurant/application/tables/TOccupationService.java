package gendev.it.serenity.restaurant.application.tables;

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

    public List<TOccupationDTO> findDisponibility(Integer status, String company, List<Integer> state,
            LocalDateTime start, LocalDateTime end) {
        status = status != null ? status : State.ACTIVE;
        state = state == null ? List.of(Utils.roomState) : state;
        List<TableOccupation> list = getJpa().findDisponibility(status, company, state, start, end);
        return conversion(list);
    }

    public Page<TOccupationDTO> findDisponibility(
            String company, List<Integer> state,
            LocalDateTime start, LocalDateTime end, int pageNumber, int pageSize, String field, String sort,
            Integer status) {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int statut = status != null ? status : State.ACTIVE;
        state = state == null ? List.of(Utils.roomState) : state;
        // Page<T> list = jpa.findAllByStatus(state, pageable);
        Page<TableOccupation> list = null;
        if (end != null && start != null) {
            list = getJpa().findDisponibility(statut, company, state, start, end, pageable);
        } else if (end == null) {
            list = getJpa().findDisponibilityDateEndnull(statut, company, state, start, pageable);
        } else if (start == null) {
            list = getJpa().findDisponibilityDateStartnull(statut, company, state, end, pageable);
        }
        Page<TOccupationDTO> result = list.map(entity -> {
            return entity.entityToDTO();
        });
        return result;

    }

    public Page<TOccupationDTO> findDisponibilityBytable(
        Integer status, String idtable, LocalDateTime start, LocalDateTime end, 
        int pageNumber, int pageSize, String field, String sort)  {
    Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
    int statut = status != null ? status : State.ACTIVE;
    Page<TableOccupation> list = null;
    if (end != null && start != null) {
        list = getJpa().findDisponibilityByIDtableAndDate(statut, idtable, start, end, pageable);
    } else if (end == null) {
        list = getJpa().findDisponibilityByIDtableAndDateEndnull(statut, idtable, start, pageable);
    } else if (start == null) {
        list = getJpa().findDisponibilityByIDtableAndDateStartnull(statut, idtable, end, pageable);
    }
    Page<TOccupationDTO> result = list.map(entity -> {
        return entity.entityToDTO();
    });
    return result;

    }
}
