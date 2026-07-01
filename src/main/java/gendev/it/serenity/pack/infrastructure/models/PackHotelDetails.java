package gendev.it.serenity.pack.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.hotel.domain.dto.room.RoomDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.Room;
import gendev.it.serenity.pack.dto.PackHotelDetailDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "packhoteldetails")
public class PackHotelDetails extends BaseEntity<PackHotelDetailDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packid", nullable = false)
    @JsonManagedReference
    private Pack pack;

    @Column(name = "roomid")
    private String roomID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomid", insertable = false, updatable = false)
    private Room room;

    @Column
    private Integer duration;

    public PackHotelDetails(String roomID, Integer duration) {
        this.roomID = roomID;
        this.duration = duration;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }

    @Override
    public PackHotelDetailDTO entityToDTO() {
        // TODO Auto-generated method stub
        RoomDTO dto = room != null ? room.entityToDTO() : null;
        if(dto!=null)
            dto.getType().resetCompany();
            dto.setPhotos(null);
        return new PackHotelDetailDTO(id, duration, dto);
    }
}
