package gendev.it.serenity.restaurant.application.tables;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.application.PhotoHandler;
import gendev.it.serenity.common.utils.FileHandler;
import gendev.it.serenity.hotel.domain.dto.ActivityPhotoCreateDTO;
import gendev.it.serenity.hotel.domain.dto.room.RoomPhotoDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomPhoto;
import gendev.it.serenity.restaurant.domain.dto.tables.TablePhotoDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TablePhoto;
import gendev.it.serenity.restaurant.infrastructure.repository.tables.TablePhotoRepo;
import jakarta.transaction.Transactional;

@Service
public class TablePhotoService extends CommonService<TablePhoto, TablePhotoDTO, String, TablePhotoRepo> {
    private PhotoHandler<TablePhoto, TablePhotoDTO> picHandler;

    public TablePhotoService(TablePhotoRepo jpa) {
        super(jpa);
        this.picHandler = new PhotoHandler<TablePhoto, TablePhotoDTO>();
        // TODO Auto-generated constructor stub
    }

    @Transactional
    public String saves(ActivityPhotoCreateDTO model) throws Exception {
        if (model.getUploadFile() != null)
            for (MultipartFile row : model.getUploadFile()) {
                TablePhotoDTO photo = new TablePhotoDTO();
                photo.setSkipValidation(true);
                photo.setTableID(model.getActivityID());
                photo.setStatus(0);
                photo.setUploadFile(row);
                photo = save(photo);
            }
        return "Enregistrement réussi";
    }

    @Override
    public TablePhotoDTO save(TablePhotoDTO model) throws Exception {
        // TODO Auto-generated method stub
        FileHandler handler = new FileHandler();
        String path = handler.createDir(model.getTableID());
        String filename = "";
        if (model.getUploadFile() != null && !model.getUploadFile().isEmpty()) {
            filename = model.getUploadFile().getOriginalFilename();
            try {
                handler.saveFile(path, filename, model.getUploadFile());
            } catch (Exception e) {
                // TODO: handle exception
                throw new Exception("Error: " + e.getMessage());
            }
        }
        model.setPath(path + "/" + filename);
        return super.save(model);
    }

    public List<TablePhotoDTO> findAllByTable(String activityID, Integer state) throws Exception {
        int status = state != null ? state : 0;
        List<TablePhoto> result = getJpa().findByTableIDAndStatus(activityID, status);
        return picHandler.ListEntityToListDtof(result);
    }

    public Page<TablePhotoDTO> paginateAllByTable(int pageNumber, int pageSize, String field, String sort,
            Integer status, String activityID) throws Exception {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findByTableIDAndStatus(activityID, state, pageable)
                .map(p -> {
                    try {
                        return picHandler.addFileToDTO(p);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                });
    }
}
