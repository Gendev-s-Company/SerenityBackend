package gendev.it.serenity.restaurant.domain.dto.dish;

import org.springframework.web.multipart.MultipartFile;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.dto.FileDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishPhoto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DishPhotoDTO extends DTO<DishPhoto> {

    private Integer photoID;
    private String dishID;
    private String path;
    private FileDTO files;
    private MultipartFile uploadFile;

    public DishPhotoDTO(Integer photoID, String dishID, String path, int status) {
        this.photoID = photoID;
        this.dishID = dishID;
        this.path = path;
        setStatus(status);
    }

    public DishPhotoDTO(Integer photoID, String dishID, String path, FileDTO files, MultipartFile uploadFile,
            int status) {
        this.photoID = photoID;
        this.dishID = dishID;
        this.path = path;
        this.files = files;
        this.uploadFile = uploadFile;
        setStatus(status);
    }

    public void setDishID(String dishID) throws Exception {
        if (dishID == null && !isSkipValidation()) {
            throw new Exception("Veuillez remplir le champ dishID");
        }
        this.dishID = dishID;
    }


    
    @Override
    public DishPhoto dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new DishPhoto(photoID, dishID, path, getStatus());
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFiles(FileDTO files) {
        this.files = files;
    }

    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }

}
