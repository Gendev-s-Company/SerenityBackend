package gendev.it.serenity.pack.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.pack.infrastructure.models.Pack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PackDTO extends DTO<Pack> {
    private String packID;
    private String companyID;
    private String title;
    private BigDecimal discount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private List<PackActivityDTO> activityPack;
    private List<PackHotelDetailDTO> hotelsPack;
    private List<PackRestoDTO> restoPack;

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

    public void publicMapping(Pack pack) {
        startMapping(pack);
    }

    private void startMapping(Pack pack) {
        if (getActivityPack() != null)
            getActivityPack().forEach(p -> pack.attachActivity(p.dtoToEntityAvoidException()));
        if (getHotelsPack() != null)
            getHotelsPack().forEach(h -> pack.attachHotel(h.dtoToEntityAvoidException()));
        if (getRestoPack() != null)
            getRestoPack().forEach(r -> pack.attachResto(r.dtoToEntityAvoidException()));
    }

    public void setPackID(String packID) {
        this.packID = packID;
    }

    public void setCompanyID(String companyID) throws Exception {
        if (companyID == null || companyID.isBlank())
            throw new Exception("Company est obligatoire");
        this.companyID = companyID;
    }

    public void setTitle(String title) throws Exception {
        if (title == null || title.isBlank())
            throw new Exception("Company est obligatoire");
        this.title = title;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) throws Exception {
        if (startDate == null)
            throw new Exception("Date début obligatoire");
        if (endDate != null && startDate.isAfter(endDate))
            throw new Exception("La date de fin ne doit pas etre anterieur à la date de debut");
        this.endDate = endDate;
    }

    public void setActivityPack(List<PackActivityDTO> activityPack) {
        this.activityPack = activityPack;
    }

    public void setHotelsPack(List<PackHotelDetailDTO> hotelsPack) {
        this.hotelsPack = hotelsPack;
    }

    public void setRestoPack(List<PackRestoDTO> restoPack) {
        this.restoPack = restoPack;
    }

}
