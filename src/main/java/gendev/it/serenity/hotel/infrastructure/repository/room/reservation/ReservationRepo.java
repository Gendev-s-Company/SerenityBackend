package gendev.it.serenity.hotel.infrastructure.repository.room.reservation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.hotel.infrastructure.entity.room.reservation.Reservation;

@Repository
public interface ReservationRepo extends CommonRepository<Reservation, String>{

    @Override
    @Query("SELECT a FROM Reservation a WHERE a.status = :status AND a.room.type.company.id = :company")
    List<Reservation> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM Reservation a WHERE a.status = :status AND a.room.type.company.id = :company")
    Page<Reservation> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE (r.status = :status AND r.room.type.company.id = :company) AND (r.state IN :state) AND (r.starttime >= :start AND r.endtime <= :end)")
    List<Reservation> findDisponibility(int status, String company, List<Integer> state, 
        LocalDateTime start, LocalDateTime end);
    @Query("SELECT r FROM Reservation r WHERE (r.status = :status AND r.room.type.company.id = :company) AND (r.state IN :state) AND (r.starttime >= :start AND r.endtime <= :end)")
    Page<Reservation> findDisponibility(int status, String company, List<Integer> state, 
        LocalDateTime start, LocalDateTime end, Pageable pageable);
}
