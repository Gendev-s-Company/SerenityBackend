package gendev.it.serenity.hotel.domain.dto.room;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.Room;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO extends DTO<Room> {
    private String roomID;
    private String name;
    private String description;
    private RoomTypeDTO type;
    private Integer peoples;
    private Integer bed;
    private Integer state;

    
    public RoomDTO(String roomID) {
        this.roomID = roomID;
    }

    public RoomDTO(String roomID, String name, String description, RoomTypeDTO type, Integer peoples, Integer bed,
            Integer state, int status) {
        this.roomID = roomID;
        this.name = name;
        this.description = description;
        this.type = type;
        this.peoples = peoples;
        this.bed = bed;
        this.state = state;
        setStatus(status);
    }

    public void setName(String name) throws Exception {
        if (name.isBlank() && !isSkipValidation()) {
            throw new Exception("Veuillez entrer un nom valide pour une chambre");
        }
        this.name = name;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(RoomTypeDTO type) throws Exception {
        if (type ==null && !isSkipValidation()) {
            throw new Exception("Veuillez choisir un type de chambre");
        }
        type.setSkipValidation(true);
        this.type = type;
    }

    public void setPeoples(Integer peoples) {
        this.peoples = peoples;
    }

    public void setBed(Integer bed) {
        this.bed = bed;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public Room dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        RoomType t = null;
        if (type != null) {
            t = new RoomType(type.getTypeID());
        }
        return new Room(roomID, name, description, t, peoples, bed, state, getStatus());
    }
    
    
}
