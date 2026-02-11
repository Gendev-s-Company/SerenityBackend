package gendev.it.serenity.hotel.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.hotel.infrastructure.entity.Activity;

@Repository
public interface ActivityRepo extends CommonRepository<Activity, String>{
    @Query("SELECT a FROM Activity a WHERE a.status = :status AND a.company.id = :company")
    List<Activity> findAllByStatusAndCompany(int status, String company);

    @Query("SELECT a FROM Activity a WHERE a.status = :status AND a.company.id = :company")
    Page<Activity> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
