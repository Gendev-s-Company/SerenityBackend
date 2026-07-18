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
    private String customerID;
    private String company;
    private List<QInvoiceModel> invoices;
    private List<DInvoiceModel> dInvoices;//duration invoices

    
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

    public List<DInvoiceModel> getdInvoices() {
        return dInvoices;
    }

    public void setdInvoices(List<DInvoiceModel> dInvoices) {
        this.dInvoices = dInvoices;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

}
