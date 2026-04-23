package gendev.it.serenity.restaurant.application.dish;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.application.PhotoHandler;
import gendev.it.serenity.hotel.domain.dto.ActivityPhotoCreateDTO;
import gendev.it.serenity.restaurant.domain.dto.dish.DishPhotoDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishPhoto;
import gendev.it.serenity.restaurant.infrastructure.repository.dish.DishPhotoRepo;
import jakarta.transaction.Transactional;
import gendev.it.serenity.common.utils.FileHandler;

@Service
public class DishPhotoService extends CommonService<DishPhoto, DishPhotoDTO, Integer, DishPhotoRepo> {
    private PhotoHandler<DishPhoto, DishPhotoDTO> picHandler;

    public DishPhotoService(DishPhotoRepo jpa) {
        super(jpa);
        // TODO Auto-generated constructor stub
        this.picHandler = new PhotoHandler<DishPhoto, DishPhotoDTO>();
    }

    @Transactional
    public String saves(ActivityPhotoCreateDTO model) throws Exception {
        if (model.getUploadFile() != null)
            for (MultipartFile row : model.getUploadFile()) {
                DishPhotoDTO photo = new DishPhotoDTO();
                photo.setSkipValidation(true);
                photo.setDishID(model.getActivityID());
                photo.setStatus(0);
                photo.setUploadFile(row);
                photo = save(photo);
            }
        return "Enregistrement réussi";
    }

    @Override
    public DishPhotoDTO save(DishPhotoDTO model) throws Exception {
        // TODO Auto-generated method stub
        FileHandler handler = new FileHandler();
        String path = handler.createDir(model.getDishID());
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

    public List<DishPhotoDTO> findAllByDish(String activityID, Integer state) throws Exception {
        int status = state != null ? state : 0;
        List<DishPhoto> result = getJpa().findByDishIDAndStatus(activityID, status);
        return picHandler.ListEntityToListDtof(result);
    }

    public Page<DishPhotoDTO> paginateAllByDish(int pageNumber, int pageSize, String field, String sort,
            Integer status, String activityID) throws Exception {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findByDishIDAndStatus(activityID, state, pageable)
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
