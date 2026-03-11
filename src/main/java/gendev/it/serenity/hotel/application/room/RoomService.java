package gendev.it.serenity.hotel.application.room;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.utils.Utils;
import gendev.it.serenity.hotel.domain.dto.room.RoomDTO;
import gendev.it.serenity.hotel.domain.dto.room.RoomDetailDispoDTO;
import gendev.it.serenity.hotel.domain.dto.room.RoomDisponibilityDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.Room;
import gendev.it.serenity.hotel.infrastructure.repository.room.RoomRepo;

@Service
public class RoomService extends CommonService<Room, RoomDTO, String, RoomRepo>  {

    public RoomService(RoomRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

    /**
     * 
     * @param state -state chambre pour définir la liste des statuts à récupérer libre, occupé, reserver...
     * @param status
     * @param company
     * @param start
     * @param end
     * @return
     */
    public List<RoomDisponibilityDTO> findRoomAvalaibility(Integer[] state, Integer status, String company, LocalDateTime start, LocalDateTime end){
        status = status == null ? 0 : status;
        state = state == null ? Utils.roomState : state;
        return getJpa().findDisponibility(state, start, end, status, company);
    }

    public List<RoomDetailDispoDTO> findRoomDetailAvalaibility(Integer[] state, Integer status, String company, LocalDateTime start, LocalDateTime end){
        status = status == null ? 0 : status;
        state = state == null ? Utils.roomState : state;
        return getJpa().findDetailDisponibility(state, start, end, status, company);
    }
}
