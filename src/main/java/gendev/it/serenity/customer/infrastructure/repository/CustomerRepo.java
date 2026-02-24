package gendev.it.serenity.customer.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.customer.infrastructure.entity.Customer;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomType;

@Repository
public interface CustomerRepo extends CommonRepository<Customer, String> {
    @Override
    @Query("SELECT a FROM Customer a WHERE a.status = :status AND a.company.id = :company")
    List<Customer> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM Customer a WHERE a.status = :status AND a.company.id = :company")
    Page<Customer> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
