package gendev.it.serenity.restaurant.infrastructure.repository.tables;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TablePhoto;

@Repository
public interface TablePhotoRepo extends CommonRepository<TablePhoto, String>{
    List<TablePhoto> findByTableIDAndStatus(String tableID, int status);

    Page<TablePhoto> findByTableIDAndStatus(String tableID, int status, Pageable page);
}
