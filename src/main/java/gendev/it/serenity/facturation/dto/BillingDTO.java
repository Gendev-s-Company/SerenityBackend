package gendev.it.serenity.facturation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.facturation.infrastructure.entity.Billing;
import gendev.it.serenity.facturation.infrastructure.entity.DurationBillingDetails;
import gendev.it.serenity.facturation.infrastructure.entity.QuantityBillingDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillingDTO extends DTO<Billing> {
    private String billID;
    private String customerID;
    private LocalDateTime billingDate;
    private BigDecimal taxe;
    private String packID;
    private List<BillingDDetailsDTO> durationsDetails;
    private List<BillingQDetailsDTO> quantityDetails;

    @Override
    public Billing dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new Billing(customerID, billingDate, taxe, packID, convertToListEntity(quantityDetails), convertToListEntity());
    }

    public List<DurationBillingDetails> convertToListEntity() {
        if (durationsDetails == null) {
            return null;
        }
        return durationsDetails.stream()
                .map(m -> {
                    try {
                        return m.dtoToEntity();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public List<QuantityBillingDetails> convertToListEntity(List<BillingQDetailsDTO> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(m -> {
                    try {
                        return m.dtoToEntity();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

}
