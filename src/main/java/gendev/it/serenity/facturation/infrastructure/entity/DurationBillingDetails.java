package gendev.it.serenity.facturation.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.facturation.dto.BillingDDetailsDTO;
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
@Entity(name = "durationbillingdetails")
public class DurationBillingDetails extends BaseEntity<BillingDDetailsDTO>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "servicename")
    private String serviceName;
    @Column(name = "servicecode")
    private String serviceCode;
    @Column(name = "typeduration")
    private String typeDuration;
    @Column(name = "unitprice")
    private BigDecimal unitPrice;

    @Column(name = "starttime")
    private LocalDateTime startTime;
    @Column(name = "endtime")
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billID")
    @JsonManagedReference
    private Billing bill;

    
    public DurationBillingDetails(String serviceName, String serviceCode, String typeDuration, BigDecimal unitPrice,
            LocalDateTime startTime, LocalDateTime endTime, Billing bill) {
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
        this.typeDuration = typeDuration;
        this.unitPrice = unitPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bill = bill;
    }

    public DurationBillingDetails(Integer id, String serviceName, String serviceCode, String typeDuration,
            BigDecimal unitPrice, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
        this.typeDuration = typeDuration;
        this.unitPrice = unitPrice;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }

    @Override
    public BillingDDetailsDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new BillingDDetailsDTO(id, serviceName, serviceCode, typeDuration, unitPrice, startTime, endTime, null);
    }

}
