package gendev.it.serenity.restaurant.infrastructure.repository.tables;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.RestaurantTable;

@Repository
public interface TableRepo extends CommonRepository<RestaurantTable, String>{
    @Override
    @Query("SELECT a FROM RestaurantTable a WHERE a.status = :status AND a.tabletype.company.id = :company")
    List<RestaurantTable> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM RestaurantTable a WHERE a.status = :status AND a.tabletype.company.id = :company")
    Page<RestaurantTable> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
