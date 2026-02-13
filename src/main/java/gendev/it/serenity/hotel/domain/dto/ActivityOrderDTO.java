package gendev.it.serenity.hotel.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.customer.domain.dto.CustomerDTO;
import gendev.it.serenity.customer.infrastructure.entity.Customer;
import gendev.it.serenity.hotel.infrastructure.entity.Activity;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ActivityOrderDTO extends DTO<ActivityOrder> {
    private String acOrderID;
    private ActivityDTO activity;
    private CustomerDTO customer;
    private BigDecimal price;
    private Integer duration = 1;
    private LocalDate dateOrder = LocalDate.now();
    
    public ActivityOrderDTO(String acOrderID, ActivityDTO activity, CustomerDTO user, BigDecimal price,
            Integer duration, LocalDate dateOrder, int status) {
        this.acOrderID = acOrderID;
        this.activity = activity;
        this.customer = user;
        this.price = price;
        this.duration = duration;
        this.dateOrder = dateOrder;
        setStatus(status);
    }
    

    @Override
    public ActivityOrder dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        Activity act = null;
        Customer u = null;
        if (activity != null) {
            act = activity.dtoToEntity();
        }
        if (customer != null) {
            u = customer.dtoToEntity();
        }
        if (dateOrder == null) {
            dateOrder = LocalDate.now();
        }
        return new ActivityOrder(acOrderID, act, u, price, duration, dateOrder, getStatus());
    }


    public void setAcOrderID(String acOrderID) {
        this.acOrderID = acOrderID;
    }


    public void setActivity(ActivityDTO activity) throws Exception {
        if (activity == null && !isSkipValidation()) {
            throw new Exception("Veuillez choisir une activitée");
        }
        activity.setSkipValidation(true);
        this.activity = activity;
    }


    public void setCustomer(CustomerDTO customer) throws Exception {
         if (customer == null && !isSkipValidation()) {
            throw new Exception("Veuillez mettre un client");
        }
        customer.setSkipValidation(true);
        this.customer = customer;
    }


    public void setPrice(BigDecimal price) throws Exception {
         if (price == null && !isSkipValidation()) {
            throw new Exception("Veuillez mettre un prix");
        }
        this.price = price;
    }


    public void setDuration(Integer duration) {
        if (duration == null) {
            duration = 1;
        }
        this.duration = duration;
    }


    public void setDateOrder(LocalDate dateOrder) {
        if (dateOrder==null) {
            dateOrder = LocalDate.now();
        }
        this.dateOrder = dateOrder;
    }
    
}
