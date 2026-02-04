package gendev.it.serenity.users.application;


import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.users.domain.dto.UserDTO;
import gendev.it.serenity.users.infrastructure.entity.Users;
import gendev.it.serenity.users.infrastructure.repository.UserRepo;
import jakarta.transaction.Transactional;


@Service
public class UserService extends CommonService<Users, UserDTO, String, UserRepo> {

    public UserService(UserRepo repo) {
        super(repo);
    }

    public UserDTO login(UserDTO loginDTO) throws Exception {
        Users user =getJpa().findByPhone(loginDTO.getPhone());

        if (user == null) {
            throw new Exception("Utilisateur non trouvé");
        }

        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new Exception("Identifiants invalides");
        }

        return user.entityToDTO();
    }

    @Transactional
    public String updatePassword(String userId, String oldPassword, String newPassword) throws Exception {
        Users user = getJpa().findById(userId)
                .orElseThrow(() -> new Exception("Utilisateur non trouvé"));

        user.setPassword(newPassword);
        getJpa().save(user);

        return "Mot de passe mis à jour avec succès";
    }
}

