package gendev.it.serenity.users.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.users.infrastructure.entity.Workschedule;

@Repository
public interface WorkscheduleRepo extends CommonRepository<Workschedule,Integer> {
    List<Workschedule> findByUserIDAndStatus(String userid, int status);

    @Query(value = "select * from Workschedule where status= :status and userid IN :userids and userid in (select userid from v_users v where v.companyid= :company)  ", nativeQuery = true)
    List<Workschedule> findByUserIDInAndStatus(List<String> userids, Integer status,String company);
    
    @Query(value = "select * from Workschedule where status= :status and userid in (select userid from v_users v where v.companyid= :company)  ", nativeQuery = true)
    List<Workschedule> findStatusAndCompany(int status, String company);

    @Query(value = "select * from Workschedule where status= :status and userid in (select userid from v_users v where v.companyid= :company)  ", nativeQuery = true)
    Page<Workschedule> findPaginateByStatusAndCompany(int status, String company, Pageable pageable);
    
    @Query(value = "select * from Workschedule where status= :status and userid= :userid and userid in (select userid from v_users v where v.companyid= :company)  ", nativeQuery = true)
    Page<Workschedule> paginatedfindByUserIDAndStatusAndCompany(String userid, int status, String company,Pageable pageable);

    @Query(value = "select * from Workschedule where status= :status and userid= :userid and userid in (select userid from v_users v where v.companyid= :company)  ", nativeQuery = true)
    List<Workschedule> findByUserIDAndStatusAndCompany(String userid, int status, String company);


}
