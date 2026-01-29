package gendev.it.serenity.users.domain.dto;
import java.time.LocalDate;

import gendev.it.serenity.users.infrastructure.entity.Profil;
import gendev.it.serenity.users.infrastructure.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class UserDTO {
    private String userID;
    private String  name;
    private String profil;
    private String phone;
    private LocalDate joineddate;
    private String password;
    private int status;
    




    public UserDTO(String name, String profil, String phone, LocalDate joineddate, String password, int status) {
        this.name = name;
        this.profil = profil;
        this.phone = phone;
        this.joineddate = joineddate;
        this.password = password;
        this.status = status;
    }



    public UserDTO(String userID, String name, String profil, String phone, LocalDate joineddate, String password,
            int status) {
        this.userID = userID;
        this.name = name;
        this.profil = profil;
        this.phone = phone;
        this.joineddate = joineddate;
        this.password = password;
        this.status = status;
    }

    


    public UserDTO(String userID, String name, String profil, String phone, LocalDate joineddate, int status) {
        this.userID = userID;
        this.name = name;
        this.profil = profil;
        this.phone = phone;
        this.joineddate = joineddate;
        this.status = status;
    }

    public void setName(String name)  throws Exception {
        if (name.isBlank()) {
            throw new Exception("Veuillez entrer un nom valide");
        }
        this.name = name;
    }

  
    public void setProfil(String profil) throws Exception {
        if (profil.isBlank()) {
            throw new Exception("Veuillez choisir un profil existant");
        }
        this.profil = profil;
    }

    public void setPhone(String phone) throws Exception {
        if (phone.length()>10) {
            throw new Exception("Veuillez vérifier la longueur du numéro telephone");
        }
        this.phone = phone;
    }

    public void setJoineddate(LocalDate joineddate) {

        this.joineddate = joineddate;
    }

    public void setPassword(String password)throws Exception {
        if (password.isBlank()) {
            throw new Exception("Veuillez entrer un mot de passe valide");
        }
        this.password = password;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Users dtoToEntity(Profil profil) {
        return new Users(getName(),profil,getPhone(),getJoineddate(),getPassword(),getStatus());
    }

}
