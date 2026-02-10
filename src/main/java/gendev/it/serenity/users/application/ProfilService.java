package gendev.it.serenity.users.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gendev.it.serenity.common.application.CommonService;
import gendev.it.serenity.users.domain.dto.ProfilDTO;
import gendev.it.serenity.users.infrastructure.entity.Profil;
import gendev.it.serenity.users.infrastructure.repository.ProfilRepo;

@Service
public class ProfilService extends CommonService<Profil,ProfilDTO,String, ProfilRepo> {

    public ProfilService(ProfilRepo profilRepository) {
        super(profilRepository); 
    }

    public List<ProfilDTO> findProfilByCurrentCompanyId(String companyId) throws Exception{
        return getJpa().findAllByStatusAndCompany(companyId)
        .stream()
        .map(p -> (ProfilDTO) p.entityToDTO())
        .toList();
    }

    public Page<ProfilDTO> getPaginateByCompany(String companyId,int page,int size,String field,String sort,Integer status) {
        Sort.Direction direction = sort.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));
        int state = status != null ? status : 0; 
        return getJpa().findAllByCompanyAndStatus(companyId, state, pageable)
                .map(p -> (ProfilDTO) p.entityToDTO());
    }
}
