package gendev.it.serenity.users.infrastructure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.users.infrastructure.entity.Profil;
import java.util.List;



@Repository
public interface ProfilRepo extends CommonRepository<Profil, String>
{
    @Query("SELECT p FROM Profil p WHERE p.status = 0 AND p.company.id = :companyId")
    List<Profil> findAllByStatusAndCompany(@Param("companyId") String companyId);

    @Query("SELECT p FROM Profil p WHERE p.status = :status AND p.company.id = :companyId")
    Page<Profil> findAllByCompanyAndStatus(@Param("companyId") String companyId,@Param("status") int status,Pageable pageable);

}
