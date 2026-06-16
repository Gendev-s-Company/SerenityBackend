package gendev.it.serenity.pack.dto;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.pack.infrastructure.models.PackHotelDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PackHotelDetailDTO extends DTO<PackHotelDetails> {

    private Integer id;
    private String roomID;
    private Integer duration;

    
    public PackHotelDetailDTO(String roomID, Integer duration) {
        this.roomID = roomID;
        this.duration = duration;
    }


    @Override
    public PackHotelDetails dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new PackHotelDetails(roomID, duration);
    }

    public PackHotelDetails dtoToEntityAvoidException(){
        return new PackHotelDetails(roomID, duration);
    }

}
