package gendev.it.serenity.facturation.application;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.utils.State;
import gendev.it.serenity.core.exception.BusinessException;
import gendev.it.serenity.facturation.dto.BillingDTO;
import gendev.it.serenity.facturation.infrastructure.entity.Billing;
import gendev.it.serenity.facturation.infrastructure.repository.BillingRepo;
import org.springframework.transaction.annotation.Transactional;

public class BillingService extends CommonService<Billing, BillingDTO, String, BillingRepo> {

    public BillingService(BillingRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

    @Transactional
    public BillingDTO updateBilling(BillingDTO toUpdate, String billID, boolean isDetail) throws BusinessException{
        Billing bill = this.findOneByIdAndStatus(billID, 0);
        bill.getDurationDetails().clear();
        bill.getQuantityDetails().clear();
        if (isDetail) {
            bill.setStatus(State.DELETED);
        }else{
            bill.attach(toUpdate.convertToListEntity(toUpdate.getQuantityDetails()),toUpdate.convertToListEntity());
        }
        return bill.entityToDTO();
    }
    
}
