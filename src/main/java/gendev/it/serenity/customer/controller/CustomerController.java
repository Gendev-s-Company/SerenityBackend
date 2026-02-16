package gendev.it.serenity.customer.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.customer.application.CustomerService;
import gendev.it.serenity.customer.domain.dto.CustomerDTO;

@RestController
@RequestMapping("customer")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class CustomerController extends CommonController<CustomerDTO,CustomerService> {

    public CustomerController(CustomerService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }

}
