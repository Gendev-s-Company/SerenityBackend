package gendev.it.serenity.restaurant.domain.dto.tables;

import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.customer.domain.dto.CustomerDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableOccupation;
import gendev.it.serenity.users.domain.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TOccupationDTO extends DTO<TableOccupation>{
    private String occupationID;
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private String tableID;
    private String customerID;
    private String userID;
    private Integer state;

    private TableDTO table;
    private UserResponseDTO user;
    private CustomerDTO customer;
    public TOccupationDTO(String occupationID, LocalDateTime starttime, LocalDateTime endtime, String tableID,
            String customerID, String userID, Integer state) {
        this.occupationID = occupationID;
        this.starttime = starttime;
        this.endtime = endtime;
        this.tableID = tableID;
        this.customerID = customerID;
        this.userID = userID;
        this.state = state;
    }
    @Override
    public TableOccupation dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new TableOccupation(occupationID, tableID, customerID, userID, starttime, endtime, getStatus(), getStatus());
    }

    
    
}
