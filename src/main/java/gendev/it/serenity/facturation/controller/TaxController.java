package gendev.it.serenity.facturation.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.facturation.application.TaxService;
import gendev.it.serenity.facturation.dto.TaxDTO;


@RestController
@RequestMapping("tax")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class TaxController extends CommonController<TaxDTO,TaxService> {

    public TaxController(TaxService service) {
        super(service);
    }
    
}
