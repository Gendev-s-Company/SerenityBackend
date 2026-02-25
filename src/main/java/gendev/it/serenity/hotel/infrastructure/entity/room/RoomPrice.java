package gendev.it.serenity.hotel.infrastructure.entity.room;

import java.math.BigDecimal;
import java.time.LocalDate;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.hotel.domain.dto.room.RoomPriceDTO;
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
@Table(name = "roomprice")
public class RoomPrice extends BaseEntity<RoomPriceDTO>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priceID;
    @Column
    private String roomID;
    @Column(name = "nightprice")
    private BigDecimal nightPrice;
    @Column(name = "hourprice")
    private BigDecimal hourPrice;
    @Column
    private LocalDate datechanged;
    @Column(name = "accountrate")
    private BigDecimal accountRate;
    
    public RoomPrice(Integer priceID) {
        this.priceID = priceID;
    }
    public RoomPrice(Integer priceID, String roomID, BigDecimal nightPrice, BigDecimal hourPrice, LocalDate datechanged,
            BigDecimal accountRate, int status) {
        this.priceID = priceID;
        this.roomID = roomID;
        this.nightPrice = nightPrice;
        this.hourPrice = hourPrice;
        this.datechanged = datechanged;
        this.accountRate = accountRate;
        setStatus(status);
    }
    @Override
    public Integer getId() {
        // TODO Auto-generated method stub
        return priceID;
    }
    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
       RoomPriceDTO d = (RoomPriceDTO) dto;
       setAccountRate(d.getAccountRate());
       setDatechanged(d.getDatechanged());
       setHourPrice(d.getHourPrice());
       setNightPrice(d.getNightPrice());
    }
    @Override
    public RoomPriceDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new RoomPriceDTO(priceID, roomID, nightPrice, hourPrice, datechanged, accountRate, status);
    }
}
