package gendev.it.serenity.users.application;


import java.util.List;

import org.springframework.stereotype.Service;

import gendev.it.serenity.users.domain.dto.UserDTO;
import gendev.it.serenity.users.domain.dto.UserResponseDTO;
import gendev.it.serenity.users.infrastructure.entity.Profil;
import gendev.it.serenity.users.infrastructure.entity.Users;
import gendev.it.serenity.users.infrastructure.repository.ProfilRepo;
import gendev.it.serenity.users.infrastructure.repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final ProfilRepo profilRepository;
    private final UserRepo userRepository;
    // private final BCryptPasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    
    public UserService(ProfilRepo profilRepository, UserRepo userRepository) {
        this.profilRepository = profilRepository;
        this.userRepository = userRepository;
    }

    // Generateur de l'ID utilisateur
    private String generateNextUserID() {
        Long nextVal = ((Number) entityManager
                .createNativeQuery("SELECT nextval('users_seq')")
                .getSingleResult())
                .longValue();

        return String.format("USER%06d", nextVal);
    }

    // Creer un utilisateur
    @Transactional
    public Users create(UserDTO userdto){
        Profil profil=profilRepository
        .findById(userdto.getProfil())
        .orElseThrow(() -> new RuntimeException("Profil introuvable"));

        Users user=userdto.dtoToEntity(profil);

        // Assigner l'ID généré avant le save()
        user.setUserID(generateNextUserID());

        return userRepository.save(user);
    }

    // Liste des profils
    public List<UserResponseDTO> findAll() {
    return userRepository.findAll()
            .stream()
            .map(u -> new UserResponseDTO(
                    u.getUserID(),
                    u.getName(),
                    u.getProfil().EntityToDTO(),
                    u.getPhone(),
                    u.getJoineddate(),
                    u.getStatus()
            ))
            .toList();
    }

    // public List<UserDTO> find() {
    // return userRepository.findAll()
    //         .stream()
    //         .map(u -> new UserDTO(
    //                 u.getUserID(),
    //                 u.getName(),
    //                 u.getProfil().EntityToDTO(),
    //                 u.getPhone(),
    //                 u.getJoineddate(),
    //                 u.getStatus()
    //         ))
    //         .toList();
    // }

    // Trouver un utilisateur
    public UserResponseDTO findOneById(String id) throws Exception{
        try {
            return userRepository.findByUserID(id).EntityResponseToDTO();
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception( "Utilisateur "+id+" introuvable");
        }
        
    }
    
    // public UserDTO findOne(String id) throws Exception{
    //     try {
    //         return userRepository.findByUserID(id).EntityToDTO();
    //     } catch (Exception e) {
    //         // TODO: handle exception
    //         throw new Exception( "Utilisateur "+id+" introuvable");
    //     }
        
    // } 


    // Update
    @Transactional
    public UserDTO update(String userID, UserDTO userDTO) throws Exception {
        Users user = userRepository.findById(userID)
                .orElseThrow(() -> new Exception("Utilisateur " + userID + " introuvable"));

        user.setName(userDTO.getName());
        // Modifier profil
        if (!user.getProfil().getProfilID().equals(userDTO.getProfil())) {
            Profil profil = profilRepository.findById(userDTO.getProfil())
                    .orElseThrow(() -> new Exception("Profil introuvable"));
            user.setProfil(profil);
        }

        user.setPhone(userDTO.getPhone());
        user.setJoineddate(userDTO.getJoineddate());
        user.setPassword(userDTO.getPassword());
        user.setStatus(userDTO.getStatus());

        // Sauvegarde de l'utilisateur
        Users saved = userRepository.save(user);
        return saved.EntityToDTO();
    }

    // Delete
    @Transactional
    public void delete(String userID) throws Exception {
        if (!userRepository.existsById(userID)) {
            throw new Exception("Utilisateur " + userID + " introuvable");
        }
        userRepository.deleteById(userID);
    }
  


 
    public UserDTO login(UserDTO loginDTO) throws Exception {
       Users userList = userRepository.findByPhone(loginDTO.getPhone());

    //    if (userList.getPassword().equals(loginDTO.getPassword())) {
    //             return userList.EntityToDTO();
    //     } else {
    //         throw new Exception("Identifiants invalides");
    //     }

        if (userList == null) {
            throw new Exception("Utilisateur non trouvé");
        }else{
            if (userList.getPassword().equals(loginDTO.getPassword())) {
                return userList.EntityToDTO();
            } else {
                throw new Exception("Identifiants invalides");
            }
        }
    }


    @Transactional
    public String updatePassword(String userId, String oldPassword, String newPassword) throws Exception {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Utilisateur non trouvé"));

        // if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
        //     throw new Exception("L'ancien mot de passe est incorrect");
        // }

        // String hashedpwd = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);

        userRepository.save(user);

        return "Mot de passe mis à jour avec succès";
    }
}
