package gendev.it.serenity.restaurant.domain.dto.tables;

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

    
    public TableTypeDTO(String tabletypeid, String name, String description, CompanyDTO company, int status) {
        this.tabletypeid = tabletypeid;
        this.name = name;
        this.description = description;
        this.company = company;
        setStatus(status);
    }


    @Override
    public TableType dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        Company comp = company !=null ? company.dtoToEntity() : null;
        return new TableType(tabletypeid, name, description, comp, getStatus());
    }
    
}
