package gendev.it.serenity.customer.domain.dto;

import java.util.regex.Pattern;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.utils.Utils;
import gendev.it.serenity.customer.infrastructure.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends DTO<Customer>{
  private String customerID = null;

    private String name;
    
    private String phone;
    
    private String mail;


    public CustomerDTO(String customerID) {
        this.customerID = customerID;
    }

    public CustomerDTO(String customerID, String name, String phone, String mail, int status) {
        this.customerID = customerID;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        setStatus(status);
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    
    public void setName(String name) throws Exception {
        if (name.isBlank() && !isSkipValidation()) {
            throw new Exception("Veuillez entrer un nom valide");
        }
        this.name = name;
    }

    public void setPhone(String phone) throws Exception {
        // eto asina verification hoe bon format ve ilay phone sinon  exception
        // exemple ito
        if (phone.length()>Utils.phoneLengthValidation && !isSkipValidation()) {
            throw new Exception("Veuillez vérifier la longueur du numéro telephone");
        }
        this.phone = phone;
    }

    public void setMail(String mail) {
        // eto asina verification hoe bon format ve ilay mail sinon  exception
        System.out.println(isSkipValidation());
        if(!isValidEmail(mail) && !isSkipValidation()){
            throw new IllegalArgumentException("Format d'email invalide");
        }
        this.mail = mail;
    }

    // Verification format d'email
    private boolean isValidEmail(String mail) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, mail);
    }

    @Override
    public Customer dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new Customer(customerID, name, phone, mail, getStatus());
    }
    
}
