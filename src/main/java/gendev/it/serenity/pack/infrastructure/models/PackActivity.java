package gendev.it.serenity.pack.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.hotel.domain.dto.ActivityDTO;
import gendev.it.serenity.hotel.infrastructure.entity.Activity;
import gendev.it.serenity.pack.dto.PackActivityDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "packactivity")
public class PackActivity extends BaseEntity<PackActivityDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "activityid")
    private String activityID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activityid", insertable = false, updatable = false)
    private Activity activity;

    @Column
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packid", nullable = false)
    @JsonManagedReference
    private Pack pack;

    public PackActivity(Integer id, String activityID, Integer duration) {
        this.id = id;
        this.activityID = activityID;
        this.duration = duration;
    }

    public PackActivity(Integer id) {
        this.id = id;
    }

    public PackActivity(String activityID, Integer duration) {
        this.activityID = activityID;
        this.duration = duration;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }

    @Override
    public PackActivityDTO entityToDTO() {
        ActivityDTO dto = activity != null ? activity.entityToDTO() : null;
        // TODO Auto-generated method stub
        return new PackActivityDTO(id, duration, dto);
    }
}
