package gendev.it.serenity.restaurant.infrastructure.entity.dish;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.common.utils.State;
import gendev.it.serenity.restaurant.domain.dto.dish.DishOrderDTO;
import gendev.it.serenity.restaurant.domain.dto.tables.TOccupationDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableOccupation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "dishorder")
public class DishOrder extends BaseEntity<DishOrderDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderID;
    @Column(name = "totalprice")
    private BigDecimal totalPrice;
    @Column(name = "dateorder")
    private LocalDateTime dateOrder = LocalDateTime.now();
    @Column
    private int state;

    @ManyToOne
    @JoinColumn(name = "tableoccupationid", nullable = false)
    private TableOccupation tableOccupation;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishOrderDetails> details = new ArrayList<>();

    public DishOrder(String orderID) {
        this.orderID = orderID;
    }

    public DishOrder(BigDecimal totalPrice, LocalDateTime dateOrder, int state,
            TableOccupation tableOccupation, int status) {
        this.totalPrice = totalPrice;
        this.dateOrder = dateOrder;
        this.state = state;
        this.tableOccupation = tableOccupation;
        setStatus(status);
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return orderID;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }

    @Override
    public DishOrderDTO entityToDTO() {
        // TODO Auto-generated method stub
        TOccupationDTO dto = null;
        if (tableOccupation != null) {
            dto = tableOccupation.entityToDTO();
        }
        return new DishOrderDTO(orderID, totalPrice, dateOrder, state, dto, getStatus());
    }

    // return la liste de detail order
    public DishOrderDTO oneEntityToDTO() {
        // TODO Auto-generated method stub
        DishOrderDTO dto = entityToDTO();
        DishOrderDetails det = new DishOrderDetails();
        dto.setDetails(det.convertListToDTO(details));
        return dto;
    }

    public void addDetail(DishOrderDetails detail) {
        detail.setOrder(this);
        this.details.add(detail);
    }

    // calcul du prix total d'une commande
    public void calculateTotalPrice() {
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < details.size(); i++) {
            DishOrderDetails one = details.get(i);
            if (one.getStatus() == State.DELETED)
                continue;
            BigDecimal value = one.getUnitPrice().multiply(new BigDecimal(one.getQuantity()));
            total = total.add(value);
        }
        setTotalPrice(total);
    }

}
