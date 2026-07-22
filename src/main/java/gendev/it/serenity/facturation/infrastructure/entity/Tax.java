package gendev.it.serenity.facturation.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.facturation.dto.TaxDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Tax extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taxID;
    @Column(name = "taxrate")
    private BigDecimal taxRate;
    @Column(name = "datetax")
    private LocalDate dateTax;
    @Column(name = "companyid")
    private String companyID;
    

    public Tax(Integer taxID, BigDecimal taxRate, LocalDate dateTax, String companyID,int status) {
        this.taxID = taxID;
        this.taxRate = taxRate;
        this.dateTax = dateTax;
        this.companyID = companyID;
        setStatus(status);
    }

    @Override
    public Object getId() {
        // TODO Auto-generated method stub
        return taxID;
    }

    @Override
    public void updateFromDTO(DTO tdto) {
        // TODO Auto-generated method stub
        TaxDTO dto = (TaxDTO) tdto;
        setTaxRate(dto.getTaxRate());
        setDateTax(dto.getDatetax());
        setCompanyID(dto.getCompanyID());
    }

    @Override
    public TaxDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new TaxDTO(taxID,taxRate,dateTax,companyID,status);
    }

}
