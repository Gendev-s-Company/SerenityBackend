package gendev.it.serenity.hotel.infrastructure.repository.room;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomType;

@Repository
public interface RoomTypeRepo extends CommonRepository<RoomType, String> {
    @Override
    @Query("SELECT a FROM RoomType a WHERE a.status = :status AND a.company.id = :company")
    List<RoomType> findAllByStatusAndCompany(int status, String company);

    @Override
    @Query("SELECT a FROM RoomType a WHERE a.status = :status AND a.company.id = :company")
    Page<RoomType> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
}
