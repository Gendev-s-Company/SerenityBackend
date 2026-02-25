package gendev.it.serenity.hotel.domain.dto.room;

import java.math.BigDecimal;
import java.time.LocalDate;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomPriceDTO extends DTO<RoomPrice> {
    private Integer priceID;
    private String roomID;
    private BigDecimal nightPrice;
    private BigDecimal hourPrice;
    private LocalDate datechanged = LocalDate.now();
    private BigDecimal accountRate;

    public RoomPriceDTO(Integer priceID) {
        this.priceID = priceID;
    }
    public RoomPriceDTO(Integer priceID, String roomID, BigDecimal nightPrice, BigDecimal hourPrice,
            LocalDate datechanged, BigDecimal accountRate, int status) {
        this.priceID = priceID;
        this.roomID = roomID;
        this.nightPrice = nightPrice;
        this.hourPrice = hourPrice;
        this.datechanged = datechanged;
        this.accountRate = accountRate;
        setStatus(status);
    }
    public void setPriceID(Integer priceID) {
        this.priceID = priceID;
    }
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
    public void setNightPrice(BigDecimal nightPrice) throws Exception {
        if (nightPrice == null && !isSkipValidation()) {
            throw new Exception("Veuillez définir le prix par nuité");
        }
        this.nightPrice = nightPrice;
    }
    public void setHourPrice(BigDecimal hourPrice) throws Exception {
        if (hourPrice == null && !isSkipValidation()) {
            throw new Exception("Veuillez définir le taux horaire");
        }
        this.hourPrice = hourPrice;
    }
    public void setDatechanged(LocalDate datechanged) {
        this.datechanged = datechanged;
    }
    public void setAccountRate(BigDecimal accountRate) {
        this.accountRate = accountRate;
    }
    @Override
    public RoomPrice dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new RoomPrice(priceID, roomID, nightPrice, hourPrice, datechanged, accountRate, getStatus());
    }
       
}
