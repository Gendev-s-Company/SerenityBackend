package gendev.it.serenity.common.dto;

import gendev.it.serenity.common.infrastructure.BaseEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class DTO<T extends BaseEntity> {
    private int status;
    public abstract T dtoToEntity();
    public void setStatus(int status) {
        if (status<0) {
            throw new IllegalArgumentException("Le status ne peut pas être négatif");
        }
        this.status = status;
    }
}
