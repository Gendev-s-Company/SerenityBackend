package gendev.it.serenity.pack.dto;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.hotel.domain.dto.ActivityDTO;
import gendev.it.serenity.pack.infrastructure.models.PackActivity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PackActivityDTO extends DTO<PackActivity> {
    private Integer id;
    private String activityID; //utilisation pour create or update
    private Integer duration;

    private ActivityDTO activity; // utilisation pour read only
    
    public PackActivityDTO(String activityID, Integer duration) {
        this.activityID = activityID;
        this.duration = duration;
    }
    
   

    public PackActivityDTO(Integer id, Integer duration, ActivityDTO activity) {
        this.id = id;
        this.duration = duration;
        this.activity = activity;
        this.activityID = activity !=null ? activity.getActivityID() : null;
    }



    public PackActivityDTO(Integer id) {
        this.id = id;
    }

    @Override
    public PackActivity dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new PackActivity(activityID, duration);
    }
    public PackActivity dtoToEntityAvoidException() {
        // TODO Auto-generated method stub
        return new PackActivity(activityID, duration);
    }

}
