package gendev.it.serenity.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.users.application.UserService;
import gendev.it.serenity.users.domain.dto.UserResponseDTO;




@RestController
@RequestMapping("api/user")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.OPTIONS })
public class UsersController extends CommonController<UserResponseDTO,UserService> {


    public UsersController(UserService service) {
        super(service);
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAll(@RequestParam(name = "status", required = false) Integer status, @RequestParam String company) {
        try {
            return ResponseEntity.ok(getService().findAllByCompany(company,status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }
    @GetMapping("/all/{page}/{size}")
    public ResponseEntity<?> findAllpaginateModel(@PathVariable("page") int page, @PathVariable("size") int size,
            @RequestParam(name = "field", defaultValue = "name", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam String company
        ) {
        try {
            return ResponseEntity.ok(getService().paginateAllByCompany(page, size, field, sort, status,company));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

}
