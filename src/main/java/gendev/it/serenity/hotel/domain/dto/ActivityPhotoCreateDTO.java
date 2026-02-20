package gendev.it.serenity.hotel.domain.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityPhotoCreateDTO {
    private String activityID;
    private List<MultipartFile> uploadFile;

    public ActivityPhotoCreateDTO(String activityID, List<MultipartFile> uploadFile) {
        this.activityID = activityID;
        this.uploadFile = uploadFile;
    }
    
    
}
