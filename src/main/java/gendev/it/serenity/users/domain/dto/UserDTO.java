package gendev.it.serenity.users.domain.dto;

import java.time.LocalDate;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.users.infrastructure.entity.Profil;
import gendev.it.serenity.users.infrastructure.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDTO extends DTO<Users> {

    private String userID =null;
    private String name;
    private String profilID;
    private String phone;
    private LocalDate joineddate = LocalDate.now();
    private String password;


    public UserDTO(String userID, String name, String profilID, String phone, LocalDate joineddate, String password) {
        this.userID = userID;
        this.name = name;
        this.profilID = profilID;
        this.phone = phone;
        this.joineddate = joineddate;
        this.password = password;
    }
    

    public UserDTO(String name, String profilID, String phone, LocalDate joineddate, String password) {
        this.name = name;
        this.profilID = profilID;
        this.phone = phone;
        this.joineddate = joineddate;
        this.password = password;
    }


    public void setName(String name) throws Exception {
        if (name == null || name.isBlank()) {
            throw new Exception("Veuillez entrer un nom valide");
        }
        this.name = name;
    }

    public void setProfilID(String profilID) throws Exception {
        if (profilID == null || profilID.isBlank()) {
            throw new Exception("Veuillez choisir un profil existant");
        }
        this.profilID = profilID;
    }

    public void setPhone(String phone) throws Exception {
        if (phone != null && phone.length() > 12) {
            throw new Exception("Format du numéro de téléphone invalide");
        }
        this.phone = phone;
    }

    public void setPassword(String password) throws Exception {
        if (password == null || password.length() < 4) {
            throw new Exception("Le mot de passe est trop court");
        }
        this.password = password;
    }

    public void setJoineddate(LocalDate joineddate) {
        this.joineddate = joineddate;
    }
    public Users dtoToEntity()throws Exception {
        Profil p = new Profil();
        p.setProfilID(profilID);
        return new Users(
                name,
                p, //profil sera injecté dans le SERVICE
                phone,
                joineddate,
                password
        );
    }
}
