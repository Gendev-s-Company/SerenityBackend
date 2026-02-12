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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class UserService extends CommonService<Users, UserResponseDTO, String, UserRepo> {

    public UserService(UserRepo repo) {
        super(repo);
    }
    
    @Override
    public UserResponseDTO save(UserResponseDTO model) throws Exception {
        // TODO Auto-generated method stub
        Users user = model.dtoToEntity();
        user.setPassword("1234");
        getJpa().save(user);
        return user.entityToDTO();
    }

    public UserResponseDTO login(UserDTO loginDTO) throws Exception {
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
    public List<UserResponseDTO> findAllByCompany(String company, Integer state){
        int status = state != null ? state : 0;
        List<Users> result = getJpa().findAllByStatusAndCompany(status, company);
        return super.conversion(result);
    }
    public Page<UserResponseDTO> paginateAllByCompany(int pageNumber, int pageSize, String field, String sort, Integer status, String company){
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0; 
        return getJpa().findPaginateByStatusAndCompany(state, company, pageable)
                .map(p -> (UserResponseDTO) p.entityToDTO());
    }

}

