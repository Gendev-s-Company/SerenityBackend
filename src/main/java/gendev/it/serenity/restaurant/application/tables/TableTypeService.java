package gendev.it.serenity.restaurant.application.tables;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.restaurant.domain.dto.tables.TableTypeDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableType;
import gendev.it.serenity.restaurant.infrastructure.repository.tables.TableTypeRepo;
import org.springframework.data.domain.Pageable;
@Service
public class TableTypeService extends CommonService<TableType, TableTypeDTO, String, TableTypeRepo>{

    public TableTypeService(TableTypeRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

    public List<TableTypeDTO> findAllTablesByCompanyGroupByType(String company, Integer status) {
    int state = status != null ? status : 0;
    return getJpa().findAllByStatusAndCompany(state, company)
            .stream()
            .map(p -> (TableTypeDTO) p.entityToDTOWithTables())
            .toList();
    }
    public Page<TableTypeDTO> paginateAllByCompanyGroupByType(int pageNumber, int pageSize, String field, String sort,
        Integer status, String company) throws Exception {
    // throw new Exception("Veuillez implémenter la function paginateAllByCompany");
    Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
    int state = status != null ? status : 0;
    return getJpa().findPaginateByStatusAndCompany(state, company, pageable)
                .map(p -> (TableTypeDTO) p.entityToDTOWithTables());
    }
}
