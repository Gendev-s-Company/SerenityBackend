package gendev.it.serenity.pack.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class PackActivity extends BaseEntity {
    private Integer id;
    @Column(name = "activityid")
    private String activityID;
    @Column
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packid", nullable = false)
    @JsonManagedReference
    private Pack pack;

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }

    @Override
    public DTO entityToDTO() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'entityToDTO'");
    }
}
