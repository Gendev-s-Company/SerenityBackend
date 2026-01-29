package gendev.it.serenity.users.infrastructure.entity;

import java.time.LocalDate;

import gendev.it.serenity.users.domain.dto.UserDTO;
import gendev.it.serenity.users.domain.dto.UserResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Users {
    @Id
    @Column(name = "userid", length = 10, updatable = false, nullable = false)
    private String userID;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "profilid", nullable = false)
    private Profil Profil;

    @Column
    private String phone;

    @Column
    private LocalDate joineddate= LocalDate.now();

    @Column(nullable = false)
    private String password;

    @Column
    private int status;


    protected Users() {}

    public Users(String name,Profil profil, String phone,
            LocalDate joineddate, String password, int status) {
        this.name = name;
        this.Profil = profil;
        this.phone = phone;
        this.joineddate = joineddate;
        this.password = password;
        this.status = status;
    }


    public UserDTO EntityToDTO() {
        return new UserDTO(
            userID,
            name,
            Profil.getProfilID(),
            phone,
            joineddate,
            password,
            status
        );
    }
        public UserResponseDTO EntityResponseToDTO() {
        return new UserResponseDTO(
            userID,
            name,
            Profil.EntityToDTO(),
            phone,
            joineddate,
            status
        );
    }
}
