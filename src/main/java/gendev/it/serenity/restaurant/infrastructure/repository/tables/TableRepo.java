package gendev.it.serenity.restaurant.infrastructure.repository.tables;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.domain.dto.tables.TableDetailDayDispoDTO;
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
    @Query(value = "SELECT * FROM get_table_disponibility(:start, :end, CAST(:state AS int[])) d where exists (select tableid from v_table where companyID=:company and v_table.status=:status and v_table.tableID = d.tableID) and d.reservation_state in :state order by tableid asc", nativeQuery = true)
    List<TableDsiponibilityDTO> findDisponibility(Integer[] state, LocalDateTime start, LocalDateTime end, int status, String company);

    @Query(value = "SELECT * FROM get_table_calendar_with_hours(:start, :end, CAST(:state AS int[])) d where exists (select tableid from v_table where companyID=:company and v_table.status=:status and v_table.tableID = d.tableID) and d.reservation_state in :state order by tableid,day asc", nativeQuery = true)
    List<TableDetailDispoDTO> findDetailDisponibility(Integer[] state, LocalDateTime start, LocalDateTime end, int status, String company);

    @Query(value = "SELECT * FROM get_table_day_with_hours(:start, :end, CAST(:state AS int[])) d where exists (select tableid from v_table where companyID=:company and v_table.status=:status and v_table.tableID = d.tableID) and d.reservation_state in :state order by tableid,actual_arrival asc", nativeQuery = true)
    List<TableDetailDayDispoDTO> findDetailDayDisponibility(Integer[] state, LocalDateTime start, LocalDateTime end, int status, String company);
}
