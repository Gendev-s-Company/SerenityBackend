package gendev.it.serenity.facturation.dto;

import java.math.BigDecimal;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.facturation.infrastructure.entity.Billing;
import gendev.it.serenity.facturation.infrastructure.entity.QuantityBillingDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillingQDetailsDTO extends DTO<QuantityBillingDetails> {
    private Integer id;
    private String serviceName;
    private String serviceCode;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String billID;

    @Override
    public QuantityBillingDetails dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        Billing bill = billID != null ? new Billing(billID) : null;
        return new QuantityBillingDetails(serviceName, serviceCode, quantity, unitPrice, bill);
    }

    public QuantityBillingDetails dtoToEntityAvoidException (){
        Billing bill = billID != null ? new Billing(billID) : null;
        return new QuantityBillingDetails(serviceName, serviceCode, quantity, unitPrice, bill);
    }

}
