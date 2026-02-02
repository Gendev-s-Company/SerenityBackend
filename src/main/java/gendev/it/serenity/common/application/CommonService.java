package gendev.it.serenity.common.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.common.repo.CommonRepository;
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

    // mitady anle entity active par id
    private T findActiveByID(ID id) throws Exception {
        return jpa.findAllByStatus(0).stream()
                .filter(entity -> entity.getId().equals(id) && entity.getStatus() == 0)
                .findFirst()
                .orElseThrow(() -> new Exception("ID introuvable ou inactif : " + id));
    }

    @Transactional
    public D update(D model, ID id) throws Exception {
        T init = findActiveByID(id);
        if (init.getId() instanceof String && !init.getId().equals(id)) 
            throw new Exception("Modification impossible");
        
        init.updateFromDTO(model);
        return (D)jpa.save(init).entityToDTO();
    }

    // mamadika azy ho lasa dto
    public D findById(ID id) throws Exception {
        return (D)findActiveByID(id).entityToDTO();
    }

    // mamafa azy by update status
    @Transactional
    public void deleteById(ID id) throws Exception {
        T model = findActiveByID(id);
        model.setStatus(1);
        jpa.save(model);
    }

    public List<D> findAll() {
        return ListEntityToListDto(jpa.findAllByStatus(0));
    }

    /*
     * Conversion de page entity to page dto
     */
    public Page<DTO> getPaginated(int pageNumber, int pageSize, String field, String sort) {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        Page<T> list = jpa.findAllByStatus(0, pageable);

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
