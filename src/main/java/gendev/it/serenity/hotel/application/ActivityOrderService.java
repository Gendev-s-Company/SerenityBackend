package gendev.it.serenity.hotel.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.ActivityOrderDTO;
import gendev.it.serenity.hotel.domain.dto.ActivityPriceDTO;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityOrder;
import gendev.it.serenity.hotel.infrastructure.repository.ActivityOrderRepo;

@Service
public class ActivityOrderService extends CommonService<ActivityOrder, ActivityOrderDTO, String, ActivityOrderRepo>{
    private final ActivityPriceService priceService;
    public ActivityOrderService(ActivityOrderRepo jpa, ActivityPriceService priceService) {
        super(jpa);
        this.priceService = priceService;
        //TODO Auto-generated constructor stub
    }


    @Override
    public ActivityOrderDTO save(ActivityOrderDTO model) throws Exception {
        // TODO Auto-generated method stub
        ActivityPriceDTO price = priceService.findLastPrice(model.getActivity().getActivityID(), 0);
        model.setPrice(price.getPrice());
        return super.save(model);
    }


    public List<ActivityOrderDTO> findAllByActivity(String activityID, Integer state) throws Exception {
        int status = state != null ? state : 0;
        List<ActivityOrder> result = getJpa().findAllBActivity(activityID, status);
        return super.conversion(result);
    }

    public Page<ActivityOrderDTO> paginateAllByACtivity(int pageNumber, int pageSize, String field, String sort,
            Integer status, String activityID) throws Exception {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findAllBActivity(activityID, state, pageable)
                .map(p -> (ActivityOrderDTO) p.entityToDTO());
    }

    public List<ActivityOrderDTO> findAllByCustomer(String customer, Integer state) throws Exception {
        // throw new Exception("Veuillez implémenter la function findAllByCompany");
        int status = state != null ? state : 0;
        List<ActivityOrder> result = getJpa().findAllByCustomer(customer, status);
        return super.conversion(result);
    }

    public Page<ActivityOrderDTO> paginateAllByCustomer(int pageNumber, int pageSize, String field, String sort,
            Integer status, String customer) throws Exception {
        // throw new Exception("Veuillez implémenter la function paginateAllByCompany");
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findAllByCustomer(customer, state, pageable)
                .map(p -> (ActivityOrderDTO) p.entityToDTO());
    }

}
