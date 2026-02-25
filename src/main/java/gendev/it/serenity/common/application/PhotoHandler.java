package gendev.it.serenity.common.application;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.dto.FileDTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.common.utils.FileHandler;

public class PhotoHandler<T extends BaseEntity,D extends DTO<T>> {
    public List<D> ListEntityToListDtof(List<T> list)
            throws Exception{
        List<D> result = new ArrayList<D>();
        for (T row : list) {
            D dto = addFileToDTO(row);
            result.add(dto);
        }
        return result;
    }

    public D addFileToDTO(T entity) throws Exception{
        FileHandler handler = new FileHandler();
        Method getPath =  entity.getClass().getMethod("getPath");
        String path = (String) getPath.invoke(entity);
        FileDTO file = handler.getFile(path);
        D dto = (D) entity.entityToDTO();
        Method setFile = dto.getClass().getMethod("setFiles", FileDTO.class);
        setFile.invoke(dto,file);
        return dto;
    }
}
