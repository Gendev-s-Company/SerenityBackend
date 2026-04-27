package gendev.it.serenity.restaurant.domain.dto.dish;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.restaurant.domain.dto.tables.TOccupationDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrder;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrderDetails;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableOccupation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DishOrderDTO extends DTO<DishOrder> {
    private String orderID;
    private BigDecimal totalPrice;
    private LocalDateTime dateOrder = LocalDateTime.now();
    private int state;
    private TOccupationDTO tableOccupation;

    private List<DishOrderDetailsDTO> details;

    public DishOrderDTO(String orderID, BigDecimal totalPrice, LocalDateTime dateOrder, int state,
            TOccupationDTO tableOccupation, int status) {
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.dateOrder = dateOrder;
        this.state = state;
        this.tableOccupation = tableOccupation;
        setStatus(status);
    }

    @Override
    public DishOrder dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        TableOccupation table = null;
        if (tableOccupation != null) {
            table = new TableOccupation();
            table.setOccupationID(tableOccupation.getOccupationID() != null ? tableOccupation.getOccupationID() : null );
            table.setTableID(tableOccupation.getTableID());
            table.setStarttime(tableOccupation.getStarttime() != null ? tableOccupation.getStarttime() : null);
            table.setCustomerID(tableOccupation.getCustomerID() != null ? tableOccupation.getCustomerID() : null);            
        }
        DishOrder order = new DishOrder(totalPrice, dateOrder, state, table, getStatus());
        if (details != null && details.size() > 0) {
            // attachement des details de commande à la commande mère
            attachOrder(order, details);
            // calcul du prix total
            order.calculateTotalPrice();
        }
        return order;
    }

    // on boucle la liste des details commande dto en les convertissant en entity
    // on le rattache chacun a la commande mere
    // on attache la liste obtenu dans la commande mere
    private void attachOrder(DishOrder order, List<DishOrderDetailsDTO> details) {
        List<DishOrderDetails> list = details.stream()
                .map(row -> {
                    try {
                        DishOrderDetails detail = row.dtoToEntity();
                        detail.setOrder(order);
                        return detail;
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                })
                .toList();

        order.setDetails(list);
    }

    public void setDetails(List<DishOrderDetailsDTO> details) {
        this.details = details;
    }
}
