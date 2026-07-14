package gendev.it.serenity.core.listener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gendev.it.serenity.core.models.QInvoiceModel;
import gendev.it.serenity.facturation.application.BillingService;
import gendev.it.serenity.facturation.dto.BillingDTO;
import gendev.it.serenity.facturation.dto.BillingQDetailsDTO;
import gendev.it.serenity.facturation.infrastructure.entity.Billing;
import gendev.it.serenity.facturation.infrastructure.entity.QuantityBillingDetails;
import gendev.it.serenity.facturation.infrastructure.entity.Tax;
import gendev.it.serenity.facturation.infrastructure.repository.TaxRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InvoiceListener {
    private final BillingService billingService;
    private final TaxRepo taxRepo;

    @EventListener
    @Transactional
    public void onInvoiceCreated(List<QInvoiceModel> invoices, String company) throws Exception {
        // Call the billing service to process the invoice
        // vérifier si le client a déjà une facture non payé
        if (invoices == null || invoices.size() <= 0) {
            return;
        }
        Billing bill = billingService.findCustomerInvoiceNotPaid(invoices.get(0).getCustomerID());
        BillingDTO dto = new BillingDTO();
        Tax taxe = findLastTax(company);
        if (bill == null) {
            buildBilling(invoices, company, dto, bill, taxe);
            return;
        }
        // traitement si il en possède déjà un
        BillingDTO billed = bill.entityToDTO();
        dto.setDurationsDetails(billed.getDurationsDetails());
        List<BillingQDetailsDTO> quantity = invoices.stream()
                .map(m -> new QuantityBillingDetails(m.getServiceName(), m.getServiceCode(), m.getQuantity(),
                        m.getUnitPrice(), bill).entityToDTO())
                .collect(Collectors.toList());
        dto.setQuantityDetails(quantity);
        // Mbola mila tenenina hoe ilay duration tsy miova
        billingService.updateBilling(dto, bill.getBillID(), false);
    }

    @EventListener
    @Transactional
    public void onInvoiceUpdated(String invoiceId) throws Exception {
        // Call the billing service to process the invoice update
        BillingDTO dto = new BillingDTO();
        billingService.updateBilling(dto, invoiceId, true);
    }

    private Tax findLastTax(String company) {
        return taxRepo.findLasTaxByCompany(company);
    }

    // pour la création de billing sur la quantity
    private void buildBilling(List<QInvoiceModel> invoices, String company,
            BillingDTO dto, Billing bill, Tax taxe) {
        dto.setCustomerID(invoices.get(0).getCustomerID());
        dto.setBillingDate(LocalDateTime.now());
        dto.setState(0);
        // traitement si le client ne possède pas de facture
        List<BillingQDetailsDTO> quantity = invoices.stream()
                .map(m -> new QuantityBillingDetails(m.getServiceName(), m.getServiceCode(), m.getQuantity(),
                        m.getUnitPrice(), bill).entityToDTO())
                .collect(Collectors.toList());
        dto.setQuantityDetails(quantity);
        dto.setTaxe(taxe.getTaxRate());
        dto.setPackID(null);
    }
}
