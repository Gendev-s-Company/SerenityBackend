package gendev.it.serenity.hotel.application.room;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.room.RoomPriceDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomPrice;
import gendev.it.serenity.hotel.infrastructure.repository.room.RoomPriceRepo;

@Service
public class RoomPriceService extends CommonService<RoomPrice, RoomPriceDTO, Integer, RoomPriceRepo> {

    public RoomPriceService(RoomPriceRepo jpa) {
        super(jpa);
        // TODO Auto-generated constructor stub
    }

    public List<RoomPriceDTO> findAllByRoom(String roomID, Integer state) throws Exception {
        int status = state != null ? state : 0;
        List<RoomPrice> result = getJpa().findByRoomIDAndStatus(roomID, status);
        return super.conversion(result);
    }

    public Page<RoomPriceDTO> paginateAllByRoom(int pageNumber, int pageSize, String field, String sort,
            Integer status, String roomID) throws Exception {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findByRoomIDAndStatus(roomID, state, pageable)
                .map(p -> (RoomPriceDTO) p.entityToDTO());
    }
}
