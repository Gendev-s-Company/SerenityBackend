package gendev.it.serenity.pack.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.pack.application.PackService;
import gendev.it.serenity.pack.dto.PackDTO;

@RestController
@RequestMapping("pack")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class PackController extends CommonController<PackDTO, PackService> {

    public PackController(PackService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }
    
}
