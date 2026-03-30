package gendev.it.serenity.restaurant.controller.tables;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.restaurant.application.tables.TableService;
import gendev.it.serenity.restaurant.domain.dto.tables.TableDTO;

@RestController
@RequestMapping("restaurant/table")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class TableController extends CommonController<TableDTO, TableService> {

    public TableController(TableService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }
    
}
