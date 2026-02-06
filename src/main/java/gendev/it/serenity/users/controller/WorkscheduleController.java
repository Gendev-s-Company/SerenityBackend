package gendev.it.serenity.users.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.users.application.UserService;
import gendev.it.serenity.users.application.WorkscheduleService;
import gendev.it.serenity.users.domain.dto.WorkscheduleDTO;

@RestController
@RequestMapping("api/workschedule")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.OPTIONS })
public class WorkscheduleController extends CommonController<WorkscheduleDTO,WorkscheduleService> {

    private final UserService userService;
    private final WorkscheduleService workscheduleService;


    public WorkscheduleController(WorkscheduleService service, UserService userService) {
        super(service);
        this.workscheduleService = service;
        this.userService = userService;
    }


    @GetMapping("/calendar")
    public ResponseEntity<?> getCalendarByAuthority(@RequestParam("userId") String userId) {
        try {
            List<WorkscheduleDTO> list = workscheduleService.getByAuthority(userId);

            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/calendar/choice")
    public ResponseEntity<List<WorkscheduleDTO>> getByMultipleIds(@RequestParam List<String> userids) {
        try {
            return ResponseEntity.ok(workscheduleService.choiceSearch(userids));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
