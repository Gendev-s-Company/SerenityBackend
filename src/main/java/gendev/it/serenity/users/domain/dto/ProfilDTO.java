package gendev.it.serenity.users.domain.dto;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import gendev.it.serenity.users.infrastructure.entity.Profil;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProfilDTO extends DTO<Profil> {

    private String profilID;
    private CompanyDTO company;
    private String name;
    private int authority;

    public ProfilDTO(CompanyDTO companyid, String name, int authority, int status) {
        this.company = companyid;
        this.name = name;
        this.authority = authority;
        this.setStatus(status);
    }

    public ProfilDTO(String profilID, CompanyDTO companyid, String name, int authority) {
        this.profilID = profilID;
        this.company = companyid;
        this.name = name;
        this.authority = authority;
    }

    public ProfilDTO(String profilID, CompanyDTO companyid, String name, int authority, int status) {
        this.profilID = profilID;
        this.company = companyid;
        this.name = name;
        this.authority = authority;
        this.setStatus(status);
    }

       // setters autorisés (input)
    public void setCompany(CompanyDTO company) throws Exception {
        if (company == null) {
            throw new Exception("Veuillez entrer le nom d'une companie valide ");
        }
        company.setSkipValidation(true);
        this.company = company;
    }

 
    public void setName(String name) throws Exception {
        if (name.isBlank()) {
            throw new Exception("Veuillez entrer un nom valide");
        }
        this.name = name;
    }

    public void setAuthority(int authority) throws Exception {
        if (authority < 0) {
            throw new Exception("Veuillez mettre une autorité supérieur ou égal à 0");
        }
        this.authority = authority;
    }

    @Override
    public Profil dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new Profil(profilID, company.dtoToEntity(), name, authority);
    }

}
