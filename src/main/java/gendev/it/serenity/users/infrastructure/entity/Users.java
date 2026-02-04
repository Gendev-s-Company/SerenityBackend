package gendev.it.serenity.users.infrastructure.entity;

import java.time.LocalDate;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.users.domain.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String userID;

    @Column(nullable = false)
    private String name;

    @JoinColumn(name = "profilid", nullable = false)
    private String profilID;

    @Column(unique = true, length = 12)
    private String phone;

    @Column
    private LocalDate joineddate= LocalDate.now();

    @Column(nullable = false, length = 100)
    private String password;

    protected Users() {}


    public Users(String name, String profilID, String phone, LocalDate joineddate, String password) {
        this.name = name;
        this.profilID = profilID;
        this.phone = phone;
        this.joineddate = joineddate;
        this.password = password;
    }

    @Override
    public UserDTO entityToDTO() {
        return new UserDTO(userID,name,profilID,phone,joineddate,password);
    }

    @Override
    public String getId() {
        return userID;

    }

    @Override
    public void updateFromDTO(DTO udto) {
        UserDTO dto= (UserDTO) udto;
        setName(dto.getName());
        setProfilID(dto.getProfilID());
        setPhone(dto.getPhone());
        setJoineddate(dto.getJoineddate());
        setPassword(dto.getPassword());
        setStatus(dto.getStatus());

    }

}
