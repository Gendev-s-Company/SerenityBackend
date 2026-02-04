package gendev.it.serenity.users.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.users.application.UserService;
import gendev.it.serenity.users.domain.dto.UserDTO;
import gendev.it.serenity.users.domain.dto.UserResponseDTO;




@RestController
@RequestMapping("api/user")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.OPTIONS })
public class UsersController extends CommonController<UserResponseDTO,UserService> {


    public UsersController(UserService service) {
        super(service);

    }

}
