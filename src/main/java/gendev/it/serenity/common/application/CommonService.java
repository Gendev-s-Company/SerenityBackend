package gendev.it.serenity.common.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.common.utils.State;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CommonService<T extends BaseEntity, D extends DTO,ID, JPA extends CommonRepository<T, ID>> {
    private final JPA jpa;

    public CommonService(JPA jpa) {
        this.jpa = jpa;
    }

    public JPA getJpa() {
        return jpa;
    }

    @Transactional
    public D save(D model) throws Exception {
        T res = jpa.save((T) model.dtoToEntity());
        return (D) res.entityToDTO();
    }

    // mitady anle entity par statut par id
    private T findByIdAndStatus(ID id, Integer status) throws Exception {
        int state = status != null ? status : State.ACTIVE;
        return jpa.findAllByStatus(state).stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("ID introuvable ou inactif : " + id));
    }

    @Transactional
    public D update(D model, ID id, Integer status) throws Exception {
        T init = findByIdAndStatus(id, status);
        Object entityID = init.getId();
        if (entityID instanceof String && !entityID.equals(id)) 
            throw new Exception("Modification impossible, ID different");
        if (entityID instanceof Integer && entityID!=id) 
            throw new Exception("Modification impossible, ID different");
        
        init.updateFromDTO(model);
        return (D)jpa.save(init).entityToDTO();
    }

    // mamadika azy ho lasa dto
    public D findById(ID id, Integer status) throws Exception {
        return (D)findByIdAndStatus(id, status).entityToDTO();
    }

    // mamafa azy by update status
    @Transactional
    public void deleteById(ID id, Integer status) throws Exception {
        T model = findByIdAndStatus(id, status);
        model.setStatus(State.DELETED);
        jpa.save(model);
    }

    public List<D> findAll(Integer status) {
        int state = status != null ? status : State.ACTIVE;
        return ListEntityToListDto(jpa.findAllByStatus(state));
    }

    /*
     * Conversion de page entity to page dto
     */
    public Page<DTO> getPaginated(int pageNumber, int pageSize, String field, String sort, Integer status) {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        int state = status != null ? status : State.ACTIVE;
        Page<T> list = jpa.findAllByStatus(state, pageable);

        Page<DTO> result = list.map(entity -> {
            return entity.entityToDTO();
        });
        return result;
    }

    // function de conversion d'une liste de entity vers une liste de dto
    private List<D> ListEntityToListDto(List<T> list) {
        List<D> result = new ArrayList<D>();
        for (T row : list) {
            result.add((D)row.entityToDTO());
        }
        return result;
    }

    // delete maina be
    @Transactional
    public void delete(D model) throws Exception {
        jpa.delete((T) model.dtoToEntity());
    }
}
