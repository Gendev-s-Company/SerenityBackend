package gendev.it.serenity.customer.application;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.customer.domain.dto.CustomerDTO;
import gendev.it.serenity.customer.infrastructure.entity.Customer;
import gendev.it.serenity.customer.infrastructure.repository.CustomerRepo;

@Service
public class CustomerService extends CommonService<Customer,CustomerDTO,String, CustomerRepo>{

    public CustomerService(CustomerRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

}
