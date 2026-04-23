package gendev.it.serenity.restaurant.application.dish;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.ActivityPriceDTO;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityPrice;
import gendev.it.serenity.restaurant.domain.dto.dish.DishPriceDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishPrice;
import gendev.it.serenity.restaurant.infrastructure.repository.dish.DishPriceRepo;

@Service
public class DishPriceService extends CommonService<DishPrice, DishPriceDTO, Integer, DishPriceRepo>{

    public DishPriceService(DishPriceRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

    public DishPriceDTO findLastPrice(String dish, Integer state) throws Exception{
        int status = state != null ? state : 0;
        DishPrice last = getJpa().findLastByStatusAndDateChangedDesc(dish, status);
        DishPriceDTO res = new DishPriceDTO();
        res.setPrice(new BigDecimal(0));
        return last != null ? last.entityToDTO() : res;
        
    }
     public List<DishPriceDTO> findAllByDish(String dish, Integer state) throws Exception {
        int status = state != null ? state : 0;
        List<DishPrice> result = getJpa().findAllByDish(dish, status);
        return super.conversion(result);
    }

    public Page<DishPriceDTO> paginateAllByDish(int pageNumber, int pageSize, String field, String sort,
            Integer status, String dishID) throws Exception {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findAllByDish(dishID, state, pageable)
                .map(p -> (DishPriceDTO) p.entityToDTO());
    }
}
