package gendev.it.serenity.users.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.users.domain.dto.WorkscheduleDTO;
import gendev.it.serenity.users.infrastructure.entity.Users;
import gendev.it.serenity.users.infrastructure.entity.Workschedule;
import gendev.it.serenity.users.infrastructure.repository.UserRepo;
import gendev.it.serenity.users.infrastructure.repository.WorkscheduleRepo;

@Service
public class WorkscheduleService extends CommonService<Workschedule, WorkscheduleDTO,Integer, WorkscheduleRepo> {

    private final UserRepo usersRepo;

    public WorkscheduleService(WorkscheduleRepo repo,UserRepo userRepo) {
        super(repo);
        this.usersRepo=userRepo;
    }

    
    @Override
    public WorkscheduleDTO save(WorkscheduleDTO dto)throws Exception  {

        // Validation
        dto.validate();

        return super.save(dto);
    }


    @Autowired
    public UserService userService;

    public List<WorkscheduleDTO> getByAuthority(String userid) throws Exception {
        Users user = userService.getJpa().findById(String.valueOf(userid))
                .orElseThrow(() -> new Exception("Utilisateur non trouvé"));


        int authority_user = user.getProfil().getAuthority();

        if (authority_user >= 4) {
            return super.findAll(null);
        } else {

          return getJpa().findByUserIDAndStatus(userid, 0)
                    .stream()
                    .map(entity -> (WorkscheduleDTO) entity.entityToDTO())
                    .collect(Collectors.toList());
        }
    
    }

    public List<WorkscheduleDTO> choiceSearch(List<String> userids) throws Exception {
        return getJpa().findByUserIDInAndStatus(userids, 0)
                .stream()
                .map(entity -> (WorkscheduleDTO) entity.entityToDTO())
                .toList();
    }


    

}
