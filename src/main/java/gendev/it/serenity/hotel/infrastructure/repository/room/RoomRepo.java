package gendev.it.serenity.hotel.infrastructure.repository.room;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.hotel.infrastructure.entity.room.Room;

@Repository
public interface RoomRepo extends CommonRepository<Room, String> {
    @Override
    @Query("SELECT a FROM Room a WHERE a.status = :status AND a.type.company.id = :company")
    List<Room> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM Room a WHERE a.status = :status AND a.type.company.id = :company")
    Page<Room> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
