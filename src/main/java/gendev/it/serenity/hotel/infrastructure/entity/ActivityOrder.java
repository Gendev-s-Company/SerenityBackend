package gendev.it.serenity.hotel.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.customer.domain.dto.CustomerDTO;
import gendev.it.serenity.customer.infrastructure.entity.Customer;
import gendev.it.serenity.hotel.domain.dto.ActivityDTO;
import gendev.it.serenity.hotel.domain.dto.ActivityOrderDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "activityorder")
public class ActivityOrder extends BaseEntity<ActivityOrderDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acorderID")
    private String acOrderID;
    @ManyToOne
    @JoinColumn(name = "activityID", nullable = false)
    private Activity activity;
    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    private Customer customer;
    @Column
    private BigDecimal price;
    @Column
    private Integer duration;

    @Column(name = "dateorder")
    private LocalDateTime dateOrder;

    public ActivityOrder(String acOrderID, Activity activity, Customer user, BigDecimal price, Integer duration,
            LocalDateTime dateOrder, int status) {
        this.acOrderID = acOrderID;
        this.activity = activity;
        this.customer = user;
        this.price = price;
        this.duration = duration;
        this.dateOrder = dateOrder;
        setStatus(status);
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return acOrderID;
    }

    @Override
    public void updateFromDTO(DTO cdto) {
        // TODO Auto-generated method stub
        ActivityOrderDTO dto = (ActivityOrderDTO) cdto;
        setActivity(new Activity(dto.getActivity().getActivityID()));
        setCustomer(new Customer(dto.getCustomer().getCustomerID()));
        setPrice(dto.getPrice());
        setDuration(dto.getDuration());
        setDateOrder(dto.getDateOrder());
    }

    @Override
    public ActivityOrderDTO entityToDTO() {
        ActivityDTO act = null;
        CustomerDTO u = null;
        if (activity != null) {
            act = activity.entityToDTO();
        }
        if (customer != null) {
            u = customer.entityToDTO();
        }
        return new ActivityOrderDTO(acOrderID, act, u, price, duration, dateOrder, status);
    }
}
