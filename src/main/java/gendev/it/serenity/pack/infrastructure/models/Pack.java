package gendev.it.serenity.pack.infrastructure.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.pack.dto.PackActivityDTO;
import gendev.it.serenity.pack.dto.PackDTO;
import gendev.it.serenity.pack.dto.PackHotelDetailDTO;
import gendev.it.serenity.pack.dto.PackRestoDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Pack extends BaseEntity<PackDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String packID;
    @Column(name = "companyid")
    private String companyID;
    @Column
    private String title;
    @Column
    private BigDecimal discount;
    @Column(name = "startdate")
    private LocalDateTime startDate;
    @Column(name = "enddate")
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("activityPack")
    private List<PackActivity> activityPack = new ArrayList<>();
    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("hotelsPack")
    private List<PackHotelDetails> hotelsPack = new ArrayList<>();
    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("restoPack")
    private List<PackRestoDetails> restoPack = new ArrayList<>();

    public Pack(String packID) {
        this.packID = packID;
    }

    public Pack(String packID, String companyID, String title, BigDecimal discount, LocalDateTime startDate,
            LocalDateTime endDate) {
        this.packID = packID;
        this.companyID = companyID;
        this.title = title;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public Object getId() {
        // TODO Auto-generated method stub
        return packID;
    }

    @Override
    public void updateFromDTO(DTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFromDTO'");
    }

    @Override
    public PackDTO entityToDTO() {
        // TODO Auto-generated method stub
        PackDTO dto = new PackDTO(packID, companyID, title, discount, startDate, endDate);
        filterInformation(dto);
        return dto;
    }

    private void filterInformation(PackDTO dto) {
        dto.setActivityPack(getActivityPack()
                .stream().map(PackActivity::entityToDTO).toList());
        dto.setHotelsPack(getHotelsPack().stream()
                .map(PackHotelDetails::entityToDTO).toList());
        dto.setRestoPack(getRestoPack().stream()
                .map(PackRestoDetails::entityToDTO).toList());
    }

    public void attachActivity(PackActivity activity) {
        activity.setPack(this);
        this.activityPack.add(activity);
    }

    public void attachHotel(PackHotelDetails hotel) {
        hotel.setPack(this);
        this.hotelsPack.add(hotel);
    }

    public void attachResto(PackRestoDetails resto) {
        resto.setPack(this);
        this.restoPack.add(resto);
    }
}
