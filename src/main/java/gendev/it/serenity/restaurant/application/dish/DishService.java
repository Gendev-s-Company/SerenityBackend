package gendev.it.serenity.restaurant.application.dish;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.restaurant.domain.dto.dish.DishDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.Dish;
import gendev.it.serenity.restaurant.infrastructure.repository.dish.DishRepo;

@Service
public class DishService extends CommonService<Dish, DishDTO, String, DishRepo> {

    public DishService(DishRepo jpa) {
        super(jpa);
        // TODO Auto-generated constructor stub
    }

    public void updateState(String id, Integer state) throws Exception {
        Dish dishToUpdate = findById(id, 0).dtoToEntity();
        if (state == null)
            throw new Exception("veuillez indiquer le state");
        dishToUpdate.setState(state);
        getJpa().save(dishToUpdate);
    }

    public Page<DishDTO> paginateAllByCompanyGroupByType(int pageNumber, int pageSize, String field, String sort,
            Integer status, String company) throws Exception {
        // throw new Exception("Veuillez implémenter la function paginateAllByCompany");
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findPaginateByStatusAndCompanyGroupByType(state, company, pageable)
                .map(p -> (DishDTO) p.entityToDTO());
    }

}
