package gendev.it.serenity.pack.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.pack.dto.PackRestoDTO;
import gendev.it.serenity.restaurant.domain.dto.dish.DishDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.Dish;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "packrestodetails")
public class PackRestoDetails extends BaseEntity<PackRestoDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packid", nullable = false)
    @JsonManagedReference
    private Pack pack;

    @Column(name = "dishid")
    private String dishID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dishid", insertable = false, updatable = false)
    private Dish dish;

    @Column
    private Integer quantity;

    
    public PackRestoDetails(String dishID, Integer quantity) {
        this.dishID = dishID;
        this.quantity = quantity;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }

    @Override
    public PackRestoDTO entityToDTO() {
        // TODO Auto-generated method stub
        DishDTO dto = dish != null ? dish.entityToDTO() : null;
        return new PackRestoDTO(id, quantity, dto);
    }
}
