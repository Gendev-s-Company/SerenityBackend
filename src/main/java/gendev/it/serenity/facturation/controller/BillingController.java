package gendev.it.serenity.facturation.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gendev.it.serenity.common.controller.CommonController;
import gendev.it.serenity.core.exception.BusinessException;
import gendev.it.serenity.facturation.application.BillingService;
import gendev.it.serenity.facturation.dto.BillingDTO;

@RestController
@RequestMapping("bill")
@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class BillingController extends CommonController<BillingDTO, BillingService> {

    public BillingController(BillingService service) {
        super(service);
        // TODO Auto-generated constructor stub
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id, @RequestBody BillingDTO toUpdate,
            @RequestParam Boolean isDetail) throws BusinessException {
        return ResponseEntity.ok(getService().updateBilling(toUpdate, id, isDetail));
    }

    @GetMapping("/customers")
    public ResponseEntity<?> findAllCustomerBilled() {
        return null;
    }

    @GetMapping("/customers/{customerid}")
    public ResponseEntity<?> findCustomersBilled(@PathVariable String customerid,
            @RequestParam(required = false, name = "start") LocalDate start,
            @RequestParam(required = false, name = "end") LocalDate end) {
        return null;
    }

}
