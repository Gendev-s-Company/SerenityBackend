package gendev.it.serenity.restaurant.application.dish;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.restaurant.domain.dto.dish.DishTypeDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishType;
import gendev.it.serenity.restaurant.infrastructure.repository.dish.DishTypeRepo;

@Service
public class DishTypeService extends CommonService<DishType, DishTypeDTO, String, DishTypeRepo> {

    public DishTypeService(DishTypeRepo jpa) {
        super(jpa);
        // TODO Auto-generated constructor stub
    }

    public Page<DishTypeDTO> paginateAllByCompanyGroupByType(int pageNumber, int pageSize, String field, String sort,
            Integer status, String company) throws Exception {
        // throw new Exception("Veuillez implémenter la function paginateAllByCompany");
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findPaginateByStatusAndCompany(state, company, pageable)
                .map(p -> (DishTypeDTO) p.entityToDTOWithDishes());
    }

}
