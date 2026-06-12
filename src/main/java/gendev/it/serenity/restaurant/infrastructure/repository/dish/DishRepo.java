package gendev.it.serenity.restaurant.infrastructure.repository.dish;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.Dish;

@Repository
public interface DishRepo extends CommonRepository<Dish, String> {
    @Override
    @Query("SELECT a FROM Dish a WHERE a.status = :status AND a.type.company.id = :company")
    List<Dish> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM Dish a WHERE a.status = :status AND a.type.company.id = :company")
    Page<Dish> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);

    @Query("SELECT a FROM Dish a WHERE a.status = :status AND a.type.company.id = :company group by a.type.typeID")
    Page<Dish> findPaginateByStatusAndCompanyGroupByType(int status, String company, Pageable pageable);

    @Query("SELECT a FROM Dish a WHERE a.type.id = :type")
    Page<Dish> findPaginatedByType(@Param("type") String type, Pageable pageable);

    @Query("SELECT a FROM Dish a WHERE a.type.company.id = :company")
    Page<Dish> findPaginatedByCompany(@Param("company") String company, Pageable pageable);

}
