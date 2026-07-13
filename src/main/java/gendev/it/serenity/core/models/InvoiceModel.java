package gendev.it.serenity.core.models;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


// mila manamboatra class invoice model pour qunatity et duration
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceModel {
    private String serviceName;
    private String serviceCode;
    private Integer quantity;
    private BigDecimal unitPrice;
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getServiceCode() {
        return serviceCode;
    }
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
}
