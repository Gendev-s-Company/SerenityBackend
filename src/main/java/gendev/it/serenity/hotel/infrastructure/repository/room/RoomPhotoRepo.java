package gendev.it.serenity.hotel.infrastructure.repository.room;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomPhoto;

public interface RoomPhotoRepo extends CommonRepository<RoomPhoto, String> {
    List<RoomPhoto> findByRoomIDAndStatus(String photoID, int status);
    Page<RoomPhoto> findByRoomIDAndStatus(String photoID, int status, Pageable page);
}
