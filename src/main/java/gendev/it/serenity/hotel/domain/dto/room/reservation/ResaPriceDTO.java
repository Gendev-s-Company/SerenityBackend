package gendev.it.serenity.hotel.domain.dto.room.reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResaPriceDTO {
    BigDecimal totalPrice;
    BigDecimal accompte;
    LocalDateTime deadline;
}
