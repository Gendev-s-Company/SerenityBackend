package gendev.it.serenity.facturation.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.facturation.infrastructure.entity.Tax;

public interface TaxRepo  extends CommonRepository<Tax, Integer> {
    @Override
    @Query("SELECT a FROM Tax a WHERE a.status = :status AND a.companyID = :company")
    List<Tax> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM Tax a WHERE a.status = :status AND a.companyID = :company")
    Page<Tax> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
