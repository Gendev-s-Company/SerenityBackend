package gendev.it.serenity.restaurant.infrastructure.entity.tables;

import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.customer.domain.dto.CustomerDTO;
import gendev.it.serenity.customer.infrastructure.entity.Customer;
import gendev.it.serenity.restaurant.domain.dto.tables.TOccupationDTO;
import gendev.it.serenity.restaurant.domain.dto.tables.TableDTO;
import gendev.it.serenity.users.domain.dto.UserResponseDTO;
import gendev.it.serenity.users.infrastructure.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "table_occupation")
public class TableOccupation extends BaseEntity<TOccupationDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String occupationID;
    @Column
    private String tableID;
    @Column
    private String customerID;
    @Column
    private String userID;
    @Column
    private LocalDateTime starttime;
    @Column
    private LocalDateTime endtime;
    @Column
    private int state;
    @ManyToOne
    @JoinColumn(name = "tableID", insertable  = false, updatable = false)
    private RestaurantTable table;
    @ManyToOne
    @JoinColumn(name = "userID", insertable  = false, updatable = false)
    private Users user;
    @ManyToOne
    @JoinColumn(name = "customerID", insertable = false, updatable = false)
    private Customer customer;
    public TableOccupation(String occupationID, String tableID, String customerID, String userID, LocalDateTime starttime,
            LocalDateTime endtime, int state, int status) {
        this.occupationID = occupationID;
        this.tableID = tableID;
        this.customerID = customerID;
        this.userID = userID;
        this.starttime = starttime;
        this.endtime = endtime;
        this.state = state;
        setStatus(status);
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return occupationID;
    }
    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        TOccupationDTO o = (TOccupationDTO) dto;
        setCustomerID(o.getCustomerID());
        setUserID(o.getUserID());
        setTableID(o.getTableID());
        setEndtime(o.getEndtime());
        setStarttime(o.getStarttime());
    }
    @Override
    public TOccupationDTO entityToDTO() {
        // TODO Auto-generated method stub
        UserResponseDTO ruser = user!=null ? user.entityToDTO() : null;
        CustomerDTO rcust = customer != null ? customer.entityToDTO() : null;
        TableDTO t = table != null ? table.entityToDTO() : null;
        return new TOccupationDTO(occupationID, starttime, endtime, tableID, customerID, userID, state, t, ruser, rcust);
    }
    
}
