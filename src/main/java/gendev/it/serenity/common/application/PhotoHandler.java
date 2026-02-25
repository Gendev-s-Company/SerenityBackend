package gendev.it.serenity.common.application;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.dto.FileDTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.common.utils.FileHandler;

public class PhotoHandler<T extends BaseEntity,D extends DTO<T>> {
    /*
        function miboucle list ana entity, dia aveo maemataka liste ana sary dans chaque entity
    */
    public List<D> ListEntityToListDtof(List<T> list)
            throws Exception{
        List<D> result = new ArrayList<D>();
        for (T row : list) {
            D dto = addFileToDTO(row);
            result.add(dto);
        }
        return result;
    }
    // function maka anle fichier, dia aveo apetany ao anaty dto ana sary le izy
    /*
        -Mila misy an'ito function ito ao amin'i entity: String getPath()
        -Mila misy an'ito indray ao amin'i DTO: setFiles(FileDTO file)
    */
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
