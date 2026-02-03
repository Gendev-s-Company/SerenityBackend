package gendev.it.serenity.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.users.application.ProfilService;
import gendev.it.serenity.users.domain.dto.ProfilDTO;

@RestController
@RequestMapping("api/profil")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })
public class ProfilController extends CommonController<ProfilDTO, ProfilService> {

     private final ProfilService profilService;

    public ProfilController(ProfilService service) {
        super(service);
        this.profilService = service;
    }


    // Creation de profil
    @Override
    @PostMapping("")
    public ResponseEntity<?> saveModel(@RequestBody ProfilDTO body) {
        try {
            return new ResponseEntity<>(profilService.create(body), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Liste des profils
    @Override
    // @GetMapping("")
    public ResponseEntity<?> findAllModel(@RequestParam(name = "status", required = false) Integer status) {
        return profilService.findAll().isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("Aucun profil n'est disponible")
                : ResponseEntity.ok(profilService.findAll());
    }

    // Profil par ID
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findEntityByID(@PathVariable String id,@RequestParam(name = "status", required = false) Integer status) {
        try {
            return ResponseEntity.ok(profilService.findOneById(id));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Update profil
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateModel(@RequestBody ProfilDTO body, @PathVariable String id, @RequestParam(name = "status", required = false) Integer status) {
         try {
             return ResponseEntity.ok(profilService.update(id, body));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
         }
    }

    // Delete profil
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            profilService.delete(id);
        return ResponseEntity.ok("Profil supprimé");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Suppression impossible : des utilisateurs sont liés à ce profil");
        }
    }


}
