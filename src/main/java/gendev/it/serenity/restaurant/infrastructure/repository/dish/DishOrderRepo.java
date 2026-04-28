package gendev.it.serenity.restaurant.infrastructure.repository.dish;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrder;

@Repository
public interface DishOrderRepo extends CommonRepository<DishOrder, String>{
    @Query("SELECT a FROM DishOrder a where a.status = :status and a.tableOccupation.occupationID = :tableOccupation limit 1")
    Optional<DishOrder> findAllByTableOccupation(String tableOccupation, int status);

    @Query("SELECT a FROM DishOrder a where a.status = 0 and a.tableOccupation.tableID = :tableid and a.tableOccupation.state= :state and a.dateOrder between :start and :end limit 1")
    Optional<DishOrder> findOneByTableAndDate(String tableid, LocalDateTime start,
            LocalDateTime end, Integer state);

    @Override
    @Query("SELECT a FROM DishOrder a WHERE a.status = :status AND a.tableOccupation.table.tabletype.company.id = :company")
    List<DishOrder> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM DishOrder a WHERE a.status = :status AND a.tableOccupation.table.tabletype.company.id = :company")
    Page<DishOrder> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
