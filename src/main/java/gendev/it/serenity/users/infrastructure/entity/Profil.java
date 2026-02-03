package gendev.it.serenity.users.infrastructure.entity;
import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import gendev.it.serenity.users.domain.dto.ProfilDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.core.ObjectReadContext.Base;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Profil extends BaseEntity {

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


    @Override
    public ProfilDTO entityToDTO() {
        return new ProfilDTO(
            profilID,           
            company.getCompanyID(),
            name,
            authority
        );
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return profilID;
    }


    @Override
    public void updateFromDTO(DTO cdto) {
        ProfilDTO dto = (ProfilDTO) cdto;
        // setCompany(dto.getCompanyid());
        setName(dto.getName());
        setAuthority(dto.getAuthority());

    }

}

