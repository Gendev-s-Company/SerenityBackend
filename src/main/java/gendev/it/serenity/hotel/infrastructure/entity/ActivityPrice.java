package gendev.it.serenity.hotel.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.hotel.domain.dto.ActivityPriceDTO;
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
@Table(name = "activityprice")
public class ActivityPrice extends BaseEntity<ActivityPriceDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priceID;
    @ManyToOne
    @JoinColumn(name = "activityID", nullable = false)
    private Activity activity;
    @Column
    private BigDecimal price;
    @Column(name = "hourprice")
    private Integer hourPrice;
    @Column(name = "datechanged")
    private LocalDate dateChanged;

    
    public ActivityPrice(Integer priceID, Activity activity, BigDecimal price, Integer hourPrice,
            LocalDate dateChanged, Integer status) {
        this.priceID = priceID;
        this.activity = activity;
        this.price = price;
        this.hourPrice = hourPrice;
        this.dateChanged = dateChanged;
        setStatus(status);
    }

    @Override
    public Integer getId() {
        // TODO Auto-generated method stub
        return priceID;
    }

    @Override
    public void updateFromDTO(DTO cdto) {
        // TODO Auto-generated method stub
        ActivityPriceDTO dto = (ActivityPriceDTO) cdto;
        setActivity(new Activity(dto.getActivity().getActivityID()));
        setDateChanged(dto.getDateChanged());
        setPrice(dto.getPrice());
        setHourPrice(dto.getHourPrice());
    }

    @Override
    public ActivityPriceDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new ActivityPriceDTO(priceID, activity.entityToDTO(), price, hourPrice, dateChanged, getStatus());
    }

}
