package gendev.it.serenity.users.application;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.users.domain.dto.WorkscheduleDTO;
import gendev.it.serenity.users.infrastructure.entity.Workschedule;
import gendev.it.serenity.users.infrastructure.repository.WorkscheduleRepo;

@Service
public class WorkscheduleService extends CommonService<Workschedule, WorkscheduleDTO,Integer, WorkscheduleRepo> {

    public WorkscheduleService(WorkscheduleRepo repo) {
        super(repo);
        //TODO Auto-generated constructor stub
    }
    @Override
    public WorkscheduleDTO save(WorkscheduleDTO dto)throws Exception  {

        // Validation
        dto.validate();
        return super.save(dto);
    }

}
