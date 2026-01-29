package gendev.it.serenity.users.domain.dto;

import gendev.it.serenity.users.infrastructure.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class UserDTO {
    
    private String userID = null;
    private String name;
    private String profilID;
    private String phone;
    private LocalDate joinedDate;
    private String password;
    private Integer status;

    public UserDTO(String userID, String name, String profilID, String phone, LocalDate joinedDate, Integer status) {
        this.userID = userID;
        this.name = name;
        this.profilID = profilID;
        this.phone = phone;
        this.joinedDate = joinedDate;
        this.status = status;
    }

    public UserDTO(String name, String profilID, String phone, LocalDate joinedDate, String password, Integer status) {
        this.name = name;
        this.profilID = profilID;
        this.phone = phone;
        this.joinedDate = joinedDate;
        this.password = password;
        this.status = status;
    }
    
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) throws Exception {
    
        if (phone == null || phone.length() > 12) {
            throw new Exception("Format du numéro de téléphone invalide : veuillez vérifier.");
        }
        this.phone = phone;
    }

    public void setPassword(String password) throws Exception {
        if (password == null || password.length() < 4) {
            throw new Exception("Le mot de passe est trop court");
        }
        this.password = password;
    }

    public User dtoToEntity() {
        return new User(
            getUserID(), 
            getName(), 
            getProfilID(), 
            getPhone(), 
            getJoinedDate(), 
            getPassword(), 
            getStatus()
        );
    }
}