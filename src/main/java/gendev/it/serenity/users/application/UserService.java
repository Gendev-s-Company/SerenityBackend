package gendev.it.serenity.users.application;

import java.util.Optional;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import gendev.it.serenity.users.domain.dto.UserDTO;
import gendev.it.serenity.users.infrastructure.entity.User;
import gendev.it.serenity.users.infrastructure.repository.UserRepo;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepo repo;
    // private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepo repo) {
        this.repo = repo;
    }

 
    public UserDTO login(UserDTO loginDTO) throws Exception {
       User userList = repo.findByPhone(loginDTO.getPhone());

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
        User user = repo.findById(userId)
                .orElseThrow(() -> new Exception("Utilisateur non trouvé"));

        // if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
        //     throw new Exception("L'ancien mot de passe est incorrect");
        // }

        // String hashedpwd = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);

        repo.save(user);

        return "Mot de passe mis à jour avec succès";
    }
}