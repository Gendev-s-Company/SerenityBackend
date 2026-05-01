package gendev.it.serenity.restaurant.domain.dto.dish;

import java.util.List;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishType;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DishTypeDTO extends DTO<DishType> {

    private String typeID;
    private String name;
    private String description;
    private CompanyDTO company;
    private List<DishDTO> dishes;

    public DishTypeDTO(String typeID, String name, String description, CompanyDTO company, int status) {
        this.typeID = typeID;
        this.name = name;
        this.description = description;
        this.company = company;
        setStatus(status);
    }
    public DishTypeDTO(String typeID, String name, String description, CompanyDTO company, int status, List<DishDTO> list) {
        this.typeID = typeID;
        this.name = name;
        this.description = description;
        this.company = company;
        setStatus(status);
        this.dishes = list;
    }

    public void setName(String name) throws Exception {
        if (!isSkipValidation() && (name == null || name.isBlank())) {
            throw new Exception("Le type de plat doit avoir un nom");
        }
        this.name = name;
    }

    public void setCompany(CompanyDTO company) throws Exception {
        if (!isSkipValidation() && (company == null || company.getCompanyID() == null || company.getCompanyID().isBlank())) {
            throw new Exception("Le type de plat doit appartenir à une companie");
        }
        company.setSkipValidation(true);
        this.company = company;
    }

    public void setDishes(List<DishDTO> dishes) {
        this.dishes = dishes;
    }
    
    @Override
    public DishType dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        Company comp = null;
        if (company != null && company.getCompanyID()!=null) {
            comp = new Company(company.getCompanyID());
        }
        return new DishType(typeID, name, description, comp, getStatus());
    }

    public DishTypeDTO(String typeID) {
        this.typeID = typeID;
    }

    
}
