package gendev.it.serenity.restaurant.domain.dto.tables;

import java.util.List;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableType;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TableTypeDTO extends DTO<TableType>{

    private String tabletypeid;
    private String name;
    private String description;
    private CompanyDTO company;
    private List<TableDTO> tables;

    
    public TableTypeDTO(String tabletypeid, String name, String description, CompanyDTO company, int status) {
        this.tabletypeid = tabletypeid;
        this.name = name;
        this.description = description;
        this.company = company;
        setStatus(status);
    }


    public TableTypeDTO(String tabletypeid, String name, String description, CompanyDTO company,int status,
        List<TableDTO> tables) {
        this.tabletypeid = tabletypeid;
        this.name = name;
        this.description = description;
        this.company = company;
        setStatus(status);
        this.tables = tables;
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

    public void setTables(List<TableDTO> tables) {
        this.tables = tables;
    }

    @Override
    public TableType dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        Company comp = company !=null ? company.dtoToEntity() : null;
        return new TableType(tabletypeid, name, description, comp, getStatus());
    }

    public TableTypeDTO(String tabletypeid) {
        this.tabletypeid = tabletypeid;
    }
    
}
