package gendev.it.serenity.common.infrastructure;

import gendev.it.serenity.common.dto.DTO;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity<D extends DTO> implements EntityInterface {
    @Column
    public int status;
    public abstract D entityToDTO();
}
