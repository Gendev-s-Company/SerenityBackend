package gendev.it.serenity.hotel.domain.dto.room;

import org.springframework.web.multipart.MultipartFile;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.dto.FileDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomPhoto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomPhotoDTO extends DTO<RoomPhoto> {
    private String photoID;
    private String roomID;
    private String path;
    private FileDTO files;
    private MultipartFile uploadFile;

    

    public RoomPhotoDTO(String photoID, String roomID, String path, int status) {
        this.photoID = photoID;
        this.roomID = roomID;
        this.path = path;
        setStatus(status);
    }
    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }
    public void setRoomID(String roomID) throws Exception {
          if (roomID == null && !isSkipValidation()) {
            throw new Exception("Veuillez remplir le champ chambre");
        }
        this.roomID = roomID;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void setFiles(FileDTO files) {
        this.files = files;
    }
    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }
    @Override
    public RoomPhoto dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new RoomPhoto(photoID, path, roomID, getStatus());
    }
    
    
}
