package gendev.it.serenity.pack.dto;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.pack.infrastructure.models.PackRestoDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PackRestoDTO extends DTO<PackRestoDetails> {
    private Integer id;
    private String dishID;
    private Integer quantity;
    public PackRestoDTO(String dishID, Integer quantity) {
        this.dishID = dishID;
        this.quantity = quantity;
    }
    @Override
    public PackRestoDetails dtoToEntity() throws Exception {
        // TODO Auto-generated method stub
        return new PackRestoDetails(dishID, quantity);
    }

    public PackRestoDetails dtoToEntityAvoidException(){
        return new PackRestoDetails(dishID, quantity);
    }
    
}
