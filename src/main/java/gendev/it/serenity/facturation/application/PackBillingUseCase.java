package gendev.it.serenity.facturation.application;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gendev.it.serenity.facturation.dto.BillingDDetailsDTO;
import gendev.it.serenity.facturation.dto.BillingDTO;
import gendev.it.serenity.facturation.dto.BillingQDetailsDTO;
import gendev.it.serenity.pack.dto.PackActivityDTO;
import gendev.it.serenity.pack.dto.PackDTO;
import gendev.it.serenity.pack.dto.PackHotelDetailDTO;
import gendev.it.serenity.pack.dto.PackRestoDTO;

/**
 * PackBillingUseCase
 * classe qui verifie si une facture correspond à un pack
 */
@Service
public class PackBillingUseCase {

    public boolean matchesPack(BillingDTO dto, PackDTO pack) {
        if (!isWithinPackPeriod(dto, pack)) {
            return false;
        }

        List<BillingDDetailsDTO> hotelDetails = safeStream(dto.getDurationsDetails())
                .filter(d -> d.getServiceCode() != null && d.getServiceCode().contains("ROOM"))
                .collect(Collectors.toList());

        List<BillingDDetailsDTO> activityDetails = safeStream(dto.getDurationsDetails())
                .filter(d -> d.getServiceCode() != null && d.getServiceCode().contains("ACT"))
                .collect(Collectors.toList());

        List<BillingQDetailsDTO> restoDetails = dto.getQuantityDetails() != null
                ? dto.getQuantityDetails()
                : Collections.emptyList();

        return checkHotelPack(pack.getHotelsPack(), hotelDetails)
                && checkActivityPack(pack.getActivityPack(), activityDetails)
                && checkRestoPack(pack.getRestoPack(), restoDetails);
    }

    // --- Période ---

    private boolean isWithinPackPeriod(BillingDTO dto, PackDTO pack) {
        LocalDateTime packStart = pack.getStartDate();
        LocalDateTime packEnd = pack.getEndDate();

        boolean durationsOk = safeStream(dto.getDurationsDetails())
                .allMatch(d -> !d.getStartTime().isBefore(packStart) && !d.getEndTime().isAfter(packEnd));

        boolean billingDateOk = dto.getBillingDate() != null
                && !dto.getBillingDate().isBefore(packStart)
                && !dto.getBillingDate().isAfter(packEnd);

        return durationsOk && billingDateOk;
    }

    // --- Hôtel ---

    private boolean checkHotelPack(List<PackHotelDetailDTO> packHotels, List<BillingDDetailsDTO> hotelDetails) {
        if (packHotels == null || packHotels.isEmpty()) {
            return true;
        }
        return packHotels.stream().allMatch(packHotel -> {
            long totalHours = hotelDetails.stream()
                    .filter(d -> matchesRoom(d, packHotel))
                    .mapToLong(d -> Duration.between(d.getStartTime(), d.getEndTime()).toHours())
                    .sum();
            return totalHours >= packHotel.getDuration(); // adapter l'unité (heures/jours) selon votre modèle
        });
    }

    private boolean matchesRoom(BillingDDetailsDTO detail, PackHotelDetailDTO packHotel) {
        return packHotel.getRoom() != null
                && detail.getServiceName().equalsIgnoreCase(packHotel.getRoom().getName());
    }

    // --- Activité ---

    private boolean checkActivityPack(List<PackActivityDTO> packActivities, List<BillingDDetailsDTO> activityDetails) {
        if (packActivities == null || packActivities.isEmpty()) {
            return true;
        }
        return packActivities.stream().allMatch(packActivity -> {
            long totalHours = activityDetails.stream()
                    .filter(d -> matchesActivity(d, packActivity))
                    .mapToLong(d -> Duration.between(d.getStartTime(), d.getEndTime()).toHours())
                    .sum();
            return totalHours >= packActivity.getDuration();
        });
    }

    private boolean matchesActivity(BillingDDetailsDTO detail, PackActivityDTO packActivity) {
        return packActivity.getActivity() != null
                && detail.getServiceName().equalsIgnoreCase(packActivity.getActivity().getName()); // ⚠️ à confirmer
    }

    // --- Restaurant ---

    private boolean checkRestoPack(List<PackRestoDTO> packRestos, List<BillingQDetailsDTO> restoDetails) {
        if (packRestos == null || packRestos.isEmpty()) {
            return true;
        }
        return packRestos.stream().allMatch(packResto -> {
            long totalQuantity = restoDetails.stream()
                    .filter(d -> matchesDish(d, packResto))
                    .mapToLong(BillingQDetailsDTO::getQuantity)
                    .sum();
            return totalQuantity >= packResto.getQuantity();
        });
    }

    private boolean matchesDish(BillingQDetailsDTO detail, PackRestoDTO packResto) {
        return packResto.getDish() != null
                && detail.getServiceName().equalsIgnoreCase(packResto.getDish().getName()); // ⚠️ à confirmer
    }

    private <T> java.util.stream.Stream<T> safeStream(List<T> list) {
        return list == null ? java.util.stream.Stream.empty() : list.stream();
    }

}