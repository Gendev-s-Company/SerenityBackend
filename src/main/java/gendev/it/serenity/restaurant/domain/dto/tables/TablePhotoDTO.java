package gendev.it.serenity.restaurant.domain.dto.tables;

import org.springframework.web.multipart.MultipartFile;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.dto.FileDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TablePhoto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TablePhotoDTO extends DTO<TablePhoto> {
    private String photoID;
    private String tableID;
    private String path;
    private FileDTO files;
    private MultipartFile uploadFile;
    
    public TablePhotoDTO(String photoID, String tableID, String path, int status) {
        this.photoID = photoID;
        this.tableID = tableID;
        this.path = path;
        setStatus(status);
    }
    public TablePhotoDTO(String photoID, String tableID, String path, FileDTO files, MultipartFile uploadFile, int status) {
        this.photoID = photoID;
        this.tableID = tableID;
        this.path = path;
        this.files = files;
        this.uploadFile = uploadFile;
        setStatus(status);
    }
     public void setTableID(String tableid) throws Exception {
          if (tableid == null && !isSkipValidation()) {
            throw new Exception("Veuillez remplir le champ table");
        }
        this.tableID = tableid;
    }
    
    
    @Override
    public TablePhoto dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new TablePhoto(photoID, tableID, path, getStatus());
    }
    public void setPhotoID(String photoID) {
        this.photoID = photoID;
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
