package gendev.it.serenity.restaurant.infrastructure.entity.tables;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.hotel.domain.dto.room.RoomPhotoDTO;
import gendev.it.serenity.restaurant.domain.dto.tables.TablePhotoDTO;
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
@Table(name = "tablephoto")
public class TablePhoto extends BaseEntity<TablePhotoDTO>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String photoID;
    @Column
    private String tableID;
    @Column
    private String path;
    
    public TablePhoto(String photoID, String tableID, String path, int status) {
        this.photoID = photoID;
        this.tableID = tableID;
        this.path = path;
        setStatus(status);
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return photoID;
    }
    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        TablePhotoDTO d = (TablePhotoDTO) dto;
        setTableID(d.getTableID());
        setPath(d.getPath());
    }
    @Override
    public TablePhotoDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new TablePhotoDTO(photoID, tableID, path, status);
    }
}
