package gendev.it.serenity.hotel.infrastructure.entity;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.hotel.domain.dto.ActivityDTO;
import gendev.it.serenity.hotel.domain.dto.ActivityPhotoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
@Table(name = "activityphoto")
public class ActivityPhoto extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String photoID;
    @ManyToOne
    @JoinColumn(name = "activityID", nullable = false)
    private Activity activity;
    @Column
    private String path;

    

    public ActivityPhoto(String photoID) {
        this.photoID = photoID;
    }
    public ActivityPhoto(String photoID, Activity activity, String path, int status) {
        this.photoID = photoID;
        this.activity = activity;
        this.path = path;
        setStatus(status);
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return photoID;
    }
    @Override
    public void updateFromDTO(DTO cdto) {
        // TODO Auto-generated method stub
        ActivityPhotoDTO dto = (ActivityPhotoDTO) cdto;
        setPath(dto.getPath());
        setActivity(new Activity(dto.getActivity().getActivityID()));
    }
    @Override
    public ActivityPhotoDTO entityToDTO() {
        // TODO Auto-generated method stub
        ActivityDTO act = null;
        if (activity != null) {
            act = activity.entityToDTO();
        }
        return new ActivityPhotoDTO(photoID, act, path, status);
    }
}
