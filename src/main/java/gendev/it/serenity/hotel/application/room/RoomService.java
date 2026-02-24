package gendev.it.serenity.hotel.application.room;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.room.RoomDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.Room;
import gendev.it.serenity.hotel.infrastructure.repository.room.RoomRepo;

@Service
public class RoomService extends CommonService<Room, RoomDTO, String, RoomRepo>  {

    public RoomService(RoomRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

}
