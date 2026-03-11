package gendev.it.serenity.hotel.domain.dto.room.reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.customer.domain.dto.CustomerDTO;
import gendev.it.serenity.customer.infrastructure.entity.Customer;
import gendev.it.serenity.hotel.domain.dto.room.RoomDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.reservation.Reservation;
import gendev.it.serenity.users.domain.dto.UserResponseDTO;
import gendev.it.serenity.users.infrastructure.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO extends DTO<Reservation>{
    private String reservationID;
    private String roomID;
    private LocalDateTime  starttime;
    private LocalDateTime endtime;
    private String customerID;
    private BigDecimal price;
    private Float accountRated;
    private BigDecimal accountPaid;
    private LocalDateTime AccountPaimentDeadline;
    private String userID;
    private Integer state;
    private RoomDTO room;
    private UserResponseDTO user;
    private CustomerDTO customer;
    
    public ReservationDTO(String reservationID, String roomID, LocalDateTime starttime, LocalDateTime endtime,
            String customerID, BigDecimal price, Float accountRated, BigDecimal accountPaid,
            LocalDateTime accountPaimentDeadline, String userID, Integer state, RoomDTO room, UserResponseDTO user,
            CustomerDTO customer, Integer status) {
        this.reservationID = reservationID;
        this.roomID = roomID;
        this.starttime = starttime;
        this.endtime = endtime;
        this.customerID = customerID;
        this.price = price;
        this.accountRated = accountRated;
        this.accountPaid = accountPaid;
        this.AccountPaimentDeadline = accountPaimentDeadline;
        this.userID = userID;
        this.state = state;
        this.room = room;
        this.user = user;
        this.customer = customer;
        setStatus(status);
    }

    @Override
    public Reservation dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new Reservation(reservationID, roomID, starttime, endtime, customerID, price, 
            accountRated, accountPaid, AccountPaimentDeadline, userID, state, getStatus());
    }
    
}