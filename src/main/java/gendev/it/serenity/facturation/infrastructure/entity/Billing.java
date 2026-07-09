package gendev.it.serenity.facturation.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonBackReference;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.customer.infrastructure.entity.Customer;
import gendev.it.serenity.facturation.dto.BillingDDetailsDTO;
import gendev.it.serenity.facturation.dto.BillingDTO;
import gendev.it.serenity.facturation.dto.BillingQDetailsDTO;
import gendev.it.serenity.pack.infrastructure.models.Pack;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Billing extends BaseEntity<BillingDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String billID;

    @Column
    private String customerID;
    @Column(name = "billingdate")
    private LocalDateTime billingDate;
    @Column
    private BigDecimal taxe;
    @Column
    private String packID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerID", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packID", insertable = false, updatable = false)
    private Pack pack;

    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("activityPack")
    private List<QuantityBillingDetails> quantityDetails = new ArrayList<>();

    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("activityPack")
    private List<DurationBillingDetails> durationDetails = new ArrayList<>();

    public Billing(String customerID, LocalDateTime billingDate, BigDecimal taxe, String packID,
            List<QuantityBillingDetails> quantityDetails, List<DurationBillingDetails> durationDetails) {
        this.customerID = customerID;
        this.billingDate = billingDate;
        this.taxe = taxe;
        this.packID = packID;
        attach(quantityDetails, durationDetails);
    }

    public Billing(String billID) {
        this.billID = billID;
    }

    public Billing(String billID, String customerID, LocalDateTime billingDate, Customer customer, Pack pack,
            List<QuantityBillingDetails> quantityDetails, List<DurationBillingDetails> durationDetails) {
        this.billID = billID;
        this.customerID = customerID;
        this.billingDate = billingDate;
        this.customer = customer;
        this.pack = pack;
        this.quantityDetails = quantityDetails;
        this.durationDetails = durationDetails;
    }

    @Override
    public Object getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }

    @Override
    public BillingDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new BillingDTO(billID, customerID, billingDate, taxe, packID, convertToListEntity(),
                convertToListEntity(quantityDetails));
    }

    private List<BillingDDetailsDTO> convertToListEntity() {
        if (durationDetails == null) {
            return null;
        }
        return durationDetails.stream()
                .map(m -> {
                    try {
                        return m.entityToDTO();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    private List<BillingQDetailsDTO> convertToListEntity(List<QuantityBillingDetails> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(m -> {
                    try {
                        return m.entityToDTO();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public void attach(List<QuantityBillingDetails> quantitys, List<DurationBillingDetails> durations) {
        attachListDurationDetails(durationDetails);
        attachListQuantityDetail(quantityDetails);
    }

    private void attachListQuantityDetail(List<QuantityBillingDetails> quantitys) {
        quantitys.forEach(m -> m.setBill(this));
        this.quantityDetails = quantitys;
    }

    private void attachListDurationDetails(List<DurationBillingDetails> durations) {
        durations.forEach(m -> m.setBill(this));
        this.durationDetails = durations;
    }

    public void attachQuantityDetail(QuantityBillingDetails quantity) {
        quantity.setBill(this);
        this.quantityDetails.add(quantity);
    }

    public void attachDurationDetail(DurationBillingDetails duration) {
        duration.setBill(this);
        this.durationDetails.add(duration);
    }
}
