package gendev.it.serenity.pack.application;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.utils.State;
import gendev.it.serenity.core.exception.BusinessException;
import gendev.it.serenity.pack.dto.PackDTO;
import gendev.it.serenity.pack.infrastructure.models.Pack;
import gendev.it.serenity.pack.infrastructure.repository.PackRepo;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PackService extends CommonService<Pack, PackDTO, String, PackRepo> {

    public PackService(PackRepo jpa) {
        super(jpa);
        // TODO Auto-generated constructor stub
    }

    @Transactional
    public PackDTO updatePack(PackDTO toUpdate, String packID, Boolean isDetail) throws BusinessException {
        Pack pack = findOneByIdAndStatus(packID, State.ACTIVE);
        pack.getActivityPack().clear();
        pack.getHotelsPack().clear();
        pack.getRestoPack().clear();
        pack.setTitle(toUpdate.getTitle());
        pack.setDiscount(toUpdate.getDiscount());
        pack.setStartDate(toUpdate.getStartDate());
        pack.setEndDate(toUpdate.getEndDate());
        if (!isDetail) {
            pack.setStatus(State.DELETED);
        } else {
            toUpdate.publicMapping(pack);
        }
        return pack.entityToDTO();
    }

}
