package gendev.it.serenity.hotel.infrastructure.repository.room;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.hotel.domain.dto.room.RoomDetailDispoDTO;
import gendev.it.serenity.hotel.domain.dto.room.RoomDisponibilityDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.Room;

@Repository
public interface RoomRepo extends CommonRepository<Room, String> {
    @Override
    @Query("SELECT a FROM Room a WHERE a.status = :status AND a.type.company.id = :company")
    List<Room> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM Room a WHERE a.status = :status AND a.type.company.id = :company")
    Page<Room> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);

    @Query(value = "SELECT * FROM get_rooms_disponibility(:start, :end, CAST(:state AS int[])) d where exists (select roomid from v_room where companyID=:company and v_room.status=:status and v_room.roomID = d.roomID) order by roomid asc", nativeQuery = true)
    List<RoomDisponibilityDTO> findDisponibility(Integer[] state, LocalDateTime start, LocalDateTime end, int status, String company);

    @Query(value = "SELECT * FROM get_room_calendar_with_hours(:start, :end, CAST(:state AS int[])) d where exists (select roomid from v_room where companyID=:company and v_room.status=:status and v_room.roomID = d.roomID) order by roomid asc", nativeQuery = true)
    List<RoomDetailDispoDTO> findDetailDisponibility(Integer[] state, LocalDateTime start, LocalDateTime end, int status, String company);
}
