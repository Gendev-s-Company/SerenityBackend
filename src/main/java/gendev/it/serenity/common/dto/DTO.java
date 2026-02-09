package gendev.it.serenity.common.dto;

import gendev.it.serenity.common.infrastructure.BaseEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor
public  class DTO<T extends BaseEntity> {
    private int status;
    public T dtoToEntity() throws Exception{
        throw new Exception("Vous devez créé une fonction dans votre classe fille");
    }
    public void setStatus(Integer state) {
        this.status = state!=null ? state : 0;
        if (status<0) {
            throw new IllegalArgumentException("Le status ne peut pas être négatif");
        }
    }
}
