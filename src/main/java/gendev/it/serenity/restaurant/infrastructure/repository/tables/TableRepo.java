package gendev.it.serenity.restaurant.infrastructure.repository.tables;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.domain.dto.tables.TableDetailDispoDTO;
import gendev.it.serenity.restaurant.domain.dto.tables.TableDsiponibilityDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.RestaurantTable;

@Repository
public interface TableRepo extends CommonRepository<RestaurantTable, String>{
    @Override
    @Query("SELECT a FROM RestaurantTable a WHERE a.status = :status AND a.tabletype.company.id = :company")
    List<RestaurantTable> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM RestaurantTable a WHERE a.status = :status AND a.tabletype.company.id = :company")
    Page<RestaurantTable> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
    @Query(value = "SELECT * FROM get_table_disponibility(:start, :end, CAST(:state AS int[])) d where exists (select roomid from v_room where companyID=:company and v_room.status=:status and v_room.roomID = d.roomID) and d.reservation_state in :state order by roomid asc", nativeQuery = true)
    List<TableDsiponibilityDTO> findDisponibility(Integer[] state, LocalDateTime start, LocalDateTime end, int status, String company);

    @Query(value = "SELECT * FROM get_table_calendar_with_hours(:start, :end, CAST(:state AS int[])) d where exists (select roomid from v_room where companyID=:company and v_room.status=:status and v_room.roomID = d.roomID) and d.reservation_state in :state order by roomid,day asc", nativeQuery = true)
    List<TableDetailDispoDTO> findDetailDisponibility(Integer[] state, LocalDateTime start, LocalDateTime end, int status, String company);
}
