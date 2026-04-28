package gendev.it.serenity.restaurant.application.dish;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.utils.State;
import gendev.it.serenity.restaurant.application.tables.TOccupationService;
import gendev.it.serenity.restaurant.domain.dto.dish.DishOrderDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrder;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrderDetails;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableOccupation;
import gendev.it.serenity.restaurant.infrastructure.repository.dish.DishOrderRepo;
import jakarta.transaction.Transactional;

@Service
public class DishOrderService extends CommonService<DishOrder, DishOrderDTO, String, DishOrderRepo> {

    private final TOccupationService tableService;

    public DishOrderService(DishOrderRepo jpa, TOccupationService tableService) {
        super(jpa);
        this.tableService = tableService;
        // TODO Auto-generated constructor stub
    }

    public DishOrderDTO findOneByTableAndDate(String tableid, LocalDateTime start,
            LocalDateTime end, Integer state) throws Exception {
        DishOrder order = getJpa().findOneByTableAndDate(tableid, start, end, state).orElseThrow(() -> new Exception("Cette table n'a pas encore de réservation"));
        return order.oneEntityToDTO();
    }

    @Override
    @Transactional
    public DishOrderDTO save(DishOrderDTO model) throws Exception {
        // TODO Auto-generated method stub
        DishOrder order = model.dtoToEntity();

        if (order.getTableOccupation() != null && order.getTableOccupation().getOccupationID() == null) {
            order.getTableOccupation().setUserID(getUserIdFromOrderDetail(order.getDetails()));
            LocalDateTime now = LocalDateTime.now();
            // mi-set state hoe occuper sa reservation raha vao libre le table nofidiana
            if (now.isBefore(order.getTableOccupation().getStarttime().plusMinutes(30))) {
                order.getTableOccupation().setState(2);
            } else {
                order.getTableOccupation().setState(3);
            }
            TableOccupation occupation = tableService.getJpa().save(order.getTableOccupation());
            order.setTableOccupation(occupation);
        } else if (order.getTableOccupation() != null && order.getTableOccupation().getOccupationID() != null) {
            Optional<DishOrder> exist = getJpa().findAllByTableOccupation(order.getTableOccupation().getOccupationID(),
                    0);
            if (exist.isPresent()) {
                throw new Exception("Cette table possède déjà une commande, modifier plutot la commande");
            }
        }

        return getJpa().save(order).entityToDTO();
    }

    private String getUserIdFromOrderDetail(List<DishOrderDetails> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser().getUserID() != null) {
                return list.get(i).getUser().getUserID();
            }
        }
        return null;
    }

    public DishOrderDTO findOneById(String id, Integer status) throws Exception {
        DishOrder order = findOneByIdAndStatus(id, status);

        // TODO Auto-generated method stub
        return order.oneEntityToDTO();
    }

    public void updateState(String id, Integer state) throws Exception {
        DishOrder dishToUpdate = findOneByIdAndStatus(id, State.ACTIVE);
        if (state == null)
            throw new Exception("veuillez indiquer le state");
        dishToUpdate.setState(state);
        getJpa().save(dishToUpdate);
    }

    // récupération de commande par tableOccupation
    public DishOrderDTO findByTableOccupation(String occupationID) throws Exception {
        return getJpa().findAllByTableOccupation(occupationID, 0)
                .orElseThrow(() -> new Exception("Commande introuvable"))
                .entityToDTO();
    }
}
