package gendev.it.serenity.common.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.Query;

import gendev.it.serenity.common.infrastructure.BaseEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@NoRepositoryBean
public interface CommonRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {
  List<T> findAllByStatus(int status);

  Page<T> findAllByStatus(int status, Pageable page);
   @Query(value = "select * from Workschedule", nativeQuery = true)
    List<T> findAllByStatusAndCompany(int status, String company);

    // On utilise un nom personnalisé pour éviter que Spring cherche "company" dans BaseEntity
   @Query(value = "select * from Workschedule", nativeQuery = true)
    Page<T> findPaginateByStatusAndCompany(int status, String companyd, Pageable pageable);
}
