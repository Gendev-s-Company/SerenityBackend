package gendev.it.serenity.facturation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.facturation.infrastructure.entity.Tax;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaxDTO extends DTO<Tax>{
    private Integer taxID;
    private BigDecimal taxRate;
    private LocalDate datetax;
    private String companyID;

    
    public TaxDTO(Integer taxID) {
        this.taxID = taxID;
    }


    public TaxDTO(Integer taxID, BigDecimal taxRate, LocalDate datetax, String companyID,int status) {
        this.taxID = taxID;
        this.taxRate = taxRate;
        this.datetax = datetax;
        this.companyID = companyID;
        setStatus(status);
    }


    public void setTaxID(Integer taxID) {
        this.taxID = taxID;
    }


    public void setTaxRate(BigDecimal taxRate) throws Exception {
        if(taxRate == null && !isSkipValidation()){
            throw new Exception("Veuillez entrer un nombre valide!");
        }
        this.taxRate = taxRate;
    }


    public void setDatetax(LocalDate datetax) throws Exception  {
        if(datetax == null && !isSkipValidation()){
            throw new Exception("Veuillez entrer une date valide!");

        }
        this.datetax = datetax;
    }


    public void setCompanyID(String companyID) throws Exception {
        if (companyID == null || companyID.isBlank()){
            throw new Exception("Company est obligatoire");
        }
        this.companyID = companyID;
    }

    @Override
    public Tax dtoToEntity() throws Exception{
        return new Tax(taxID,taxRate,datetax,companyID,getStatus());
    }

    
}
