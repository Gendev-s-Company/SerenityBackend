package gendev.it.serenity.restaurant.infrastructure.repository.dish;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishType;

@Repository
public interface DishTypeRepo extends CommonRepository<DishType, String> {
    @Override
    @Query("SELECT a FROM DishType a WHERE a.status = :status AND a.company.id = :company")
    List<DishType> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM DishType a WHERE a.status = :status AND a.company.id = :company")
    Page<DishType> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
