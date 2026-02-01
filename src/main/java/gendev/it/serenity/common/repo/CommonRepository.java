package gendev.it.serenity.common.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;

import gendev.it.serenity.common.infrastructure.BaseEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@NoRepositoryBean
public interface CommonRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {
    List<T> findAllByStatus(int status);

    Page<T> findAllByStatus(int status, Pageable page);
}
