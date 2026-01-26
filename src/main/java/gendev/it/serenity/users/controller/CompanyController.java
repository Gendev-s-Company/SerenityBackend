package gendev.it.serenity.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import gendev.it.serenity.users.application.CompanyService;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/company")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }
    // Eto mamoaka anle exception depuis setter mandeha automatiquement
    // afaka afindra any anaty classe controller common
    // comme les autres fonctions
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException e) {
       
        Throwable cause = e.getMostSpecificCause();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("Erreur de validation : " + cause.getMessage());
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

}
