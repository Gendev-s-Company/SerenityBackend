package gendev.it.serenity.hotel.application.room;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.room.RoomTypeDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomType;
import gendev.it.serenity.hotel.infrastructure.repository.room.RoomTypeRepo;

@Service
public class RoomTypeService extends CommonService<RoomType, RoomTypeDTO, String, RoomTypeRepo>  {

    public RoomTypeService(RoomTypeRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }
    
}
