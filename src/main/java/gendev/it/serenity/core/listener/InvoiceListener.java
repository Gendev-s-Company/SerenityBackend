package gendev.it.serenity.core.listener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gendev.it.serenity.core.models.InvoiceModel;
import gendev.it.serenity.core.models.QInvoiceModel;
import gendev.it.serenity.facturation.application.BillingService;
import gendev.it.serenity.facturation.dto.BillingDDetailsDTO;
import gendev.it.serenity.facturation.dto.BillingDTO;
import gendev.it.serenity.facturation.dto.BillingQDetailsDTO;
import gendev.it.serenity.facturation.infrastructure.entity.Billing;
import gendev.it.serenity.facturation.infrastructure.entity.DurationBillingDetails;
import gendev.it.serenity.facturation.infrastructure.entity.QuantityBillingDetails;
import gendev.it.serenity.facturation.infrastructure.entity.Tax;
import gendev.it.serenity.facturation.infrastructure.repository.TaxRepo;

import lombok.RequiredArgsConstructor;

/**
 *  Class permettant de gérer les événements liés à la création d'une facture (Invoice) dans le système.
 *  Il écoute les événements de type InvoiceModel et déclenche le traitement approprié pour créer ou mettre à jour les factures dans le système de facturation.
 *  @author MyRanto Randria
 * InvoiceListener
 */
@Component
@RequiredArgsConstructor
public class InvoiceListener {
    private final BillingService billingService;
    private final TaxRepo taxRepo;

    @EventListener
    @Transactional
    public void onSaveInvoice(InvoiceModel invoiceModel) throws Exception {
        // Call the billing service to process the invoice
        // vérifier si le client a déjà une facture non payé
        List<QInvoiceModel> invoices = invoiceModel.getInvoices();
        String company = invoiceModel.getCompany();
        if (invoices == null || invoices.size() <= 0) {
            return;
        }
        Billing bill = billingService.findCustomerInvoiceNotPaid(invoices.get(0).getCustomerID());
        BillingDTO dto = new BillingDTO();
        Tax taxe = findLastTax(company);
        if (bill == null) {
            buildBilling(invoiceModel, company, dto, bill, taxe);
            return;
        }
        // traitement si il en possède déjà un
        BillingDTO billed = bill.entityToDTO();
        dto.setDurationsDetails(billed.getDurationsDetails());
        buildBillingDetails(invoiceModel, dto, bill);
System.out.println("updating");
        // Mbola mila tenenina hoe ilay duration tsy miova
        billingService.updateBilling(dto, bill.getBillID(), false);
    }


    private Tax findLastTax(String company) {
        return taxRepo.findLasTaxByCompany(company);
    }

    // pour la création de billing sur la quantity
    private void buildBilling(InvoiceModel invoiceModel, String company,
            BillingDTO dto, Billing bill, Tax taxe) throws Exception {
        List<QInvoiceModel> invoices = invoiceModel.getInvoices();

        dto.setCustomerID(invoices.get(0).getCustomerID());
        dto.setBillingDate(LocalDateTime.now());
        dto.setState(0);
        // traitement si le client ne possède pas de facture
        buildBillingDetails(invoiceModel, dto, bill);

        dto.setTaxe(taxe.getTaxRate());
        dto.setPackID(null);
        billingService.save(dto);
    }

    private void buildBillingDetails(InvoiceModel model, BillingDTO dto, Billing bill) {
        if (model.getdInvoices() != null) {
            List<BillingDDetailsDTO> duration = model.getdInvoices().stream()
                    .map(m -> new DurationBillingDetails(m.getServiceName(), m.getServiceCode(),
                            m.getTypeDuration(), m.getUnitPrice(), m.getStartTime(), m.getEndTime(), bill)
                            .entityToDTO())
                    .collect(Collectors.toList());

            dto.setDurationsDetails(duration);
        }
        if (model.getInvoices() != null) {
            List<BillingQDetailsDTO> quantity = model.getInvoices().stream()
                    .map(m -> new QuantityBillingDetails(m.getServiceName(), m.getServiceCode(), m.getQuantity(),
                            m.getUnitPrice(), bill).entityToDTO())
                    .collect(Collectors.toList());

            dto.setQuantityDetails(quantity);
        }
    }
}
