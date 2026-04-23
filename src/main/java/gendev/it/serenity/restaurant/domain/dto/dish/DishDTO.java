package gendev.it.serenity.restaurant.domain.dto.dish;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.Dish;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DishDTO extends DTO<Dish> {
    private String dishID;
    private String name;
    private String description;
    private DishTypeDTO type;
    private int state;

    public DishDTO(String dishID, String name, String description, DishTypeDTO type, int state, int status) {
        this.dishID = dishID;
        this.name = name;
        this.description = description;
        this.type = type;
        this.state = state;
        setStatus(status);
    }

    public void setName(String name) throws Exception {
        if (!isSkipValidation() && (name == null || name.isBlank())) {
            throw new Exception("Le type de plat doit avoir un nom");
        }
        this.name = name;
    }

    public void setType(DishTypeDTO type) throws Exception {
        if (!isSkipValidation() && (type == null || type.getTypeID().isBlank())) {
            throw new Exception("Le  plat doit avoir un type de plat");
        }
        type.setSkipValidation(true);
        this.type = type;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public Dish dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        DishType t = type != null && type.getTypeID() != null ? new DishType(type.getTypeID()): null;
        
        return new Dish(dishID, name, description, t, state, getStatus());
    }

    
}
