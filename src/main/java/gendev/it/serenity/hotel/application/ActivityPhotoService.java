package gendev.it.serenity.hotel.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.ActivityPhotoDTO;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityPhoto;
import gendev.it.serenity.hotel.infrastructure.repository.ActivityPhotoRepo;

@Service
public class ActivityPhotoService extends CommonService<ActivityPhoto, ActivityPhotoDTO, String, ActivityPhotoRepo> {

    public ActivityPhotoService(ActivityPhotoRepo jpa) {
        super(jpa);
        // TODO Auto-generated constructor stub
    }

    public List<ActivityPhotoDTO> findAllByActivity(String activityID, Integer state) throws Exception {
        int status = state != null ? state : 0;
        List<ActivityPhoto> result = getJpa().findAllBActivity(activityID, status);
        return super.conversion(result);
    }

    public Page<ActivityPhotoDTO> paginateAllByACtivity(int pageNumber, int pageSize, String field, String sort,
            Integer status, String activityID) throws Exception {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findAllBActivity(activityID, state, pageable)
                .map(p -> (ActivityPhotoDTO) p.entityToDTO());
    }
}
