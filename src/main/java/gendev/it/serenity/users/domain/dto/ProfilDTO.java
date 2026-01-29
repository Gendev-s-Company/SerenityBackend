package gendev.it.serenity.users.domain.dto;
import gendev.it.serenity.users.infrastructure.entity.Company;
import gendev.it.serenity.users.infrastructure.entity.Profil;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class ProfilDTO {

    private String profilID;  
    private String companyid;
    private String name;
    private int authority;

    //Constructeur pour CREATE (entrée client)
    public ProfilDTO(String companyid, String name, int authority) {
        this.companyid = companyid;
        this.name = name;
        this.authority = authority;
    }

    //Constructeur pour READ (sortie API)
    public ProfilDTO(String profilID, String companyid, String name, int authority) {
        this.profilID = profilID;
        this.companyid = companyid;
        this.name = name;
        this.authority = authority;
    }

    // getters
    public String getProfilID() { return profilID; }
    public String getCompanyid() { return companyid; }
    public String getName() { return name; }
    public int getAuthority() { return authority; }

    // setters autorisés (input)
    public void setCompanyid(String companyid) throws Exception {
        if (companyid.isBlank())  {
            throw new Exception("Veuillez entrer le nom d'une companie valide ");
        }
        this.companyid = companyid;
    }

    public void setName(String name) throws Exception {
        if (name.isBlank()) {
            throw new Exception("Veuillez entrer un nom valide");
        }
        this.name = name;
    }

    public void setAuthority(int authority)throws Exception {
        if (authority<0) {
            throw new Exception("Veuillez mettre une autorité supérieur ou égal à 0");
        }
        this.authority=authority;
    }


    public Profil dtoToEntity(Company company) {
        return new Profil(company, name, authority);
    }
}
