package gendev.it.serenity.hotel.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.hotel.domain.dto.ActivityDTO;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Activity extends BaseEntity<ActivityDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String activityID;
    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private Company company;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column
    private Boolean isindividual;

    
    public Activity(String activityID) {
        this.activityID = activityID;
    }

    public Activity(String activityID, Company company, String name, String description, int status) {
        this.activityID = activityID;
        this.company = company;
        this.name = name;
        this.description = description;
        setStatus(status);
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return activityID;
    }

    public Boolean getIsIndividual(){
        return isindividual;
    }

    @Override
    public void updateFromDTO(DTO cdto) {
        // TODO Auto-generated method stub
        ActivityDTO dto = (ActivityDTO) cdto;
        setCompany(dto.getCompany().dtoToEntity());
        setName(dto.getName());
        setDescription(dto.getDescription());
        setIsindividual(dto.getIsIndividual());
    }

    @Override
    public ActivityDTO entityToDTO() {
        // TODO Auto-generated method stub
        CompanyDTO comp = null;
        if (company!=null) {
            comp = company.entityToDTO();
        }
        ActivityDTO act = new ActivityDTO(activityID, comp, name, description, getStatus());
        act.setIsindividual(isindividual);
        return act;
    }
}
