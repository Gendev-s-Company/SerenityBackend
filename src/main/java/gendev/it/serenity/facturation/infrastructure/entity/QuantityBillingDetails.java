package gendev.it.serenity.facturation.infrastructure.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.facturation.dto.BillingQDetailsDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "quantitybillingdetails")
public class QuantityBillingDetails extends BaseEntity<BillingQDetailsDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "servicename")
    private String serviceName;
    @Column(name = "servicecode")
    private String serviceCode;
    @Column
    private Integer quantity;
    @Column(name = "unitprice")
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billID")
    @JsonManagedReference
    private Billing bill;

    

    public QuantityBillingDetails(String serviceName, String serviceCode, Integer quantity, BigDecimal unitPrice,
            Billing bill) {
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.bill = bill;
    }

    public QuantityBillingDetails(Integer id, String serviceName, String serviceCode, Integer quantity,
            BigDecimal unitPrice) {
        this.id = id;
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }
    

    @Override
    public BillingQDetailsDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new BillingQDetailsDTO(id, serviceName, serviceCode, quantity, unitPrice, null);
    }
}
