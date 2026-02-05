package gendev.it.serenity.users.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.users.infrastructure.entity.Workschedule;

@Repository
public interface WorkscheduleRepo extends CommonRepository<Workschedule,Integer> {
    List<Workschedule> findByUserIDAndStatus(String userid, int status);
    List<Workschedule> findByUserIDInAndStatus(List<String> userids, Integer status);

}
