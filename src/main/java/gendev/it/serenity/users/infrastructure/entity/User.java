package gendev.it.serenity.users.infrastructure.entity;

import gendev.it.serenity.users.domain.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "userid", length = 10)
    private String userID;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "profilid", nullable = false, length = 10)
    private String profilID;

    @Column(unique = true, length = 12)
    private String phone;

    @Column(name = "joineddate")
    private LocalDate joinedDate;

    @Column(nullable = false, length = 100)
    private String password;

    @Column
    private Integer status;

    public UserDTO EntityToDTO() {
        return new UserDTO(userID, name, profilID, phone, joinedDate, status);
    }
}