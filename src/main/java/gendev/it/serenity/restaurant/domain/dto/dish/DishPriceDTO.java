package gendev.it.serenity.restaurant.domain.dto.dish;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DishPriceDTO extends DTO<DishPrice> {
    private Integer priceID;
    private String dishID;
    private BigDecimal price;
    private LocalDateTime dateChanged = LocalDateTime.now();

    public DishPriceDTO(Integer priceID, String dishID, BigDecimal price, LocalDateTime dateChanged, int status) {
        this.priceID = priceID;
        this.dishID = dishID;
        this.price = price;
        this.dateChanged = dateChanged;
        setStatus(status);
    }

    public void setDishID(String dishID) throws Exception {
        if (!isSkipValidation() && ( dishID == null || dishID.isBlank())) {
            throw new Exception("veuillez attribuer le prix au plat");
        }
        this.dishID = dishID;
    }

    @Override
    public DishPrice dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new DishPrice(priceID, dishID, price, dateChanged, getStatus());
    }

    public void setPriceID(Integer priceID) {
        this.priceID = priceID;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDateChanged(LocalDateTime dateChanged) {
        this.dateChanged = dateChanged;
    }

    

    

    
}
