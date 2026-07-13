package gendev.it.serenity.core.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gendev.it.serenity.core.models.InvoiceModel;
import gendev.it.serenity.facturation.application.BillingService;
import gendev.it.serenity.facturation.dto.BillingDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InvoiceListener {
    private final BillingService billingService;

    @EventListener
    @Transactional
    public void onInvoiceCreated(InvoiceModel invoice) throws Exception {
        // Call the billing service to process the invoice
        BillingDTO dto = new BillingDTO();
        billingService.save(dto);
    }

    @EventListener
    @Transactional
    public void onInvoiceUpdated(String invoiceId) throws Exception {
        // Call the billing service to process the invoice update
        BillingDTO dto = new BillingDTO();
        billingService.updateBilling(dto, invoiceId, true);
    }
}
