package gendev.it.serenity.restaurant.application.tables;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.restaurant.domain.dto.tables.TableTypeDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableType;
import gendev.it.serenity.restaurant.infrastructure.repository.tables.TableTypeRepo;

@Service
public class TableTypeService extends CommonService<TableType, TableTypeDTO, String, TableTypeRepo>{

    public TableTypeService(TableTypeRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }
    
}
