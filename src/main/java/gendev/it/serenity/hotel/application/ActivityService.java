package gendev.it.serenity.hotel.application;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.ActivityDTO;
import gendev.it.serenity.hotel.infrastructure.entity.Activity;
import gendev.it.serenity.hotel.infrastructure.repository.ActivityRepo;
import org.springframework.data.domain.Page;

@Service
public class ActivityService extends CommonService<Activity, ActivityDTO, String, ActivityRepo> {

    public ActivityService(ActivityRepo jpa) {
        super(jpa);
    }

    public List<ActivityDTO> findAllByCompany(String company, Integer state) {
        int status = state != null ? state : 0;
        List<Activity> result = getJpa().findAllByStatusAndCompany(status,company);
        return super.conversion(result);
    }

    public Page<ActivityDTO> paginateAllByCompany(int pageNumber, int pageSize, String field, String sort,
            Integer status, String company) {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findPaginateByStatusAndCompany(state, company, pageable)
                .map(p -> (ActivityDTO) p.entityToDTO());
    }

}
