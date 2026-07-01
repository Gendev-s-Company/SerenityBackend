package gendev.it.serenity.pack.dto;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.pack.infrastructure.models.PackRestoDetails;
import gendev.it.serenity.restaurant.domain.dto.dish.DishDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PackRestoDTO extends DTO<PackRestoDetails> {
    private Integer id;
    private String dishID; // utilisation pour create or update
    private Integer quantity;
    private DishDTO dish; // utilisation pour read only

    public PackRestoDTO(Integer id, Integer quantity, DishDTO dish) {
        this.id = id;
        this.quantity = quantity;
        this.dish = dish;
        this.dishID = dish != null ? dish.getDishID() : null;
    }

    public PackRestoDTO(String dishID, Integer quantity) {
        this.dishID = dishID;
        this.quantity = quantity;
    }

    @Override
    public PackRestoDetails dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new PackRestoDetails(dishID, quantity);
    }

    public PackRestoDetails dtoToEntityAvoidException() {
        return new PackRestoDetails(dishID, quantity);
    }

}
