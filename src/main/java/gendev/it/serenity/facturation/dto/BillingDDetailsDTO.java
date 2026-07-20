package gendev.it.serenity.facturation.dto;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.facturation.infrastructure.entity.Billing;
import gendev.it.serenity.facturation.infrastructure.entity.DurationBillingDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillingDDetailsDTO extends DTO<DurationBillingDetails> {
    private Integer id;
    private String serviceName;
    private String serviceCode;
    private String typeDuration;
    private BigDecimal unitPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String billID;

    @Override
    public DurationBillingDetails dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        Billing bill = billID != null ? new Billing(billID) : null;
        return new DurationBillingDetails(serviceName, serviceCode, typeDuration, unitPrice, startTime, endTime, bill);
    }

    public DurationBillingDetails dtoToEntityAvoidException() {
        Billing bill = billID != null ? new Billing(billID) : null;
        return new DurationBillingDetails(serviceName, serviceCode, typeDuration, unitPrice, startTime, endTime, bill);
    }

    public String getFormatDuration() { // heure ou jours
        if (startTime == null || endTime == null) {
            return "";
        }

        // Calcule la durée entre les deux dates
        Duration duration = Duration.between(startTime, endTime);

        return Math.abs(duration.toHours()) < 24 ? "h" : "d";

    }

}
