package gendev.it.serenity.hotel.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityPhoto;

@Repository
public interface ActivityPhotoRepo extends CommonRepository<ActivityPhoto, String> {
    @Query("SELECT a FROM ActivityPhoto a where a.activity.activityID = :activity and a.status=:status")
    List<ActivityPhoto> findAllBActivity(String activity, int status);

    @Query("SELECT a FROM ActivityPrice a where a.activity.activityID = :activity and a.status=:status")
    Page<ActivityPhoto> findAllBActivity(String activity, int status, Pageable page);
}
