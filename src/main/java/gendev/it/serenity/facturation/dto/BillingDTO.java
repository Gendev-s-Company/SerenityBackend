package gendev.it.serenity.facturation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.facturation.infrastructure.entity.Billing;
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
        Billing bill = new Billing(customerID, billingDate, taxe, packID);
        startMapping(bill);
        return bill;
    }
    public void publicMapping(Billing bill){
        startMapping(bill);
    }
    private void startMapping(Billing bill){
        if (getDurationsDetails()!=null) {
            getDurationsDetails().forEach(m -> bill.attachDurationDetail(m.dtoToEntityAvoidException()));
        }
        if (getQuantityDetails()!=null) {
            getQuantityDetails().forEach(m -> bill.attachQuantityDetail(m.dtoToEntityAvoidException()));
        }
    }
    

}
