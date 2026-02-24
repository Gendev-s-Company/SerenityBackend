package gendev.it.serenity.hotel.domain.dto.room;
import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomType;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RoomTypeDTO extends DTO<RoomType>{
    private String typeID;
    private CompanyDTO company;
    private String name;
    private String description;

    


    public RoomTypeDTO(String typeID) {
        this.typeID = typeID;
    }
    public RoomTypeDTO(String typeID, CompanyDTO company, String name, String description, int status) {
        this.typeID = typeID;
        this.company = company;
        this.name = name;
        this.description = description;
        setStatus(status);
    }
    public void setCompany(CompanyDTO company) throws Exception {
        if (company == null && !isSkipValidation()) {
            throw new Exception("Veuillez entrer le nom d'une companie valide pour le type de chambre");
        }
        company.setSkipValidation(true);
        this.company = company;
    }

    public void setName(String name) throws Exception {
        if (name.isBlank() && !isSkipValidation()) {
            throw new Exception("Veuillez entrer un nom valide pour le type de chambre");
        }
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }
    @Override
    public RoomType dtoToEntity() throws Exception {
        Company comp = null;
        // TODO Auto-generated method stub
        if (company!=null && company.getCompanyID()!=null) {
            comp = new Company(company.getCompanyID());
        }
        return new RoomType(typeID, comp, name, description, getStatus());
    }
    
}
