package gendev.it.serenity.restaurant.application.dish;


import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.utils.State;
import gendev.it.serenity.restaurant.domain.dto.dish.DishOrderDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrder;
import gendev.it.serenity.restaurant.infrastructure.repository.dish.DishOrderRepo;

@Service
public class DishOrderService extends CommonService<DishOrder, DishOrderDTO, String, DishOrderRepo>{

    public DishOrderService(DishOrderRepo jpa) {
        super(jpa);
        //TODO Auto-generated constructor stub
    }

    
    public DishOrderDTO findOneById(String id, Integer status) throws Exception {
        DishOrder order = findOneByIdAndStatus(id, status);
        
        // TODO Auto-generated method stub
        return order.oneEntityToDTO();
    }


    public void updateState(String id, Integer state) throws Exception{
        DishOrder dishToUpdate = findOneByIdAndStatus(id, State.ACTIVE);
        if (state == null)
            throw new Exception("veuillez indiquer le state");
        dishToUpdate.setState(state);
        getJpa().save(dishToUpdate);
    }
    // récupération de commande par tableOccupation
    public DishOrderDTO findByTableOccupation(String occupationID) throws Exception{
        return getJpa().findAllByTableOccupation(occupationID, 0)
        .orElseThrow(() -> new Exception("Commande introuvable"))
        .entityToDTO();        
    }
}
