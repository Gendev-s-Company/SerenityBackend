package gendev.it.serenity.facturation.application;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.utils.State;
import gendev.it.serenity.core.exception.BusinessException;
import gendev.it.serenity.customer.domain.dto.CustomerDTO;
import gendev.it.serenity.customer.infrastructure.entity.Customer;
import gendev.it.serenity.facturation.dto.BillingDTO;
import gendev.it.serenity.facturation.infrastructure.entity.Billing;
import gendev.it.serenity.facturation.infrastructure.repository.BillingRepo;
import gendev.it.serenity.pack.application.PackService;
import gendev.it.serenity.pack.dto.PackDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillingService extends CommonService<Billing, BillingDTO, String, BillingRepo> {

    private final PackService packService;
    private final PackBillingUseCase packBillingUseCase;

    public BillingService(BillingRepo jpa, PackService packService, PackBillingUseCase packBillingUseCase) {
        super(jpa);
        this.packService = packService;
        // TODO Auto-generated constructor stub
        this.packBillingUseCase = packBillingUseCase;
    }

    @Transactional
    public void updateState(String billID, Integer state) throws BusinessException {
        if (state == null)
            throw new BusinessException("veuillez indiquer le state", HttpStatus.BAD_REQUEST);
        if (state != 1 && state != 0)
            throw new BusinessException("La valeur de state doit etre 1 ou 0", HttpStatus.BAD_REQUEST);
        Billing invoice = findOneByIdAndStatus(billID, 0);
        invoice.setState(state);
        return;
    }

    // Récupérer les clients qui sont facturer
    public Page<CustomerDTO> findCustomersInvoice(String sort, String company, String field, int pageNumber,
            int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Customer> list = getJpa().findCustomerInvoiced(0, company, pageable);
        return list.map(m -> m.entityToDTO());
    }

    public Page<BillingDTO> findByCustomer(String sort, String customer, String field, int pageNumber,
            int pageSize) {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        Page<Billing> list = getJpa().findByCustomer(0, customer, pageable);
        return list.map(m -> {
            BillingDTO dto = m.entityToDTO();
            dto.setDurationsDetails(null);
            dto.setQuantityDetails(null);
            return dto;
        });
    }

    @Override
    public BillingDTO findById(String id, Integer status) throws Exception {
        // TODO Auto-generated method stub
        BillingDTO dto = super.findById(id, status);

        return dto;
    }

    private void isBillInsidePack(BillingDTO dto) throws Exception {
        List<PackDTO> packs = packService.findAllByCompany(dto.getCompanyID(), 0);
        PackDTO pack = packs.stream()
                .filter(p -> packBillingUseCase.matchesPack(dto, p))
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public BillingDTO updateBilling(BillingDTO toUpdate, String billID, boolean isDetail) throws BusinessException {
        Billing bill = this.findOneByIdAndStatus(billID, 0);
        bill.getDurationDetails().clear();
        bill.getQuantityDetails().clear();
        if (isDetail) {
            bill.setStatus(State.DELETED);
        } else {
            toUpdate.publicMapping(bill);
        }
        return bill.entityToDTO();
    }

    public Billing findCustomerInvoiceNotPaid(String customerid) {
        Billing invoice = getJpa().findTopBycustomerIDAndState(customerid, 0);// zay mbola tsy payé
        return invoice;
    }

}
