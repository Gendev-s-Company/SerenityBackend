package gendev.it.serenity.core.listener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gendev.it.serenity.core.models.DInvoiceModel;
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
 * Class permettant de gérer les événements liés à la création d'une facture
 * (Invoice) dans le système.
 * Il écoute les événements de type InvoiceModel et déclenche le traitement
 * approprié pour créer ou mettre à jour les factures dans le système de
 * facturation.
 * 
 * @author MyRanto Randria
 *         InvoiceListener
 */
@Component
@RequiredArgsConstructor
public class InvoiceListener {
    private final BillingService billingService;
    private final TaxRepo taxRepo;

    @EventListener
    @Transactional
    public void onSaveInvoice(InvoiceModel invoiceModel) throws Exception {
        // vérifier si le client a déjà une facture non payé
        List<QInvoiceModel> invoices = invoiceModel.getInvoices();
        List<DInvoiceModel> dInvoices = invoiceModel.getdInvoices();
        String company = invoiceModel.getCompany();
        if ((invoices == null || invoices.size() <= 0) && (dInvoices == null || dInvoices.size() <= 0)) {
            return;
        }
        Billing bill = billingService.findCustomerInvoiceNotPaid(invoiceModel.getCustomerID());
        BillingDTO dto = new BillingDTO();
        Tax taxe = findLastTax(company);
        if (bill == null) {
            buildBilling(invoiceModel, company, dto, bill, taxe);
            return;
        }
        buildBillingDetails(invoiceModel, dto, bill);
        // Mbola mila tenenina hoe ilay duration tsy miova
        billingService.updateBilling(dto, bill.getBillID(), false);
    }

    private Tax findLastTax(String company) {
        return taxRepo.findLasTaxByCompany(company);
    }

    // pour la création de billing sur la quantity
    private void buildBilling(InvoiceModel invoiceModel, String company,
            BillingDTO dto, Billing bill, Tax taxe) throws Exception {

        dto.setCustomerID(invoiceModel.getCustomerID());
        dto.setBillingDate(LocalDateTime.now());
        dto.setCompanyID(company);
        dto.setState(0);
        // traitement si le client ne possède pas de facture
        buildBillingDetails(invoiceModel, dto, bill);

        dto.setTaxe(taxe.getTaxRate());
        dto.setPackID(null);
        billingService.save(dto);
    }

    private void buildBillingDetails(InvoiceModel model, BillingDTO dto, Billing bill) throws Exception {
        List<BillingDDetailsDTO> billsDurations = bill != null ? bill.convertToListEntity() : new ArrayList<>();
        List<BillingQDetailsDTO> billsQuantity = bill != null ? bill.convertToListEntity(bill.getQuantityDetails())
                : new ArrayList<>();
        if (model.getdInvoices() != null) {
            List<BillingDDetailsDTO> duration = model.getdInvoices().stream()
                    .map(m -> new DurationBillingDetails(m.getServiceName(), m.getServiceCode(),
                            m.getTypeDuration(), m.getUnitPrice(), m.getStartTime(), m.getEndTime(), bill)
                            .entityToDTO())
                    .collect(Collectors.toList());

            validateDurationDetails(duration, billsDurations);

        }
        if (model.getInvoices() != null) {
            List<BillingQDetailsDTO> quantity = model.getInvoices().stream()
                    .map(m -> new QuantityBillingDetails(m.getServiceName(), m.getServiceCode(), m.getQuantity(),
                            m.getUnitPrice(), bill).entityToDTO())
                    .collect(Collectors.toList());

            validateQuantityDetails(quantity, billsQuantity);
        }
        dto.setDurationsDetails(billsDurations);
        dto.setQuantityDetails(billsQuantity);
    }

    private void validateDurationDetails(List<BillingDDetailsDTO> input, List<BillingDDetailsDTO> alreadySaved)
            throws Exception {
        Set<String> savedCodes = alreadySaved.stream()
                .map(BillingDDetailsDTO::getServiceCode)
                .collect(Collectors.toSet());

        input.forEach(invoice -> {
            if (!savedCodes.contains(invoice.getServiceCode())) {
                // La facture n'existe pas encore, on l'ajoute
                alreadySaved.add(invoice);
            }
        });
    }

    private void validateQuantityDetails(List<BillingQDetailsDTO> input, List<BillingQDetailsDTO> alreadySaved)
            throws Exception {
        Set<String> savedCodes = alreadySaved.stream()
                .map(BillingQDetailsDTO::getServiceCode)
                .collect(Collectors.toSet());

        input.forEach(invoice -> {
            if (!savedCodes.contains(invoice.getServiceCode())) {
                // La facture n'existe pas encore, on l'ajoute
                alreadySaved.add(invoice);
            } else {
                // La facture existe déjà, on met à jour les détails
                BillingQDetailsDTO existingInvoice;
                try {
                    existingInvoice = alreadySaved.stream()
                            .filter(b -> b.getServiceCode().equals(invoice.getServiceCode()))
                            .findFirst()
                            .orElseThrow(() -> new Exception(
                                    "Erreur lors de la mise à jour de la facture : facture introuvable"));
                    existingInvoice.setQuantity(invoice.getQuantity() + existingInvoice.getQuantity());
                    existingInvoice.setUnitPrice(invoice.getUnitPrice());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
    }

}
