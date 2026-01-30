package gendev.it.serenity.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.users.application.UserService;
import gendev.it.serenity.users.domain.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("api/auth")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.OPTIONS })
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO body) {
        try {
            UserDTO loggedUser = service.login(body);
            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<?> changePassword(
            @PathVariable String id, 
            @RequestParam String oldPassword, 
            @RequestParam String newPassword) {
        try {
            String result = service.updatePassword(id, oldPassword, newPassword);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
