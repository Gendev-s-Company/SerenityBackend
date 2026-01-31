package gendev.it.serenity.common.repo;

import java.util.List;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonRepository<T, ID> extends JpaRepository<T, ID>{
    List<T> findAllByStatus(int status);
    Page<T> findAllByStatus(int status, Pageable page);
}