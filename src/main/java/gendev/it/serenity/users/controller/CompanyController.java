package gendev.it.serenity.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gendev.it.serenity.users.application.CompanyService;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/company")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }


    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CompanyDTO body) {
        try {
            return new ResponseEntity<>(service.create(body), HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getMethodName() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMethodName(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.findOneById(id));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Update company
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable String id, @RequestBody CompanyDTO body) {
         try {
             return ResponseEntity.ok(service.update(id, body));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletCompany(@PathVariable String id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Profil supprimé"); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Suppression impossible : des profils sont liés à cette companie");
        }
    }

}
