package gendev.it.serenity.core.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * InvoiceModel utiliser pour le billing d'un quantité de service ou produit
 */
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceModel {
    private String company;
    private List<QInvoiceModel> invoices;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<QInvoiceModel> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<QInvoiceModel> invoices) {
        this.invoices = invoices;
    }

}
