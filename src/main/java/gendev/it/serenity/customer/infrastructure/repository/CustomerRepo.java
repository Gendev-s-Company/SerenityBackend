package gendev.it.serenity.customer.infrastructure.repository;

import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.customer.infrastructure.entity.Customer;

@Repository
public interface CustomerRepo extends CommonRepository<Customer, String>{

}
