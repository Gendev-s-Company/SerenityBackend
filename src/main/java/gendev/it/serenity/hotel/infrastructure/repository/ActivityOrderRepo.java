package gendev.it.serenity.hotel.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.hotel.infrastructure.entity.Activity;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityOrder;

@Repository
public interface ActivityOrderRepo extends CommonRepository<ActivityOrder, String> {
    @Query("SELECT a FROM ActivityOrder a where a.activity.activityID = :activity and a.status=:status")
    List<ActivityOrder> findAllBActivity(String activity, int status);

    @Query("SELECT a FROM ActivityOrder a where a.activity.activityID = :activity and a.status=:status")
    Page<ActivityOrder> findAllBActivity(String activity, int status, Pageable page);

    @Query("SELECT a FROM ActivityOrder a where a.customer.customerID = :customer and a.status=:status")
    List<ActivityOrder> findAllByCustomer(String customer, int status);
    @Query("SELECT a FROM ActivityOrder a where a.customer.customerID = :customer and a.status=:status")
    Page<ActivityOrder> findAllByCustomer(String customer, int status, Pageable page);

    @Override
    @Query("SELECT a FROM ActivityOrder a WHERE a.status = :status AND a.activity.company.id = :company")
    List<ActivityOrder> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM ActivityOrder a WHERE a.status = :status AND a.activity.company.id = :company")
    Page<ActivityOrder> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
