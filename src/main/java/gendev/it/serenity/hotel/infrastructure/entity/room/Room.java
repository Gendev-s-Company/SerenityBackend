package gendev.it.serenity.hotel.infrastructure.entity.room;

import java.io.IOException;
import java.util.List;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.hotel.domain.ClasseUtils.RoomPhotoHandler;
import gendev.it.serenity.hotel.domain.dto.room.RoomDTO;
import gendev.it.serenity.hotel.domain.dto.room.RoomPhotoDTO;
import gendev.it.serenity.hotel.domain.dto.room.RoomTypeDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room extends BaseEntity<RoomDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String roomID;

    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "typeid", nullable = false)
    private RoomType type;

    @Column
    private Integer peoples;
    @Column
    private Integer bed;
    @Column
    private Integer state;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomid", insertable = false, updatable = false)
    private List<RoomPhoto> photos;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomid", insertable = false, updatable = false)
    private List<RoomPrice> roomPrices;

    public Room(String roomID) {
        this.roomID = roomID;
    }

    public Room(String roomID, String name, String description, RoomType type, Integer peoples, Integer bed,
            Integer state, int status) {
        this.roomID = roomID;
        this.name = name;
        this.description = description;
        this.type = type;
        this.peoples = peoples;
        this.bed = bed;
        this.state = state;
        setStatus(status);
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return roomID;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        RoomDTO d = (RoomDTO) dto;
        setBed(d.getBed());
        setPeoples(d.getPeoples());
        setDescription(d.getDescription());
        setName(d.getName());
        setState(d.getState());
        if (d.getType() != null) {
            setType(new RoomType(d.getType().getTypeID()));
        }
    }

    @Override
    public RoomDTO entityToDTO() {
        // TODO Auto-generated method stub
        RoomTypeDTO t = null;
        if (type != null) {
            t = type.entityToDTO();
        }
        RoomDTO dto = new RoomDTO(roomID, name, description, t, peoples, bed, state, status);
        List<RoomPhotoDTO> list;
        try {
            list = new RoomPhotoHandler().ListEntityToListDtof(photos);
            dto.setPhotos(list);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Cannot set photos on room " + roomID);
        }
        try {
            dto.setRoomPrice(dto.findLastPrice(roomPrices));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Cannot set photos on room " + roomID +", cause list of roomprice is null");
        }
        return dto;
    }
}
