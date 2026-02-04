package gendev.it.serenity.users.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.users.application.WorkscheduleService;
import gendev.it.serenity.users.domain.dto.WorkscheduleDTO;

@RestController
@RequestMapping("api/workschedule")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.OPTIONS })
public class WorkscheduleController extends CommonController<WorkscheduleDTO,WorkscheduleService> {

    public WorkscheduleController(WorkscheduleService service) {
        super(service);
        //TODO Auto-generated constructor stub
    }

}
