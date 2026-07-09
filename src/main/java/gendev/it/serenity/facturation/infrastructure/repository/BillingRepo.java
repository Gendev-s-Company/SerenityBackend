package gendev.it.serenity.facturation.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.customer.infrastructure.entity.Customer;
import gendev.it.serenity.facturation.infrastructure.entity.Billing;

public interface BillingRepo extends CommonRepository<Billing, String>{
    @Override
    @Query("SELECT a FROM Billing a WHERE a.status = :status AND a.customer.company.companyID = :company")
    List<Billing> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM Billing a WHERE a.status = :status AND a.customer.company.companyID = :company")
    Page<Billing> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);

    @Query("SELECT distinct a.customer FROM Billing a WHERE a.status = :status AND a.customer.company.companyID = :company")
    Page<Customer> findCustomerInvoiced(int status, String company, Pageable pageable);

    @Query("SELECT a FROM Billing a WHERE a.status = :status AND a.customerID = :customerid order by a.billingDate desc")
    Page<Billing> findByCustomer(int status, String customerid, Pageable page);
}
