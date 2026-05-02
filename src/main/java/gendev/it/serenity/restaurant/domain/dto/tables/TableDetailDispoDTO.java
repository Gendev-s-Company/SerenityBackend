package gendev.it.serenity.restaurant.domain.dto.tables;

import java.time.LocalDateTime;

public interface TableDetailDispoDTO {
    public LocalDateTime getDay();
    public String getTableID();
    public String getTable_name();
    public Integer getTable_state();
    public Integer getReservation_state();
    public LocalDateTime getActual_arrival();
    public LocalDateTime getActual_departure();
    
}
