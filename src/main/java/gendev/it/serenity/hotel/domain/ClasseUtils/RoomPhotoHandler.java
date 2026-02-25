package gendev.it.serenity.hotel.domain.ClasseUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gendev.it.serenity.common.dto.FileDTO;
import gendev.it.serenity.common.utils.FileHandler;
import gendev.it.serenity.hotel.domain.dto.room.RoomPhotoDTO;
import gendev.it.serenity.hotel.infrastructure.entity.room.RoomPhoto;

public class RoomPhotoHandler {
    public List<RoomPhotoDTO> ListEntityToListDtof(List<RoomPhoto> list) throws IOException {
        List<RoomPhotoDTO> result = new ArrayList<RoomPhotoDTO>();
        for (RoomPhoto row : list) {
            RoomPhotoDTO dto = addFileToDTO(row);
            result.add(dto);
        }
        return result;
    }
    public RoomPhotoDTO addFileToDTO(RoomPhoto entity) throws IOException {
        FileHandler handler = new FileHandler();
        FileDTO file = handler.getFile(entity.getPath());
        RoomPhotoDTO dto = (RoomPhotoDTO) entity.entityToDTO();
        dto.setFiles(file);
        return dto;
    }
}
