package gendev.it.serenity.restaurant.controller.tables;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.restaurant.application.tables.TableTypeService;
import gendev.it.serenity.restaurant.domain.dto.tables.TableTypeDTO;

@RestController
@RequestMapping("restaurant/tabletype")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class TableTypeController extends CommonController<TableTypeDTO, TableTypeService>{

    public TableTypeController(TableTypeService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }
    
}
