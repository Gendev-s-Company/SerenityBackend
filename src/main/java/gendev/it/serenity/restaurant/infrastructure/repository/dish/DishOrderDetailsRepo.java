package gendev.it.serenity.restaurant.infrastructure.repository.dish;

import java.util.List;

import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrderDetails;

@Repository
public interface DishOrderDetailsRepo extends CommonRepository<DishOrderDetails, String>{

    List<DishOrderDetails> findAllByOrderIDAndStatusOrderByDateOrderAsc(String orderID, int status);
}
