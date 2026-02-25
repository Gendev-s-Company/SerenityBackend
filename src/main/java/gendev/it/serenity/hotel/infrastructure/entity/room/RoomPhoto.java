package gendev.it.serenity.hotel.infrastructure.entity.room;
import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.hotel.domain.dto.room.RoomPhotoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roomphoto")
public class RoomPhoto extends BaseEntity<RoomPhotoDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String photoID;
    @Column
    private String path;
    @Column
    private String roomID;

    public RoomPhoto(String photoID) {
        this.photoID = photoID;
    }

  

    public RoomPhoto(String photoID, String path, String roomID, int status) {
        this.photoID = photoID;
        this.path = path;
        this.roomID = roomID;
        setStatus(status);
    }



    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return photoID;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        RoomPhotoDTO d = (RoomPhotoDTO) dto;
        setRoomID(d.getRoomID());
        setPath(d.getPath());
    }

    @Override
    public RoomPhotoDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new RoomPhotoDTO(photoID, roomID, path, status);
    }
    
}
