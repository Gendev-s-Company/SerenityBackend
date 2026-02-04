package gendev.it.serenity.users.infrastructure.entity;

import java.time.LocalDate;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.users.domain.dto.UserDTO;
import gendev.it.serenity.users.domain.dto.UserResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
public class Users extends BaseEntity<UserResponseDTO> {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String userID;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "profilid", nullable = false)
    private Profil profil;

    @Column(unique = true, length = 12)
    private String phone;

    @Column
    private LocalDate joineddate= LocalDate.now();

    @Column(nullable = false, length = 100)
    private String password;

    protected Users() {}


    public Users(String name, Profil profilID, String phone, LocalDate joineddate, String password) {
        this.name = name;
        this.profil = profilID;
        this.phone = phone;
        this.joineddate = joineddate;
        this.password = password;
    }

    @Override
    public UserResponseDTO entityToDTO() {
        return new UserResponseDTO(userID,name,profil.entityToDTO(),phone,joineddate,getStatus());
    }

    @Override
    public String getId() {
        return userID;

    }

    @Override
    public void updateFromDTO(DTO udto) {
        UserResponseDTO dto= (UserResponseDTO) udto;
        setName(dto.getName());
        try {
            setProfil(dto.getProfil().dtoToEntity());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
        setPhone(dto.getPhone());
        setJoineddate(dto.getJoinedDate());
        setStatus(dto.getStatus());

    }

}
