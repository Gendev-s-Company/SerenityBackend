package gendev.it.serenity.restaurant.infrastructure.entity.tables;

import java.util.ArrayList;
import java.util.List;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.restaurant.domain.dto.dish.DishDTO;
import gendev.it.serenity.restaurant.domain.dto.dish.DishTypeDTO;
import gendev.it.serenity.restaurant.domain.dto.tables.TableDTO;
import gendev.it.serenity.restaurant.domain.dto.tables.TableTypeDTO;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "tabletypeid", insertable = false, updatable = false)
    private List<RestaurantTable> tables;


    public TableType(String tabletypeid) {
        this.tabletypeid = tabletypeid;
    }



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

    public TableTypeDTO entityToDTOWithTables() {
        // TODO Auto-generated method stub
        TableTypeDTO result = entityToDTO();
        try {
            result.setCompany(new CompanyDTO(company.getCompanyID()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        List<TableDTO> list = constructChildDTO();

        result.setTables(list);
        return result;
    }

    
    private List<TableDTO> constructChildDTO(){
        if (tables == null || tables.size()==0) {
            return new ArrayList<TableDTO>();
        }
        return tables.stream()
        .map(m -> {
            TableDTO dto = m.entityToDTO();
            dto.removeType();
            return dto;
        }).toList();
    }
    
}
