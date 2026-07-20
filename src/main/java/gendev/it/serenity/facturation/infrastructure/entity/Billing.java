package gendev.it.serenity.facturation.infrastructure.entity;

import java.math.BigDecimal;
import java.time.Duration;
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
    @Column
    private String companyID;

    @Column
    private int state  = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerID", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packID", insertable = false, updatable = false)
    private Pack pack;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("quantityDetails")
    private List<QuantityBillingDetails> quantityDetails = new ArrayList<>();

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("durationDetails")
    private List<DurationBillingDetails> durationDetails = new ArrayList<>();

    public Billing(String customerID, LocalDateTime billingDate, BigDecimal taxe, String packID) {
        this.customerID = customerID;
        this.billingDate = billingDate;
        this.taxe = taxe;
        this.packID = packID;
    }

    public Billing(String customerID, LocalDateTime billingDate, BigDecimal taxe, String packID,
            List<QuantityBillingDetails> quantityDetails, List<DurationBillingDetails> durationDetails) {
        this.customerID = customerID;
        this.billingDate = billingDate;
        this.taxe = taxe;
        this.packID = packID;
        attach(quantityDetails, durationDetails);

        System.out.println(quantityDetails.size());
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
        return billID;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }

    @Override
    public BillingDTO entityToDTO() {
        // TODO Auto-generated method stub

        BillingDTO invoice = new BillingDTO(billID, customerID, billingDate, taxe, packID, this.state, convertToListEntity(),
                convertToListEntity(quantityDetails));
        calculateTotalInvoice(invoice);
        return invoice;
    }

    public List<BillingDDetailsDTO> convertToListEntity() {
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

    public List<BillingQDetailsDTO> convertToListEntity(List<QuantityBillingDetails> list) {
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
        quantitys.forEach(m -> attachQuantityDetail(m));
    }

    private void attachListDurationDetails(List<DurationBillingDetails> durations) {
        durations.forEach(m -> attachDurationDetail(m));
    }

    public void attachQuantityDetail(QuantityBillingDetails quantity) {
        quantity.setBill(this);
        this.quantityDetails.add(quantity);
    }

    public void attachDurationDetail(DurationBillingDetails duration) {
        duration.setBill(this);
        this.durationDetails.add(duration);
    }


    //calcul total price
    private BigDecimal calculateDurationInvoice(List<BillingDDetailsDTO> durationInvoices) {
        if (durationInvoices == null || durationInvoices.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return durationInvoices.stream()
                .map(item -> {
                    // Sécurité : si une donnée essentielle est manquante, la ligne coûte 0
                    if (item.getStartTime() == null || item.getEndTime() == null || item.getUnitPrice() == null) {
                        return BigDecimal.ZERO;
                    }

                    // 1. Calcul de la durée absolue entre start et end
                    Duration duration = Duration.between(item.getStartTime(), item.getEndTime());
                    long totalHours = Math.abs(duration.toHours());

                    BigDecimal quantity;

                    // 2. Application de la règle tarifaire (< 24h ou >= 24h)
                    if (totalHours < 24) {
                        //  l'heure
                        quantity = BigDecimal.valueOf(totalHours);
                    } else {
                        //  jour (24h = 1 jour)
                        long totalDays = duration.toDays(); // ou totalHours / 24
                        quantity = BigDecimal.valueOf(totalDays);
                    }

                    // 3. Calcul du sous-total pour cette ligne
                    return item.getUnitPrice().multiply(quantity);
                })
                // 4. Somme de tous les sous-totaux
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateQuantityInvoice(List<BillingQDetailsDTO> quantityInvoices) {
        return quantityInvoices.stream()
                .map(m -> m.getUnitPrice().multiply(BigDecimal.valueOf(m.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void calculateTotalInvoice(BillingDTO invoice) {
        BigDecimal totalDuration = calculateDurationInvoice(invoice.getDurationsDetails());
        BigDecimal totalQuantity = calculateQuantityInvoice(invoice.getQuantityDetails());
        BigDecimal totalHT = totalDuration.add(totalQuantity);
        BigDecimal totalTTC = totalHT.add(totalHT.multiply(invoice.getTaxe().divide(BigDecimal.valueOf(100))));
        invoice.setTotalHT(totalHT);
        invoice.setTotalTTC(totalTTC);
    }
}
