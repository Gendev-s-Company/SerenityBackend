package gendev.it.serenity.restaurant.infrastructure.repository.tables;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableType;

@Repository
public interface TableTypeRepo extends CommonRepository<TableType, String> {
    @Override
    @Query("SELECT a FROM TableType a WHERE a.status = :status AND a.company.id = :company")
    List<TableType> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM TableType a WHERE a.status = :status AND a.company.id = :company")
    Page<TableType> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
