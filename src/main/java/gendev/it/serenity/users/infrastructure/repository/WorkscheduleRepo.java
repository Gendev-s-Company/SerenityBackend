package gendev.it.serenity.users.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.users.infrastructure.entity.Workschedule;

@Repository
public interface WorkscheduleRepo extends CommonRepository<Workschedule,Integer> {
    List<Workschedule> findByUserIDAndStatus(String userid, int status);
    @Query(value = "select * from Workschedule where status= :status and userid= :userid and userid in (select userid from v_users v where v.companyid= :company)  ", nativeQuery = true)
    List<Workschedule> findByUserIDAndStatusAndCompany(String userid, int status, String company);
    List<Workschedule> findByUserIDInAndStatus(List<String> userids, Integer status);
    @Query(value = "select * from Workschedule where status= :status and userid in (select userid from v_users v where v.companyid= :company)  ", nativeQuery = true)
    List<Workschedule> findStatusAndCompany(int status, String company);

}
