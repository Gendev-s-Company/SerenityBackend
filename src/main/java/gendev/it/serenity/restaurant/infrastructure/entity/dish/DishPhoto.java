package gendev.it.serenity.restaurant.infrastructure.entity.dish;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.restaurant.domain.dto.dish.DishPhotoDTO;
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
@Table(name = "dishphoto")
public class DishPhoto extends BaseEntity<DishPhotoDTO>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photoID;
    @Column
    private String dishID;
    @Column
    private String path;

    
    public DishPhoto(Integer photoID, String dishID, String path, int status) {
        this.photoID = photoID;
        this.dishID = dishID;
        this.path = path;
        setStatus(status);
    }
    @Override
    public Object getId() {
        // TODO Auto-generated method stub
        return photoID;
    }
    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        DishPhotoDTO d = (DishPhotoDTO) dto;
        setPath(d.getPath());
        setDishID(d.getDishID());
    }
    @Override
    public DishPhotoDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new DishPhotoDTO(photoID, dishID, path, status);
    }
}
