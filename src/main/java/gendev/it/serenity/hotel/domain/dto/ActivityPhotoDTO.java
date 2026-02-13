package gendev.it.serenity.hotel.domain.dto;

import gendev.it.serenity.common.dto.DTO;
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
            p = activity.dtoToEntity();   
        }
        return new ActivityPhoto(photoID, p, path, getStatus());
    }
    
}
