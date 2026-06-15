package gendev.it.serenity.pack.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.pack.infrastructure.models.Pack;

public interface PackRepo extends CommonRepository<Pack, String> {
    @Override
    @Query("SELECT a FROM Pack a WHERE a.status = :status AND a.companyID = :company")
    List<Pack> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM Pack a WHERE a.status = :status AND a.companyID = :company")
    Page<Pack> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
