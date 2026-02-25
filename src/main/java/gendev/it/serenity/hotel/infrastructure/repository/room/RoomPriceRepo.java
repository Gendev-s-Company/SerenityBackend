package gendev.it.serenity.hotel.infrastructure.repository.room;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomPrice;

@Repository
public interface RoomPriceRepo extends CommonRepository<RoomPrice, Integer> {
    List<RoomPrice> findByRoomIDAndStatus(String roomID, int status);

    Page<RoomPrice> findByRoomIDAndStatus(String roomID, int status, Pageable page);
}
