package gendev.it.serenity.users.application;


import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.users.domain.dto.UserDTO;
import gendev.it.serenity.users.domain.dto.UserResponseDTO;
import gendev.it.serenity.users.infrastructure.entity.Users;
import gendev.it.serenity.users.infrastructure.repository.UserRepo;
import jakarta.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class UserService extends CommonService<Users, UserResponseDTO, String, UserRepo> {

    public UserService(UserRepo repo) {
        super(repo);
    }

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    
    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    
    @Override
    public UserResponseDTO save(UserResponseDTO model) throws Exception {
        // TODO Auto-generated method stub
        Users user = model.dtoToEntity();
        // user.setPassword("1234");
        String hashedPassword = hashPassword("1234");
        user.setPassword(hashedPassword);
        getJpa().save(user);
        return user.entityToDTO();
    }

    public UserResponseDTO login(UserDTO loginDTO) throws Exception {
        Users user =getJpa().findByPhone(loginDTO.getPhone());

        if (user == null) {
            throw new Exception("Utilisateur non trouvé");
        }

        boolean isMatch = verifyPassword(loginDTO.getPassword(), user.getPassword());

        System.out.println(isMatch);

        if (isMatch) {
            return user.entityToDTO();
        } else {
            throw new Exception("Mot de passe incorrect");
        }

        // return user.entityToDTO();
    }

    @Transactional
    public String updatePassword(String userId, String oldPassword, String newPassword) throws Exception {
        Users user = getJpa().findById(userId)
                .orElseThrow(() -> new Exception("Utilisateur non trouvé"));

        if (!verifyPassword(oldPassword, user.getPassword())) {
            throw new Exception("Ancien mot de passe incorrect");
        }

        String hashedNewPassword = hashPassword(newPassword);
        user.setPassword(hashedNewPassword);

        getJpa().save(user);

        return "Mot de passe mis à jour avec succès";
    }

}

