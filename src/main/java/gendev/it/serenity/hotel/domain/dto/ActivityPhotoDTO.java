package gendev.it.serenity.hotel.domain.dto;


import org.springframework.web.multipart.MultipartFile;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.dto.FileDTO;
import gendev.it.serenity.hotel.infrastructure.entity.Activity;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityPhoto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityPhotoDTO extends DTO<ActivityPhoto> {
    private String photoID;
    private ActivityDTO activity;
    private String path;
    private FileDTO files;
    private MultipartFile uploadFile;
    public ActivityPhotoDTO(String photoID, ActivityDTO activity, String path, int status) {
        this.photoID = photoID;
        this.activity = activity;
        this.path = path;
        setStatus(status);
    }
    
    @Override
    public ActivityPhoto dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        Activity p = null;
        if (activity != null) {
            // p = activity.dtoToEntity();   
            p = new Activity(activity.getActivityID());
        }
        return new ActivityPhoto(photoID, p, path, getStatus());
    }

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    public void setActivity(ActivityDTO activity) throws Exception {
         if (activity == null && !isSkipValidation()) {
            throw new Exception("Veuillez remplir le champ activité");
        }
        activity.setSkipValidation(true);
        this.activity = activity;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFiles(FileDTO files) {
        this.files = files;
    }

    public void setUploadFile(MultipartFile uploadFiles) {
        this.uploadFile = uploadFiles;
    }
    
}
