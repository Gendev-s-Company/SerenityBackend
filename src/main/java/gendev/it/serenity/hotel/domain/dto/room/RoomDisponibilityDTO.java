package gendev.it.serenity.hotel.domain.dto.room;

/**
 * 
 * DTO pour la récupéération de disponibilité global (sur une intervalle de date)
 */
public interface RoomDisponibilityDTO {
    public String getRoomID();
    public String getName();
    public String getDescription();
    public Integer getRoom_state();
    public Integer getReservation_state();
}
