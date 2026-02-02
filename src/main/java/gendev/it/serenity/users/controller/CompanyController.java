package gendev.it.serenity.users.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.users.application.CompanyService;
import gendev.it.serenity.users.domain.dto.CompanyDTO;

@RestController
@RequestMapping("api/company")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class CompanyController extends CommonController<CompanyDTO,CompanyService> {

    public CompanyController(CompanyService service) {
        super(service);
    }
}
