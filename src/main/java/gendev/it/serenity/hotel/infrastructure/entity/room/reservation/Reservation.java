package gendev.it.serenity.hotel.infrastructure.entity.room.reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.customer.infrastructure.entity.Customer;
import gendev.it.serenity.hotel.domain.dto.room.reservation.ReservationDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.Room;
import gendev.it.serenity.users.infrastructure.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation extends BaseEntity<ReservationDTO>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String reservationID;
    @Column
    private String roomID;
     @Column(nullable = false)
    LocalDateTime  starttime;
    @Column
    LocalDateTime endtime;
    @Column
    private String customerID;
    @Column
    private BigDecimal price;
    @Column(name = "accountrated")
    private Float accountRated;
    @Column(name = "accountpaid")
    private BigDecimal accountPaid;
    @Column(name = "accountpaimentdeadline")
    private String AccountPaimentDeadline;
    @Column
    private String userID;
    @Column
    private Integer state;
    @ManyToOne
    @JoinColumn(name = "roomID", insertable  = false, updatable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name = "userID", insertable  = false, updatable = false)
    private Users user;
    @ManyToOne
    @JoinColumn(name = "customerID", insertable = false, updatable = false)
    private Customer customer;
    
    public Reservation(String reservationID, String roomID, LocalDateTime starttime, LocalDateTime endtime,
            String customerID, BigDecimal price, Float accountRated, BigDecimal accountPaid,
            String accountPaimentDeadline, String userID, Integer state, Integer status) {
        this.reservationID = reservationID;
        this.roomID = roomID;
        this.starttime = starttime;
        this.endtime = endtime;
        this.customerID = customerID;
        this.price = price;
        this.accountRated = accountRated;
        this.accountPaid = accountPaid;
        AccountPaimentDeadline = accountPaimentDeadline;
        this.userID = userID;
        this.state = state;
        setStatus(status);
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return reservationID;
    }
    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        ReservationDTO c = (ReservationDTO) dto;
        setAccountPaid(c.getAccountPaid());
        setAccountPaimentDeadline(c.getAccountPaimentDeadline());
        setAccountRated(c.getAccountRated());
        setCustomerID(c.getCustomerID());
        setEndtime(c.getEndtime());
        setStarttime(c.getStarttime());
        setState(c.getState());
        setPrice(c.getPrice());
    }
    @Override
    public ReservationDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new ReservationDTO(reservationID, roomID, starttime, endtime, customerID, price, accountRated, 
            accountPaid, AccountPaimentDeadline, userID, state, room.entityToDTO(), user.entityToDTO(), customer.entityToDTO(), getStatus());
    }
}
