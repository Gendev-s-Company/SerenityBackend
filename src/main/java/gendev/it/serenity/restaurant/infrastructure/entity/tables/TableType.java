package gendev.it.serenity.restaurant.infrastructure.entity.tables;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.restaurant.domain.dto.tables.TableTypeDTO;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tabletype")
public class TableType extends BaseEntity<TableTypeDTO>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String tabletypeid;
    @Column
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private Company company;
    public TableType(String tabletypeid, String name, String description, Company company, int status) {
        this.tabletypeid = tabletypeid;
        this.name = name;
        this.description = description;
        this.company = company;
        setStatus(status);
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return tabletypeid;
    }
    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        TableTypeDTO c = (TableTypeDTO) dto;
        setDescription(c.getDescription());
        setName(c.getName());
    }
    @Override
    public TableTypeDTO entityToDTO() {
        // TODO Auto-generated method stub
        CompanyDTO comp = company != null ? company.entityToDTO() : null;
        return new TableTypeDTO(tabletypeid, name, description, comp, status);
    }
    
}
