package gendev.it.serenity.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.users.application.ProfilService;
import gendev.it.serenity.users.domain.dto.ProfilDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/profil")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })
public class ProfilController extends CommonController<ProfilDTO, ProfilService> {

    // private final ProfilService profilService;

    public ProfilController(ProfilService service) {
        super(service);
        // this.profilService = service;
    }

    @GetMapping("/profil")
    public ResponseEntity<?> findAll(@RequestParam String companyId) {
        try {
            return ResponseEntity.ok(getService().findProfilByCurrentCompanyId(companyId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    @GetMapping("/paginate")
    public ResponseEntity<?> getPaginate(@RequestParam String companyId,@RequestParam int page,@RequestParam int size,@RequestParam String field,@RequestParam String sort) {
        return ResponseEntity.ok(
                getService().getPaginateByCompany(companyId, page, size, field, sort, 0)
        );
    }

}
