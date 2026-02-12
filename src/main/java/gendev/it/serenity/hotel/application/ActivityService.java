package gendev.it.serenity.hotel.application;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.hotel.domain.dto.ActivityDTO;
import gendev.it.serenity.hotel.infrastructure.entity.Activity;
import gendev.it.serenity.hotel.infrastructure.repository.ActivityRepo;
import org.springframework.data.domain.Page;

@Service
public class ActivityService extends CommonService<Activity, ActivityDTO, String, ActivityRepo> {

    public ActivityService(ActivityRepo jpa) {
        super(jpa);
    }

}
