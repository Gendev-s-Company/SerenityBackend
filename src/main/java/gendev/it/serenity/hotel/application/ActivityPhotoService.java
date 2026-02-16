package gendev.it.serenity.hotel.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.dto.FileDTO;
import gendev.it.serenity.common.utils.FileHandler;
import gendev.it.serenity.hotel.domain.dto.ActivityPhotoDTO;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityPhoto;
import gendev.it.serenity.hotel.infrastructure.repository.ActivityPhotoRepo;

@Service
public class ActivityPhotoService extends CommonService<ActivityPhoto, ActivityPhotoDTO, String, ActivityPhotoRepo> {

    public ActivityPhotoService(ActivityPhotoRepo jpa) {
        super(jpa);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ActivityPhotoDTO save(ActivityPhotoDTO model) throws Exception {
        // TODO Auto-generated method stub
        FileHandler handler = new FileHandler();
        String path = handler.createDir(model.getActivity().getActivityID());
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

    private List<ActivityPhotoDTO> ListEntityToListDtof(List<ActivityPhoto> list) throws IOException {
        List<ActivityPhotoDTO> result = new ArrayList<ActivityPhotoDTO>();
        for (ActivityPhoto row : list) {
            ActivityPhotoDTO dto = addFileToDTO(row);
            result.add(dto);
        }
        return result;
    }

    private ActivityPhotoDTO addFileToDTO(ActivityPhoto entity) throws IOException {
        FileHandler handler = new FileHandler();
        FileDTO file = handler.getFile(entity.getPath());
        ActivityPhotoDTO dto = (ActivityPhotoDTO) entity.entityToDTO();
        dto.setFiles(file);
        return dto;
    }

    public List<ActivityPhotoDTO> findAllByActivity(String activityID, Integer state) throws Exception {
        int status = state != null ? state : 0;
        List<ActivityPhoto> result = getJpa().findAllBActivity(activityID, status);
        return ListEntityToListDtof(result);
    }

    public Page<ActivityPhotoDTO> paginateAllByACtivity(int pageNumber, int pageSize, String field, String sort,
            Integer status, String activityID) throws Exception {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findAllBActivity(activityID, state, pageable)
                .map(p -> {
                    try {
                        return addFileToDTO(p);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                });
    }
}
