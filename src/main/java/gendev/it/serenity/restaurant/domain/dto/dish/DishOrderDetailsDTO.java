package gendev.it.serenity.restaurant.domain.dto.dish;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.Dish;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrder;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrderDetails;
import gendev.it.serenity.users.domain.dto.UserResponseDTO;
import gendev.it.serenity.users.infrastructure.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DishOrderDetailsDTO extends DTO<DishOrderDetails> {
    private String orderDetailsID;
    private String orderID;
    private BigDecimal unitPrice;
    private int quantity;
    private LocalDateTime dateOrder = LocalDateTime.now();
    private int state;
    private DishDTO dish;
    private UserResponseDTO user;

    public DishOrderDetailsDTO(String orderDetailsID, String orderID, BigDecimal unitPrice, int quantity,
            LocalDateTime dateOrder, int state, DishDTO dish, UserResponseDTO user, int status) {
        this.orderDetailsID = orderDetailsID;
        this.orderID = orderID;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.dateOrder = dateOrder;
        this.state = state;
        this.dish = dish;
        this.user = user;
        setStatus(status);
    }

    @Override
    public DishOrderDetails dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        Dish d = dish != null ? dish.dtoToEntity() : null;
        Users ud = user != null ? user.dtoToEntity() : null;
        DishOrderDetails ndetail = new DishOrderDetails(unitPrice, quantity,
                dateOrder, state, d, ud, getStatus());
            ndetail.setOrder(new DishOrder(orderID));
            ndetail.setOrderID(orderID);
        return ndetail;
    }

}
