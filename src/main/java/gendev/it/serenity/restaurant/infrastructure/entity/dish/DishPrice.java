package gendev.it.serenity.restaurant.infrastructure.entity.dish;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.restaurant.domain.dto.dish.DishPriceDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "dishprice")
public class DishPrice extends BaseEntity<DishPriceDTO>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priceID;
    @Column
    private String dishID;
    @Column
    private BigDecimal price;

    @Column(name = "datechanged")
    private LocalDateTime dateChanged = LocalDateTime.now();


    
    
    public DishPrice(Integer priceID, String dishID, BigDecimal price, LocalDateTime dateChanged, int status) {
        this.priceID = priceID;
        this.dishID = dishID;
        this.price = price;
        this.dateChanged = dateChanged;
        setStatus(status);
    }

    @Override
    public Object getId() {
        // TODO Auto-generated method stub
        return priceID;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        DishPriceDTO d = (DishPriceDTO) dto;
        setDishID(d.getDishID());
        setPrice(d.getPrice());
        setDateChanged(LocalDateTime.now());
    }

    @Override
    public DishPriceDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new DishPriceDTO(priceID, dishID, price, dateChanged, status);
    }

}
