package gendev.it.serenity.hotel.controller;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.hotel.application.ActivityService;
import gendev.it.serenity.hotel.domain.dto.ActivityDTO;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hotel/activity")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class ActivityController extends CommonController<ActivityDTO,ActivityService> {

    public ActivityController(ActivityService service) {
        super(service);
    }

}
