package gendev.it.serenity.restaurant.infrastructure.entity.tables;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.restaurant.domain.dto.tables.TableDTO;
import gendev.it.serenity.restaurant.domain.dto.tables.TableTypeDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant_table")
public class RestaurantTable extends BaseEntity<TableDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String tableID;
    @Column
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "tabletypeid", nullable = false)
    private TableType tabletype;
    @Column
    private int capacity;
    
    public RestaurantTable(String tableID, String name, String description, TableType tabletype, int capacity, int status) {
        this.tableID = tableID;
        this.name = name;
        this.description = description;
        this.tabletype = tabletype;
        this.capacity = capacity;
        setStatus(status);
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return tableID;
    }
    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        TableDTO d = (TableDTO) dto;
        setCapacity(d.getCapacity());
        setDescription(d.getDescription());
        setName(d.getName());
        TableType type = new TableType();
        type.setTabletypeid(d.getTabletype().getTabletypeid());
        setTabletype(type);
    }
    @Override
    public TableDTO entityToDTO() {
        // TODO Auto-generated method stub
        TableTypeDTO type = tabletype != null ? tabletype.entityToDTO() : null;
        return new TableDTO(tableID, name, description, type, capacity, status);
    }
}
