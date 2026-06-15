package gendev.it.serenity.pack.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.pack.infrastructure.models.Pack;
import gendev.it.serenity.pack.infrastructure.models.PackActivity;
import gendev.it.serenity.pack.infrastructure.models.PackHotelDetails;
import gendev.it.serenity.pack.infrastructure.models.PackRestoDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PackDTO extends DTO<Pack> {
    private String packID;
    private String companyID;
    private String title;
    private BigDecimal discount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private List<PackActivity> activityPack;
    private List<PackHotelDetails> hotelsPack;
    private List<PackRestoDetails> restoPack;

    public PackDTO(String packID) {
        this.packID = packID;
    }

    
    public PackDTO(String packID, String companyID, String title, BigDecimal discount, LocalDateTime startDate,
            LocalDateTime endDate) {
        this.packID = packID;
        this.companyID = companyID;
        this.title = title;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Override
    public Pack dtoToEntity() throws Exception {
        Pack pack = new Pack(packID, companyID, title, discount, startDate, endDate);
        startMapping(pack);
        return pack;
    }

    private void startMapping(Pack pack) {
        getActivityPack().forEach(p -> pack.attachActivity(p));
        getHotelsPack().forEach(h -> pack.attachHotel(h));
        getRestoPack().forEach(r -> pack.attachResto(r));
    }
    

}
