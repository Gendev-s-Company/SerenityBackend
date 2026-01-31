package gendev.it.serenity.users.domain.dto;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.regex.Pattern;

@NoArgsConstructor
@Getter
public class CompanyDTO extends DTO{
    
    private String companyID = null;

    private String name;
    
    private String phone;
    
    private String mail;

    public CompanyDTO(String companyID, String name, String phone, String mail) {
        this.companyID = companyID;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
    }

    public CompanyDTO(String name, String phone, String mail) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public void setName(String name) throws Exception {
        if (name.isBlank()) {
            throw new Exception("Veuillez entrer un nom valide");
        }
        this.name = name;
    }

    public void setPhone(String phone) throws Exception {
        // eto asina verification hoe bon format ve ilay phone sinon  exception
        // exemple ito
        if (phone.length()>10) {
            throw new Exception("Veuillez vérifier la longueur du numéro telephone");
        }
        this.phone = phone;
    }

    public void setMail(String mail) {
        // eto asina verification hoe bon format ve ilay mail sinon  exception
        if(!isValidEmail(mail)){
            throw new IllegalArgumentException("Format d'email invalide");
        }
        this.mail = mail;
    }

    // Verification format d'email
    private boolean isValidEmail(String mail) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, mail);
    }

    public Company dtoToEntity(){
        return new Company(getCompanyID(),getName(),getPhone(),getMail());
    }
}
