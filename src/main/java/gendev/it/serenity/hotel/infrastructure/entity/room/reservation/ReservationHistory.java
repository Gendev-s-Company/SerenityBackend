package gendev.it.serenity.hotel.infrastructure.entity.room.reservation;

import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.hotel.domain.dto.room.reservation.ReservationHistoryDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "reservationhistory")
public class ReservationHistory extends BaseEntity<ReservationHistoryDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyid;
    @Column
    private String reservationID;
    @Column(name = "datehistory")
    private LocalDateTime dateHistory;
    @Column
    private int state;
public ReservationHistory(Integer historyid, String reservationID, LocalDateTime dateHistory, int state, int status) {
        this.historyid = historyid;
        this.reservationID = reservationID;
        this.dateHistory = dateHistory;
        this.state = state;
        setStatus(status);
    }
    
    


    public ReservationHistory(String reservationID, LocalDateTime dateHistory, int state, int status) {
    this.reservationID = reservationID;
    this.dateHistory = dateHistory;
    this.state = state;
    setStatus(status);
}




    @Override
    public Integer getId() {
        // TODO Auto-generated method stub
        return historyid;
    }
    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }
    @Override
    public ReservationHistoryDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new ReservationHistoryDTO(historyid, reservationID, dateHistory, state, getStatus());
    }
}
