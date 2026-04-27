package gendev.it.serenity.restaurant.infrastructure.entity.dish;

import java.util.ArrayList;
import java.util.List;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.restaurant.domain.dto.dish.DishDTO;
import gendev.it.serenity.restaurant.domain.dto.dish.DishTypeDTO;
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
@Table(name = "dishtype")
public class DishType extends BaseEntity<DishTypeDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String typeID;
    @Column
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private Company company;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeid", insertable = false, updatable = false)
    private List<Dish> dishes;

    public DishType(String typeID) {
        this.typeID = typeID;
    }

    public DishType(String typeID, String name, String description, Company company, int status) {
        this.typeID = typeID;
        this.name = name;
        this.description = description;
        this.company = company;
        setStatus(status);
    }

    @Override
    public Object getId() {
        // TODO Auto-generated method stub
        return typeID;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        DishTypeDTO typed = (DishTypeDTO) dto;
        setDescription(typed.getDescription());
        setName(typed.getName());
        if (typed.getCompany() != null && typed.getCompany().getCompanyID() != null) {
            setCompany(new Company(typed.getCompany().getCompanyID()));
        }
    }

    @Override
    public DishTypeDTO entityToDTO() {
        // TODO Auto-generated method stub
        CompanyDTO dto = null;
        if (company != null) {
            dto = company.entityToDTO();
        }
        return new DishTypeDTO(typeID, name, description, dto, getStatus());
    }

    public DishTypeDTO entityToDTOWithDishes() {
        // TODO Auto-generated method stub
        DishTypeDTO result = entityToDTO();
        List<DishDTO> list = constructChildDTO();

        result.setDishes(list);
        return result;
    }

    private List<DishDTO> constructChildDTO(){
        if (dishes == null | dishes.size()==0) {
            return new ArrayList<DishDTO>();
        }
        return dishes.stream()
        .map(m -> {
            DishDTO dto = m.entityToDTO();
            dto.removeType();
            return dto;
        }).toList();
    }
}
