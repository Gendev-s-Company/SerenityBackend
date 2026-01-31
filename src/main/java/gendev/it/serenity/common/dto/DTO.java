package gendev.it.serenity.common.dto;

import gendev.it.serenity.common.infrastructure.BaseEntity;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DTO {
    private int status;
    public abstract BaseEntity dtoToEntity();
}
