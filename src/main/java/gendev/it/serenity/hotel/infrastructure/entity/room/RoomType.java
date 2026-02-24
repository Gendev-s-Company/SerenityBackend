package gendev.it.serenity.hotel.infrastructure.entity.room;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.hotel.domain.dto.room.RoomTypeDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roomtype")
public class RoomType extends BaseEntity<RoomTypeDTO>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String typeID;
    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private Company company;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;


    
    public RoomType(String typeID) {
        this.typeID = typeID;
    }
    public RoomType(String typeID, Company company, String name, String description, int status) {
        this.typeID = typeID;
        this.company = company;
        this.name = name;
        this.description = description;
        setStatus(status);
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return typeID;
    }
    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        RoomTypeDTO d = (RoomTypeDTO) dto;
        setDescription(d.getDescription());
        setName(d.getName());
    }
    @Override
    public RoomTypeDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new RoomTypeDTO(typeID, company.entityToDTO(), name, description, getStatus());
    }
}
