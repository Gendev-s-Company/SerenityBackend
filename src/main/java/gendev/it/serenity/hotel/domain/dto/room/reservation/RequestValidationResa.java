package gendev.it.serenity.hotel.domain.dto.room.reservation;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestValidationResa {
    String roomid;
    LocalDateTime start;
    LocalDateTime end;
}
