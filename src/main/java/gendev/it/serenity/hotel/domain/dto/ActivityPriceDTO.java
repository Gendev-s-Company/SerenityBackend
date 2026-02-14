package gendev.it.serenity.hotel.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.hotel.infrastructure.entity.Activity;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ActivityPriceDTO extends DTO<ActivityPrice> {
    private Integer priceID;
    private ActivityDTO activity;
    private BigDecimal price;
    private Integer hourPrice;
    private LocalDate dateChanged;

    public ActivityPriceDTO(Integer priceID, ActivityDTO activity, BigDecimal price, Integer hourPrice,
            LocalDate dateChanged, Integer status) {
        this.priceID = priceID;
        this.activity = activity;
        this.price = price;
        this.hourPrice = hourPrice;
        this.dateChanged = dateChanged;
        setStatus(status);
    }

    @Override
    public ActivityPrice dtoToEntity() throws Exception {
        if (dateChanged == null) {
            dateChanged = LocalDate.now();
        }
         Activity p = null;
        if (activity != null) {
            // p = activity.dtoToEntity();   
            p = new Activity(activity.getActivityID());
        }
        // TODO Auto-generated method stub
        return new ActivityPrice(priceID, p, price, hourPrice, dateChanged, getStatus());
    }

    public void setPriceID(Integer priceID) {
        this.priceID = priceID;
    }

    public void setActivity(ActivityDTO activity) throws Exception {
        if (activity == null && !isSkipValidation()) {
            throw new Exception("Veuillez remplir le champ activité");
        }
        activity.setSkipValidation(true);
        this.activity = activity;
    }

    public void setPrice(BigDecimal price) throws Exception {
        if (price == null && !isSkipValidation()) {
            throw new Exception("Veuillez remplir le champ prix");
        }
        this.price = price;
    }

    public void setHourPrice(Integer hourPrice) throws Exception {
        if (hourPrice == null  && !isSkipValidation()) {
            throw new Exception("Veuillez remplir le champ prix par heure");
        }
        this.hourPrice = hourPrice;
    }

    public void setDateChanged(LocalDate dateChanged) {
        if (dateChanged == null) {
            this.dateChanged = LocalDate.now();
        }
        this.dateChanged = dateChanged;
    }

}
