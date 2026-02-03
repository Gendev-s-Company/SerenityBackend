package gendev.it.serenity.common.infrastructure;

import gendev.it.serenity.common.dto.DTO;

public interface EntityInterface{
    public Object getId();
    void updateFromDTO(DTO dto);
}
