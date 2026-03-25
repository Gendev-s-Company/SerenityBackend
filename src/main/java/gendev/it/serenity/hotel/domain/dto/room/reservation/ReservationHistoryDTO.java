package gendev.it.serenity.hotel.domain.dto.room.reservation;

import java.time.LocalDateTime;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.reservation.ReservationHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationHistoryDTO extends DTO<ReservationHistory> {
    private Integer historyid;
    private String reservationID;
    private LocalDateTime dateHistory;
    private int state;
    
    public ReservationHistoryDTO(Integer historyid, String reservationID, LocalDateTime dateHistory, int state, int status) {
        this.historyid = historyid;
        this.reservationID = reservationID;
        this.dateHistory = dateHistory;
        this.state = state;
        setStatus(status);
    }

    @Override
    public ReservationHistory dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new ReservationHistory(historyid, reservationID, dateHistory, state, getStatus());
    }
    
}
