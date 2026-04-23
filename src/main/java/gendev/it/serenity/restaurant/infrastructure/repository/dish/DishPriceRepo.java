package gendev.it.serenity.restaurant.infrastructure.repository.dish;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishPrice;

@Repository
public interface DishPriceRepo extends CommonRepository<DishPrice, Integer> {
    @Query("SELECT a FROM DishPrice a where a.dishID = :dishID and a.status=:status")
    List<DishPrice> findAllByDish(String dishID, int status);

    @Query("SELECT a FROM DishPrice a where a.dishID = :dishID and a.status=:status")
    Page<DishPrice> findAllByDish(String dishID, int status, Pageable page);

    @Query("SELECT  a FROM DishPrice a where a.dishID = :dishID and a.status=:status and a.dateChanged is not null order by a.dateChanged desc limit 1")
    DishPrice findLastByStatusAndDateChangedDesc(String dishID, int status);
}
