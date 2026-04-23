package gendev.it.serenity.restaurant.infrastructure.repository.dish;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishPhoto;

@Repository
public interface DishPhotoRepo extends CommonRepository<DishPhoto, Integer> {
    List<DishPhoto> findByDishIDAndStatus(String dishID, int status);

    Page<DishPhoto> findByDishIDAndStatus(String dishID, int status, Pageable page);

}
