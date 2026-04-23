package gendev.it.serenity.restaurant.application.tables;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.utils.Utils;
import gendev.it.serenity.hotel.domain.dto.room.RoomDetailDispoDTO;
import gendev.it.serenity.hotel.domain.dto.room.RoomDisponibilityDTO;
import gendev.it.serenity.restaurant.domain.dto.tables.TableDTO;
import gendev.it.serenity.restaurant.domain.dto.tables.TableDetailDispoDTO;
import gendev.it.serenity.restaurant.domain.dto.tables.TableDsiponibilityDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.RestaurantTable;
import gendev.it.serenity.restaurant.infrastructure.repository.tables.TableRepo;

@Service
public class TableService extends CommonService<RestaurantTable, TableDTO, String, TableRepo> {

    public TableService(TableRepo jpa) {
        super(jpa);
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     * @param state   -state chambre pour définir la liste des statuts à récupérer
     *                libre, occupé, reserver...
     * @param status
     * @param company
     * @param start
     * @param end
     * @return
     */
    public List<TableDsiponibilityDTO> findTableAvalaibility(Integer[] state, Integer status, String company,
            LocalDateTime start, LocalDateTime end) {
        status = status == null ? 0 : status;
        state = state == null ? Utils.roomState : state;
        return getJpa().findDisponibility(state, start, end, status, company);
    }

    public List<?> findTableDetailAvalaibility(Integer[] state, Integer status, String company,
            LocalDateTime start, LocalDateTime end) {
        status = status == null ? 0 : status;
        state = state == null ? Utils.roomState : state;
        boolean isSameDate = start.toLocalDate().equals(end.toLocalDate());
        return isSameDate ? getJpa().findDetailDayDisponibility(state, start, end, status, company)
                : getJpa().findDetailDisponibility(state, start, end, status, company);
    }
}
