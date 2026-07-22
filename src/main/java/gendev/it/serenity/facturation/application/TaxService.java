package gendev.it.serenity.facturation.application;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.facturation.dto.TaxDTO;
import gendev.it.serenity.facturation.infrastructure.entity.Tax;
import gendev.it.serenity.facturation.infrastructure.repository.TaxRepo;

@Service
public class TaxService extends CommonService<Tax,TaxDTO, Integer,TaxRepo> {

    public TaxService(TaxRepo jpa) {
        super(jpa);
    }  
}
