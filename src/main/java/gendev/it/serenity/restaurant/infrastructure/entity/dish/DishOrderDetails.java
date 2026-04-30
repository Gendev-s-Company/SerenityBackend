package gendev.it.serenity.restaurant.infrastructure.entity.dish;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.common.utils.State;
import gendev.it.serenity.restaurant.domain.dto.dish.DishDTO;
import gendev.it.serenity.restaurant.domain.dto.dish.DishOrderDetailsDTO;
import gendev.it.serenity.users.domain.dto.UserResponseDTO;
import gendev.it.serenity.users.infrastructure.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "dishorderdetails")
public class DishOrderDetails extends BaseEntity<DishOrderDetailsDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderdetailsid")
    private String orderDetailsID;
    @Column(insertable = false, updatable = false)
    private String orderID;
    @Column(name = "unitprice")
    private BigDecimal unitPrice;
    @Column
    private int quantity;
    @Column(name = "dateorder")
    private LocalDateTime dateOrder = LocalDateTime.now();
    @Column
    private int state;

    @ManyToOne
    @JoinColumn(name = "dishid", nullable = false)
    private Dish dish;
    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderID", nullable = false)
    private DishOrder order;

    public DishOrderDetails(BigDecimal unitPrice, int quantity,
            LocalDateTime dateOrder, int state, Dish dish, Users user, int status) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.dateOrder = dateOrder;
        this.state = state;
        this.dish = dish;
        this.user = user;
        setStatus(status);
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return orderDetailsID;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }

    @Override
    public DishOrderDetailsDTO entityToDTO() {
        // TODO Auto-generated method stub
        DishDTO dishd = dish != null ? dish.entityToDTO() : null;
        UserResponseDTO userd = user != null ? user.entityToDTO() : null;

        return new DishOrderDetailsDTO(orderDetailsID, orderID, unitPrice, quantity,
                dateOrder, state, dishd, userd, status);
    }

    public List<DishOrderDetailsDTO> convertListToDTO(List<DishOrderDetails> list) {
        return list.stream()
                .filter(row -> row.getStatus() != State.DELETED)
                .map(row -> {
                    try {
                        DishOrderDetailsDTO detail = row.entityToDTO();
                        return detail;
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                })
                .toList();
    }
}
