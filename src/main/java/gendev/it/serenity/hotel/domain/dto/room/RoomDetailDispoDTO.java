package gendev.it.serenity.hotel.domain.dto.room;

import java.time.LocalDateTime;


/**
 * 
 * DTO pour obtenir la disponibilité par jour
 */
public interface RoomDetailDispoDTO {
    public LocalDateTime getDay();
    public String getRoomID();
    public String getName();
    public Integer getRoom_state();
    public Integer getReservation_state();
    public LocalDateTime getActual_arrival();
    public LocalDateTime getActual_departure();
    public String getRoom_name();
}
