package gendev.it.serenity.restaurant.infrastructure.entity.dish;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.restaurant.domain.dto.dish.DishDTO;
import gendev.it.serenity.restaurant.domain.dto.dish.DishTypeDTO;
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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dish extends BaseEntity<DishDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String dishID;
    @Column
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "typeid")
    private DishType type;
    private int state;

    public Dish(String dishID, String name, String description, DishType type, int state, int status) {
        this.dishID = dishID;
        this.name = name;
        this.description = description;
        this.type = type;
        this.state = state;
        setStatus(status);
    }

    @Override
    public Object getId() {
        // TODO Auto-generated method stub
        return dishID;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        DishDTO t = (DishDTO) dto;
        setName(t.getName());
        setDescription(t.getDescription());
        if (t.getType() != null && t.getType().getTypeID() != null) {
            setType(new DishType(t.getType().getTypeID()));
        }
    }

    @Override
    public DishDTO entityToDTO() {
        // TODO Auto-generated method stub

        DishTypeDTO t = type != null && type.getTypeID() != null ? type.entityToDTO() : null;
        return new DishDTO(dishID, name, description, t, state, getStatus());
    }

}
