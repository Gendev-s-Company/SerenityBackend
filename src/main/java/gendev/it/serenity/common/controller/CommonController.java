package gendev.it.serenity.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;

/*
 * Classe dont tous les controller entity réalisant un crud
 */
public class CommonController<D extends DTO,S extends CommonService> {
    private final S service;

    public CommonController(S service) {
        this.service = service;
    }

    public S getService() {
        return service;
    }

    @PostMapping("")
    public ResponseEntity<?> saveModel(@RequestBody D model) {
        try {
            return new ResponseEntity<>(service.save(model), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateModel(@RequestBody D model,@PathVariable String id) {
        try {
            service.update(model, id);
            return ResponseEntity.ok("Modification réussi");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            service.deleteById(id);
            return ResponseEntity.ok("Suppression réussi");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Suppression impossible : des utilisateurs sont liés à ce profil ou Profil introuvable");

        }
    }

    @GetMapping("")
    public ResponseEntity<?> findAllModel(@RequestParam(value = "key", required = false) String key) {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findEntityByID(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<?> findAllpaginateModel(@PathVariable("page") int page, @PathVariable("size") int size,
            @RequestParam(name = "field", defaultValue = "name", required = false) String field,
            @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort) {
        try {
            return ResponseEntity.ok(service.getPaginated(page, size, field, sort));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    // @GetMapping("/count")
    // public ResponseEntity<?> countAllModel() {
    // try {
    // return ResponseEntity.ok(new ToJsonData<>(getService().getJpa().count(),
    // null));
    // } catch (Exception e) {
    // e.printStackTrace();
    // return new ResponseEntity<>(new ToJsonData<>(null, e.getMessage()),
    // org.springframework.http.HttpStatus.NOT_ACCEPTABLE);
    // }
    // }

}
