package gendev.it.serenity.hotel.application;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.ActivityPriceDTO;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityPrice;
import gendev.it.serenity.hotel.infrastructure.repository.ActivityPriceRepo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;


@Service
public class ActivityPriceService extends CommonService<ActivityPrice, ActivityPriceDTO, Integer, ActivityPriceRepo> {

    public ActivityPriceService(ActivityPriceRepo jpa) {
        super(jpa);
    }
    // maka prix farany
    public ActivityPriceDTO findLastPrice(String activity, Integer state) throws Exception{
        int status = state != null ? state : 0;
        ActivityPrice last = getJpa().findLastByStatusAndDateChangedDesc(activity, status);
        ActivityPriceDTO res = new ActivityPriceDTO();
        res.setHourPrice(1);
        res.setPrice(new BigDecimal(0));
        return last != null ? last.entityToDTO() : res;
        
    }
     public List<ActivityPriceDTO> findAllByActivity(String activityID, Integer state) throws Exception {
        int status = state != null ? state : 0;
        List<ActivityPrice> result = getJpa().findAllBActivity(activityID, status);
        return super.conversion(result);
    }

    public Page<ActivityPriceDTO> paginateAllByACtivity(int pageNumber, int pageSize, String field, String sort,
            Integer status, String activityID) throws Exception {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findAllBActivity(activityID, state, pageable)
                .map(p -> (ActivityPriceDTO) p.entityToDTO());
    }

}
