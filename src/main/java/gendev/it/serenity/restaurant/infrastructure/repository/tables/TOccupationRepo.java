package gendev.it.serenity.restaurant.infrastructure.repository.tables;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableOccupation;

@Repository
public interface TOccupationRepo extends CommonRepository<TableOccupation, String>{

    @Override
    @Query("SELECT a FROM TableOccupation a WHERE a.status = :status AND a.table.tabletype.company.id = :company")
    List<TableOccupation> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM TableOccupation a WHERE a.status = :status AND a.table.tabletype.company.id = :company")
    Page<TableOccupation> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
