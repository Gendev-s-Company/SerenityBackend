package gendev.it.serenity.hotel.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityPrice;

@Repository
public interface ActivityPriceRepo extends CommonRepository<ActivityPrice, Integer>{

    @Query("SELECT a FROM ActivityPrice a where a.activity.activityID = :activity and a.status=:status")
    List<ActivityPrice> findAllBActivity(String activity, int status);

    @Query("SELECT a FROM ActivityPrice a where a.activity.activityID = :activity and a.status=:status")
    Page<ActivityPrice> findAllBActivity(String activity, int status, Pageable page);

    @Query("SELECT a FROM ActivityPrice a where a.activity.activityID = :activity and a.status=:status and a.dateChanged is not null order by a.dateChanged desc limit 1")
    ActivityPrice findLastByStatusAndDateChangedDesc(String activity, int status);
}
