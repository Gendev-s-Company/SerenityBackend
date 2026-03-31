package gendev.it.serenity.restaurant.application.tables;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.restaurant.domain.dto.tables.TableDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.RestaurantTable;
import gendev.it.serenity.restaurant.infrastructure.repository.tables.TableRepo;

@Service
public class TableService extends CommonService<RestaurantTable, TableDTO, String, TableRepo>{

    public TableService(TableRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

}
