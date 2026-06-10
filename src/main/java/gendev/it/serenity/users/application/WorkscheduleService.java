package gendev.it.serenity.users.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<WorkscheduleDTO> paginatedgetByAuthority(
            String userid,
            int pageNumber,
            int pageSize,
            String field,
            String sort,
            Integer status) throws Exception {
            
        Users user = userService.getJpa().findById(String.valueOf(userid))
                .orElseThrow(() -> new Exception("Utilisateur non trouvé"));
            
        int authority_user = user.getProfil().getAuthority();
        String company = user.getProfil().getCompany().getCompanyID();
            
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
            
        if (authority_user >= 4) {
            return getJpa().findPaginateByStatusAndCompany(state, company, pageable)
                    .map(p -> (WorkscheduleDTO) p.entityToDTO());
        } else {
            return getJpa().paginatedfindByUserIDAndStatusAndCompany(userid, state, company, pageable)
                    .map(p -> (WorkscheduleDTO) p.entityToDTO());
        }
    }
        
    public List<WorkscheduleDTO> getByAuthority(String userid) throws Exception {
        Users user = userService.getJpa().findById(String.valueOf(userid))
                .orElseThrow(() -> new Exception("Utilisateur non trouvé"));


        int authority_user = user.getProfil().getAuthority();
        String company = user.getProfil().getCompany().getCompanyID();
        if (authority_user >= 4) {
            List<Workschedule> list = getJpa().findStatusAndCompany(0, company);
            return super.conversion(list);
        } else {

          return getJpa().findByUserIDAndStatusAndCompany(userid, 0, company)
                    .stream()
                    .map(entity -> (WorkscheduleDTO) entity.entityToDTO())
                    .collect(Collectors.toList());
        }
    
    }

    public List<WorkscheduleDTO> choiceSearch(List<String> userids,String company) throws Exception {
        return getJpa().findByUserIDInAndStatus(userids, 0,company)
                .stream()
                .map(entity -> (WorkscheduleDTO) entity.entityToDTO())
                .toList();
    }


    

}
