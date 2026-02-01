package gendev.it.serenity.common.infrastructure;

import gendev.it.serenity.common.dto.DTO;

public interface EntityInterface{
    public String getId();
    void updateFromDTO(DTO dto);
}
