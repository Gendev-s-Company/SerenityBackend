package gendev.it.serenity.restaurant.infrastructure.repository.tables;

import java.time.LocalDateTime;
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

      @Query("SELECT r FROM TableOccupation r WHERE (r.status = :status AND r.table.tabletype.company.id = :company) AND (r.state IN :state) AND (r.starttime >= :start AND r.endtime <= :end)")
    List<TableOccupation> findDisponibility(int status, String company, List<Integer> state, 
        LocalDateTime start, LocalDateTime end);
    @Query("SELECT r FROM TableOccupation r WHERE (r.status = :status AND r.table.tabletype.id = :company) AND (r.state IN :state) AND (r.starttime >= :start AND r.endtime <= :end)")
    Page<TableOccupation> findDisponibility(int status, String company, List<Integer> state, 
        LocalDateTime start, LocalDateTime end, Pageable pageable);
    
    @Query("SELECT r FROM TableOccupation r WHERE (r.status = :status AND r.table.tabletype.company.id = :company) AND (r.state IN :state) AND (r.starttime >= :start)")
    Page<TableOccupation> findDisponibilityDateEndnull(int status, String company, List<Integer> state, 
        LocalDateTime start, Pageable pageable);

    @Query("SELECT r FROM TableOccupation r WHERE (r.status = :status AND r.table.tabletype.company.id = :company) AND (r.state IN :state) AND (r.endtime <= :end)")
    Page<TableOccupation> findDisponibilityDateStartnull(int status, String company, List<Integer> state, LocalDateTime end, Pageable pageable);

    @Query("SELECT r FROM TableOccupation r WHERE (r.status = :status AND r.table.id = :idtable) AND (r.starttime >= :start AND r.endtime <= :end)")
    Page<TableOccupation> findDisponibilityByIDtableAndDate(int status, String idtable,LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT r FROM TableOccupation r WHERE (r.status = :status AND r.table.id = :idtable) AND (r.endtime <= :end)")
    Page<TableOccupation> findDisponibilityByIDtableAndDateStartnull(int status, String idtable, LocalDateTime end, Pageable pageable);
    
    @Query("SELECT r FROM TableOccupation r WHERE (r.status = :status AND r.table.id = :idtable) AND (r.starttime >= :start)")
    Page<TableOccupation> findDisponibilityByIDtableAndDateEndnull(int status, String idtable, LocalDateTime start, Pageable pageable);

}
