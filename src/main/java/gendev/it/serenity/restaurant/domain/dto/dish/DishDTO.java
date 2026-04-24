package gendev.it.serenity.restaurant.domain.dto.dish;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.Dish;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishPrice;
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
    private List<DishPhotoDTO> photos;
    private DishPriceDTO price;

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
    public void setPhotos(List<DishPhotoDTO> photos) {
        this.photos = photos;
    }
    public void setPrice(DishPriceDTO price) {
        this.price = price;
    }

    @Override
    public Dish dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        DishType t = type != null && type.getTypeID() != null ? new DishType(type.getTypeID()): null;
        
        return new Dish(dishID, name, description, t, state, getStatus());
    }

    public DishPriceDTO findLastPrice(List<DishPrice> list) {
        Optional<DishPrice> result = list.stream()
                .max(Comparator.comparing(DishPrice::getDateChanged));
        return result.isPresent() ? result.get().entityToDTO() : new DishPriceDTO(null, dishID, BigDecimal.ZERO, null, state);
    }

    
}
