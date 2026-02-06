package gendev.it.serenity.users.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.users.application.ProfilService;
import gendev.it.serenity.users.domain.dto.ProfilDTO;

@RestController
@RequestMapping("api/profil")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })
public class ProfilController extends CommonController<ProfilDTO, ProfilService> {

    // private final ProfilService profilService;

    public ProfilController(ProfilService service) {
        super(service);
        // this.profilService = service;
    }

}
