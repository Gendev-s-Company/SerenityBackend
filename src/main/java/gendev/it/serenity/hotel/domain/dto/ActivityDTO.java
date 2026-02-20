package gendev.it.serenity.hotel.domain.dto;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.hotel.infrastructure.entity.Activity;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ActivityDTO extends DTO<Activity> {
    private String activityID;
    private CompanyDTO company;
    private String name;
    private String description;
    
    public ActivityDTO(String activityID) {
        this.activityID = activityID;
    }

    public ActivityDTO(String activityID, CompanyDTO company, String name, String description, int status) {
        this.activityID = activityID;
        this.company = company;
        this.name = name;
        this.description = description;
        setStatus(status);
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public void setCompany(CompanyDTO company) throws Exception {
        if (company == null && !isSkipValidation()) {
            throw new Exception("Veuillez entrer le nom d'une companie valide ");
        }
        company.setSkipValidation(true);
        this.company = company;
    }

    public void setName(String name) throws Exception {
        if (name.isBlank() && !isSkipValidation()) {
            throw new Exception("Veuillez entrer un nom valide");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Activity dtoToEntity() {
        // TODO Auto-generated method stub
        Company comp = null;
        if (company!=null) {
            comp = company.dtoToEntity();
        }
        return new Activity(activityID, comp, name, description, getStatus());
    }

}
