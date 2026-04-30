package gendev.it.serenity.restaurant.application.dish;

import java.util.List;

import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.utils.OrderState;
import gendev.it.serenity.common.utils.State;
import gendev.it.serenity.restaurant.domain.dto.dish.DishOrderDetailsDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrder;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrderDetails;
import gendev.it.serenity.restaurant.infrastructure.repository.dish.DishOrderDetailsRepo;
import jakarta.transaction.Transactional;

@Service
public class DishOrderDetailsService
        extends CommonService<DishOrderDetails, DishOrderDetailsDTO, String, DishOrderDetailsRepo> {
    private final DishOrderService service;

    public DishOrderDetailsService(DishOrderDetailsRepo jpa, DishOrderService service) {
        super(jpa);
        this.service = service;
        // TODO Auto-generated constructor stub
    }

    @Transactional
    public void updateState(String id, Integer state) throws Exception {
        DishOrderDetails dishToUpdate = findOneByIdAndStatus(id, State.ACTIVE);
        if (state == null)
            throw new Exception("veuillez indiquer le state");
        dishToUpdate.setState(state);
        getJpa().save(dishToUpdate);
        checkChildsState(dishToUpdate.getOrderID());
    }

    

    @Override
    public DishOrderDetailsDTO save(DishOrderDetailsDTO model) throws Exception {
        // TODO Auto-generated method stub
        DishOrderDetailsDTO dto = super.save(model);
        DishOrder order = service.findOneByIdAndStatus(dto.getOrderID(), 0);
        // mila asina comparaison entre prix avant et vaovao
        order.calculateTotalPrice();
        checkChildsState(dto.getOrderID());
        return dto;
    }
    

    @Override
    public void deleteById(String id, Integer status) throws Exception {
        // TODO Auto-generated method stub
        DishOrderDetails dishToUpdate = findOneByIdAndStatus(id, State.ACTIVE);
        dishToUpdate.setStatus(State.DELETED);
        getJpa().save(dishToUpdate);
        checkChildsState(dishToUpdate.getOrderID());

    }

    public void checkChildsState(String orderId) throws Exception {
        List<DishOrderDetails> list = getJpa().findAllByOrderIDAndStatusOrderByDateOrderAsc(orderId, 0);
        boolean isFinish = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getState() == OrderState.INPROGRESS) {
                isFinish = false;
            }
        }
        service.updateState(orderId, isFinish ? OrderState.FINISH : OrderState.INPROGRESS);
    }

    public List<DishOrderDetails> findAllByOrderID(String order, Integer status){
        int statut = status != null ? status : State.ACTIVE;
        return  getJpa().findAllByOrderIDAndStatusOrderByDateOrderAsc(order, statut);
    }
}
