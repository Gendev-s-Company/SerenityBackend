package gendev.it.serenity.restaurant.domain.dto.tables;

import java.time.LocalDateTime;

public interface TableDetailDayDispoDTO {
    public LocalDateTime getDay();

    public String getTableID();

    public String getName();

    public Integer getTable_state();

    public Integer getReservation_state();

    public LocalDateTime getActual_arrival();

    public LocalDateTime getActual_departure();

    public String getTable_name();
}
