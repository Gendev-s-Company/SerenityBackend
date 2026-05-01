package gendev.it.serenity.restaurant.domain.dto.tables;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.RestaurantTable;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableType;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TableDTO extends DTO<RestaurantTable>{
    private String tableID;
    private String name;
    private String description;
    private TableTypeDTO tabletype;
    private int capacity;
    public TableDTO(String tableID, String name, String description, TableTypeDTO tabletype, int capacity, int status) {
        this.tableID = tableID;
        this.name = name;
        this.description = description;
        this.tabletype = tabletype;
        this.capacity = capacity;
        setStatus(status);
    }

    public void removeType() {
        this.tabletype = null;
    }

    @Override
    public RestaurantTable dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        TableType type = tabletype != null ? tabletype.dtoToEntity() : null;
        return new RestaurantTable(tableID, name, description, type, capacity, getStatus());
    }
    
    
}
