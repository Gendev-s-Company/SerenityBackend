package gendev.it.serenity.users.domain.dto;

import java.time.LocalDate;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.users.infrastructure.entity.Profil;
import gendev.it.serenity.users.infrastructure.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserResponseDTO extends DTO<Users> {
    private String userID=null;
    private String name;
    private Profil profil; 
    private String phone;
    private LocalDate joinedDate = LocalDate.now();

 

    public UserResponseDTO(String userID, String name, Profil profil, String phone, LocalDate joinedDate,
            Integer status) {
        this.userID = userID;
        this.name = name;
        this.profil = profil;
        this.phone = phone;
        this.joinedDate = joinedDate; 
        setStatus(status);
    }
    




    // ----- Setters (optionnel, selon besoin) -----
  

    public void setName(String name) throws Exception {
        if (name == null || name.isBlank()) {
            throw new Exception("Veuillez entrer un nom valide");
        }
        this.name = name;
    }

    public void setProfilID(Profil profilID) throws Exception {
        if (profilID == null) {
            throw new Exception("Veuillez choisir un profil existant");
        }
        this.profil = profilID;
    }

    public void setPhone(String phone) throws Exception {
        if (phone != null && phone.length() > 12) {
            throw new Exception("Format du numéro de téléphone invalide");
        }
        this.phone = phone;
    }





    @Override
    public Users dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new Users(userID, name, profil, phone, joinedDate, getStatus());
    }
    
}

