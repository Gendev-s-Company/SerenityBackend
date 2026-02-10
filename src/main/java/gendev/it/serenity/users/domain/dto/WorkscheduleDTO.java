package gendev.it.serenity.users.domain.dto;

import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.users.infrastructure.entity.Workschedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WorkscheduleDTO extends DTO<Workschedule> {
    public Integer scheduleID =null;
    public String userID;
    public LocalDateTime  starttime;
    public LocalDateTime  endtime;
    public String color;

    
    public WorkscheduleDTO(Integer scheduleID, String userID, LocalDateTime  starttime, LocalDateTime  endtime,String color) {
        this.scheduleID = scheduleID;
        this.userID = userID;
        this.starttime = starttime;
        this.endtime = endtime;
        this.color=color;
    }


    public WorkscheduleDTO(String userID, LocalDateTime  starttime, LocalDateTime  endtime,String color) {
        this.userID = userID;
        this.starttime = starttime;
        this.endtime = endtime;
        this.color=color;
    }


    public void setUserID(String userID) throws Exception {
        if (userID == null || userID.isBlank()) {
            throw new Exception("Veuillez choisir un utilisateur existant");
        }
        this.userID = userID;
    }


    public void setStarttime(LocalDateTime starttime) throws Exception {
        if (starttime == null) {
            throw new Exception("La date de début est obligatoire");
        }
        this.starttime = starttime;
    }



    public void setEndtime(LocalDateTime endtime) throws Exception {
        this.endtime = endtime;
    }
        
    public void setColor(String color) throws Exception {
        if (color == null || color.isBlank()) {
            throw new Exception("Veuillez choisir une couleur valide");
        }
        this.color = color;
    }

    public Workschedule dtoToEntity()throws Exception {
        return new Workschedule(
                userID,
                starttime, 
                endtime,
                color
        );
    }

    public void validate() throws Exception {
        if (starttime == null) {
            throw new Exception("La date de début est obligatoire");
        }
        if(endtime!=null){
            if (endtime.isBefore(starttime)) {
                throw new Exception("La date de fin doit être postérieure à la date de début");
            }
        }
    }

}
