package gendev.it.serenity.users.application;

import java.util.List;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.users.domain.dto.ProfilDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import gendev.it.serenity.users.infrastructure.entity.Profil;
import gendev.it.serenity.users.infrastructure.repository.CompanyRepo;
import gendev.it.serenity.users.infrastructure.repository.ProfilRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class ProfilService extends CommonService<Profil,ProfilDTO,String, ProfilRepo> {

    private final ProfilRepo profilRepository;
    private final CompanyRepo companyRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public ProfilService(ProfilRepo profilRepository,CompanyRepo companyRepository) {
        super(profilRepository); 
        this.companyRepository = companyRepository;
        this.profilRepository = profilRepository;
    }

    // Generateur de'ID Profil
    private String generateNextProfilID() {
        Long nextVal = ((Number) entityManager
                .createNativeQuery("SELECT nextval('profil_seq')")
                .getSingleResult())
                .longValue();

        return String.format("PROF%06d", nextVal);
    }


    // Creer un profil
    @Transactional
    public ProfilDTO create(ProfilDTO profildto) {
        Company company = companyRepository
                .findById(profildto.getCompanyid())
                .orElseThrow(() -> new RuntimeException("Company introuvable"));

        Profil profil = profildto.dtoToEntity(company);

        // Assigner l'ID généré avant le save()
        profil.setProfilID(generateNextProfilID());

        Profil saved = profilRepository.save(profil);

        return saved.entityToDTO(); // Retourner le profil avec ID
    }



    // Liste des profils
    public List<ProfilDTO> findAll() {
    return profilRepository.findAll()
            .stream()
            .map(p -> new ProfilDTO(
                    p.getProfilID(),
                    p.getCompany().getCompanyID(),
                    p.getName(),
                    p.getAuthority()
            ))
            .toList();

    }
    
    // Trouver un profil
    public ProfilDTO findOneById(String id) throws Exception{
        try {
            return profilRepository.findByProfilID(id).entityToDTO();
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception( "Profil "+id+" introuvable");
        }
        
    }  


    // Update
    @Transactional
    public ProfilDTO update(String profilID, ProfilDTO profildto) throws Exception {
        Profil profil = profilRepository.findById(profilID)
                .orElseThrow(() -> new Exception("Profil " + profilID + " introuvable"));

        profil.setName(profildto.getName());
        profil.setAuthority(profildto.getAuthority());

        // Modifier company
        if (!profil.getCompany().getCompanyID().equals(profildto.getCompanyid())) {
            Company company = companyRepository.findById(profildto.getCompanyid())
                    .orElseThrow(() -> new Exception("Company introuvable"));
            profil.setCompany(company);
        }
        // Sauvegarde de la companie
        Profil saved = profilRepository.save(profil);
        return saved.entityToDTO();
    }


    // Delete
    @Transactional
    public void delete(String profilID) throws Exception {
        if (!profilRepository.existsById(profilID)) {
            throw new Exception("Profil " + profilID + " introuvable");
        }
        profilRepository.deleteById(profilID);
    }


}
