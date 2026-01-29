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

import gendev.it.serenity.users.application.UserService;
import gendev.it.serenity.users.domain.dto.UserDTO;

@RestController
@RequestMapping("api/user")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException e) {
       
        Throwable cause = e.getMostSpecificCause();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("Erreur de validation : " + cause.getMessage());
    }

    
// Creation d'utilsiateur
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody UserDTO body) {
        System.out.print(body.getName());
        try {
            return new ResponseEntity<>(userService.create(body), HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

 // Liste des utilisateurs
    @GetMapping("")
    public ResponseEntity<?> getMethodName() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
   
// Utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMethodName(@PathVariable String id) {
        try {
            return ResponseEntity.ok(userService.findOneById(id));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

// Update user
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserDTO body) {
         try {
             return ResponseEntity.ok(userService.update(id, body));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
    }

// Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletUser(@PathVariable String id) {
        try {
            userService.delete(id);
        return ResponseEntity.ok("Utilisateur supprimé");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
