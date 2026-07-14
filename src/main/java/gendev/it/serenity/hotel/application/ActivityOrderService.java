package gendev.it.serenity.hotel.application;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.core.models.QInvoiceModel;
import gendev.it.serenity.hotel.domain.dto.ActivityOrderDTO;
import gendev.it.serenity.hotel.domain.dto.ActivityPriceDTO;
import gendev.it.serenity.hotel.infrastructure.entity.ActivityOrder;
import gendev.it.serenity.hotel.infrastructure.repository.ActivityOrderRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class ActivityOrderService extends CommonService<ActivityOrder, ActivityOrderDTO, String, ActivityOrderRepo> {
    private final ActivityPriceService priceService;
    private final EntityManager entity;
    private final ApplicationEventPublisher eventPublisher;
    
    public ActivityOrderService(ActivityOrderRepo jpa, ActivityPriceService priceService, EntityManager entity, ApplicationEventPublisher eventPublisher) {
        super(jpa);
        this.priceService = priceService;
        this.entity = entity;
        // TODO Auto-generated constructor stub
        this.eventPublisher = eventPublisher;
    }

    public Page<ActivityOrderDTO> paginateAllByCompanyAndState(int pageNumber, int pageSize, String field, String sort,
            Integer status, String company, int state) throws Exception {
        // throw new Exception("Veuillez implémenter la function paginateAllByCompany");
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int states = status != null ? status : 0;

        if (state == -1) {
            return getJpa().findPaginateByStatusAndCompany(states, company, pageable)
                    .map(p -> (ActivityOrderDTO) p.entityToDTO());
        }
        return getJpa().findPaginateByStatusAndCompanyAndState(states, company, state, pageable)
                .map(p -> (ActivityOrderDTO) p.entityToDTO());
    }

    private String createCustomerQuery(String customer) {
        return customer == null ? "" : " and a.customerID='" + customer + "' ";
    }

    private String createPriceQuery(String min, String max) {
        if (min != null && max != null) {
            return " and a.price between " + min + " and " + max + " ";
        } else if (max == null && min != null) {
            return " and a.price >=" + min + " ";
        } else if (min == null && max != null) {
            return " and a.price <=" + max + " ";
        }
        return "";
    }

    private String createDateQuery(String start, String end) {
        if (start != null && end != null) {
            return " and a.dateorder between '" + start + "' and '" + end + "' ";
        } else if (end == null && start != null) {
            return " and a.dateorder >='" + start + "' ";
        } else if (start == null && end != null) {
            return " and a.dateorder <='" + end + "' ";
        }
        return "";
    }

    private String createStatusQuery(int state) {
        return state == -1 ? "" : " and a.state = " + state + " ";
    }

    // recherche avancer
    public Page<ActivityOrderDTO> launchSearch(int pageNumber, int pageSize, String field, String sort,
            Integer status, String company, int state, String customer, String minprice, String maxprice,
            String start, String end) throws Exception {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int states = status != null ? status : 0;
        String sql_query = "SELECT * FROM activityorder a ";
        String whereClause = "where a.status = " + states
                + " and a.activityID in (select activityID from activity ab where ab.companyID='" + company
                + "') ";
        whereClause += createStatusQuery(state);
        whereClause += createCustomerQuery(customer);
        whereClause += createPriceQuery(minprice, maxprice);
        whereClause += createDateQuery(start, end);
        // requete finale
        sql_query += whereClause;
        // count ligne
        // System.out.println(sql_query);
        String sqlCount = "SELECT COUNT(*) FROM activityorder a " + whereClause;
        Query countQuery = entity.createNativeQuery(sqlCount);

        Query query = entity.createNativeQuery(sql_query, ActivityOrder.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<ActivityOrder> resultList = query.getResultList();
        long totalRows = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(resultList, pageable, totalRows)
                .map(p -> withTotalPrice(p));
        // return getJpa().findPaginateByStatusAndCompanyAndState(states, company,
        // state, pageable)
        // .map(p -> (ActivityOrderDTO) p.entityToDTO());
    }

    private ActivityOrderDTO withTotalPrice(ActivityOrder p) {
        ActivityOrderDTO res = p.entityToDTO();
        try {
            ActivityPriceDTO lastPrice = priceService.findLastPrice(res.getActivity().getActivityID(), null);
            res.setTotalPrice(res.getTotalPrice().divide(new BigDecimal(lastPrice.getHourPrice())));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    @Override
    public ActivityOrderDTO save(ActivityOrderDTO model) throws Exception {
        // TODO Auto-generated method stub
        ActivityPriceDTO price = priceService.findLastPrice(model.getActivity().getActivityID(), 0);
        model.setPrice(price.getPrice());
        ActivityOrderDTO res = super.save(model);

        // QInvoiceModel invoice = new QInvoiceModel(res.getActivity().getName(), res.getActivity().getActivityID(), 2,
        //         res.getPrice());
        // eventPublisher.publishEvent(invoice);
        return res;
    }

    public List<ActivityOrderDTO> findAllByActivity(String activityID, Integer state) throws Exception {
        int status = state != null ? state : 0;
        List<ActivityOrder> result = getJpa().findAllBActivity(activityID, status);
        return super.conversion(result);
    }

    public Page<ActivityOrderDTO> paginateAllByACtivity(int pageNumber, int pageSize, String field, String sort,
            Integer status, String activityID) throws Exception {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findAllBActivity(activityID, state, pageable)
                .map(p -> (ActivityOrderDTO) p.entityToDTO());
    }

    public List<ActivityOrderDTO> findAllByCustomer(String customer, Integer state) throws Exception {
        // throw new Exception("Veuillez implémenter la function findAllByCompany");
        int status = state != null ? state : 0;
        List<ActivityOrder> result = getJpa().findAllByCustomer(customer, status);
        return super.conversion(result);
    }

    public Page<ActivityOrderDTO> paginateAllByCustomer(int pageNumber, int pageSize, String field, String sort,
            Integer status, String customer) throws Exception {
        // throw new Exception("Veuillez implémenter la function paginateAllByCompany");
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : 0;
        return getJpa().findAllByCustomer(customer, state, pageable)
                .map(p -> (ActivityOrderDTO) p.entityToDTO());
    }

}
