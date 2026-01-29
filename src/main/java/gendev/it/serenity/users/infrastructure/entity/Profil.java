package gendev.it.serenity.users.infrastructure.entity;
import gendev.it.serenity.users.domain.dto.ProfilDTO;
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
public class Profil {

    @Id
    @Column(name = "profilid", length = 30, nullable = false, updatable = false)
    private String profilID;

    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private Company company;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private int authority;

    //OBLIGATOIRE pour JPA
    protected Profil() {}


    //PAS de profilID ici
    public Profil(Company company, String name, int authority) {
        this.company = company;
        this.name = name;
        this.authority = authority;
    }

    public ProfilDTO EntityToDTO() {
        return new ProfilDTO(
            profilID,           
            company.getCompanyID(),
            name,
            authority
        );
    }
}

