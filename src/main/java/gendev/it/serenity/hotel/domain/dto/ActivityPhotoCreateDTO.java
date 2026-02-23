package gendev.it.serenity.hotel.domain.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ActivityPhotoCreateDTO {
    private String activityID;
    private MultipartFile[] uploadFile;

    public ActivityPhotoCreateDTO(String activityID, MultipartFile[] uploadFile) {
        this.activityID = activityID;
        this.uploadFile = uploadFile;
    }

    public void setActivityID(String activityID) throws Exception {
        if (activityID == null || activityID.isBlank()) {
            throw new Exception("Veuillez mettre un activity");
        }
        this.activityID = activityID;
    }

    public void setUploadFile(MultipartFile[] uploadFile) throws Exception {
         if (uploadFile == null || uploadFile.length == 0) {
            throw new Exception("Veuillez mettre au moins une photo");
        }
        this.uploadFile = uploadFile;
    }
    
    
}
