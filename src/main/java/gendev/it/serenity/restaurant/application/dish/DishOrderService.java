package gendev.it.serenity.restaurant.application.dish;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.common.utils.State;
import gendev.it.serenity.core.exception.BusinessException;
import gendev.it.serenity.core.models.InvoiceModel;
import gendev.it.serenity.core.models.QInvoiceModel;
import gendev.it.serenity.restaurant.application.tables.TOccupationService;
import gendev.it.serenity.restaurant.domain.dto.dish.DishOrderDTO;
import gendev.it.serenity.restaurant.domain.dto.dish.DishOrderDetailsDTO;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrder;
import gendev.it.serenity.restaurant.infrastructure.entity.dish.DishOrderDetails;
import gendev.it.serenity.restaurant.infrastructure.entity.tables.TableOccupation;
import gendev.it.serenity.restaurant.infrastructure.repository.dish.DishOrderRepo;
import jakarta.transaction.Transactional;

@Service
public class DishOrderService extends CommonService<DishOrder, DishOrderDTO, String, DishOrderRepo> {

    private final TOccupationService tableService;
    private final ApplicationEventPublisher eventPublisher;

    public DishOrderService(DishOrderRepo jpa, TOccupationService tableService,
            ApplicationEventPublisher eventPublisher) {
        super(jpa);
        this.tableService = tableService;
        // TODO Auto-generated constructor stub
        this.eventPublisher = eventPublisher;
    }

    public DishOrderDTO findOneByTableAndDate(String tableid, LocalDateTime start,
            LocalDateTime end, Integer state) throws Exception {
        DishOrder order = getJpa().findOneByTableAndDate(tableid, start, end, state)
                .orElseThrow(() -> new Exception("Cette table n'a pas encore de réservation"));
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

        DishOrder saved = getJpa().save(order);
        String company = getJpa().findCompany(saved.getOrderID());
        List<QInvoiceModel> invoices = saved.getDetails()
                .stream()
                .map(m -> new QInvoiceModel(m.getDish().getName(),
                        m.getDish().getDishID(), m.getQuantity(), m.getUnitPrice()))
                .collect(Collectors.toList());
        // order.getTableOccupation().getCustomer().getCompany().getCompanyID()
        InvoiceModel invoiceModel = new InvoiceModel(saved.getTableOccupation().getCustomerID(), company, invoices,
                null);
        eventPublisher.publishEvent(invoiceModel);

        return saved.entityToDTO();
    }

    @Override
    @Transactional
    public DishOrderDTO update(DishOrderDTO model, String id, Integer status) throws Exception {
        // TODO Auto-generated method stub
        DishOrder entity = super.findOneByIdAndStatus(id, status);
        if (!entity.getOrderID().equals(id)) {
            throw new Exception("Modification impossible, ID different");
        }
        validateOrderDetails(entity, model.getDetails());
        String company = getJpa().findCompany(entity.getOrderID());
        List<QInvoiceModel> invoices = model.getDetails()
                .stream()
                .map(m -> new QInvoiceModel(m.getDish().getName(),
                        m.getDish().getDishID(), m.getQuantity(), m.getUnitPrice()))
                .collect(Collectors.toList());

        InvoiceModel invoiceModel = new InvoiceModel(entity.getTableOccupation().getCustomerID(), company, invoices,
                null);
        eventPublisher.publishEvent(invoiceModel);

        return entity.entityToDTO();
    }

    private void validateOrderDetails(DishOrder order, List<DishOrderDetailsDTO> details) {
        Set<String> savedDish = order.getDetails()
                .stream()
                .map(DishOrderDetails::getDishID)
                .collect(Collectors.toSet());

        details.forEach(dish -> {
            if (!savedDish.contains(dish.getDish().getDishID())) {
                try {
                    order.addDetail(dish.dtoToEntity());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                DishOrderDetails existingDish;
                try {
                    existingDish = order.getDetails().stream()
                            .filter(b -> b.getDishID().equals(dish.getDish().getDishID()))
                            .findFirst()
                            .orElseThrow(() -> new BusinessException(
                                    "Erreur interne inconnu lors de la modification de commande",
                                    HttpStatus.INTERNAL_SERVER_ERROR));
                    if ((existingDish.getQuantity() <= 0) && dish.getQuantity() <= 0)
                        throw new BusinessException(
                                "Erreur: la quantity de ce plat est deja 0 " + existingDish.getDishID(),
                                HttpStatus.NOT_ACCEPTABLE);
                    existingDish.setQuantity(dish.getQuantity());
                    // existingDish.setQuantity(existingDish.getQuantity() + dish.getQuantity());
                    existingDish.setUnitPrice(dish.getUnitPrice());

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
        order.calculateTotalPrice();
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
                .oneEntityToDTO();
    }

    public List<DishOrderDTO> findAllByCompanyAndState(String company, Integer state, List<Integer> states)
            throws Exception {
        // throw new Exception("Veuillez implémenter la function findAllByCompany");
        int status = state != null ? state : 0;
        if (states == null || states.size() == 0) {
            states = new ArrayList<>();
            states = List.of(State.ACTIVE, State.DELETED);
        }
        List<DishOrder> result = getJpa().findAllByStatusAndCompanyAndState(status, company, states);
        return this.conversion(result);
    }

    @Override
    public List<DishOrderDTO> conversion(List<DishOrder> list) {
        // TODO Auto-generated method stub
        List<DishOrderDTO> result = new ArrayList<DishOrderDTO>();
        for (DishOrder row : list) {
            result.add(row.oneEntityToDTO());
        }
        return result;
    }

    public Page<DishOrderDTO> findAllByCompanyAndState(int pageNumber, int pageSize, String field, String sort,
            Integer status, String company, List<Integer> states) throws Exception {
        // throw new Exception("Veuillez implémenter la function paginateAllByCompany");
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        if (states == null || states.size() == 0) {
            states = new ArrayList<>();
            states = List.of(State.ACTIVE, State.DELETED);
        }
        return getJpa().findAllByStatusAndCompanyAndState(state, company, states, pageable)
                .map(p -> (DishOrderDTO) p.entityToDTO());
    }

    public Page<DishOrderDTO> findAllOccupationAndState(int pageNumber, int pageSize, String field, String sort,
            Integer status, String idOccupation, int state) throws Exception {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        return getJpa().findAllByOccupation(status, idOccupation, state, pageable)
                .map(p -> (DishOrderDTO) p.entityToDTO());
    }
}
