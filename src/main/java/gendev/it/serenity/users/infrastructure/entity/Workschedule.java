package gendev.it.serenity.users.infrastructure.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.users.domain.dto.WorkscheduleDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
public class Workschedule extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer scheduleID;

    @JoinColumn(name = "userid", nullable = false)
    private String userID;

    @Column(nullable = false)
    LocalDateTime  starttime;

    @Column
    LocalDateTime endtime;

    protected Workschedule() {}

    

    public Workschedule(String userID, LocalDateTime  starttime, LocalDateTime endtime) {
        this.userID = userID;
        this.starttime = starttime;
        this.endtime = endtime;
    }


    @Override
    public Object getId() {
        return scheduleID;
    }

    @Override
    public void updateFromDTO(DTO wdto) {
        WorkscheduleDTO dto= (WorkscheduleDTO) wdto;
        setUserID(dto.getUserID());
        setStarttime(dto.getStarttime());
        setEndtime(dto.getEndtime());
        setStatus(dto.getStatus());
    }

    @Override
    public DTO entityToDTO() {
        return new WorkscheduleDTO(scheduleID,userID,starttime,endtime);
    }

}
