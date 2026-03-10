package gendev.it.serenity.hotel.infrastructure.repository.room.reservation;

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
}
