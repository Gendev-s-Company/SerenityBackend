package gendev.it.serenity.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.users.application.ProfilService;
import gendev.it.serenity.users.domain.dto.ProfilDTO;

@RestController
@RequestMapping("api/profil")
public class ProfilController {

    private final ProfilService profilService;

    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException e) {
       
        Throwable cause = e.getMostSpecificCause();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("Erreur de validation : " + cause.getMessage());
    }

    // Creation de profil
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ProfilDTO body) {
        try {
            return new ResponseEntity<>(profilService.create(body), HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Liste des profils
    @GetMapping("")
    public ResponseEntity<?> getMethodName() {
        return new ResponseEntity<>(profilService.findAll(), HttpStatus.OK);
    }

    // Profil par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMethodName(@PathVariable String id) {
        try {
            return ResponseEntity.ok(profilService.findOneById(id));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Update profil
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfil(@PathVariable String id, @RequestBody ProfilDTO body) {
         try {
             return ResponseEntity.ok(profilService.update(id, body));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
    }
    // Delete profil
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfil(@PathVariable String id) {
        try {
            profilService.delete(id);
        return ResponseEntity.ok("Profil supprimé");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Suppression impossible : des utilisateurs sont liés à ce profil");
        }
    }
}
