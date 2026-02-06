package gendev.it.serenity.users.infrastructure.entity;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company extends BaseEntity<CompanyDTO>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String companyID;
    @Column(nullable = false)
    private String name;
    @Column
    private String phone;
    @Column
    private String mail;


    @Override
    public CompanyDTO entityToDTO() {
        return new CompanyDTO(companyID, name, phone, mail);
    }


    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return companyID;
    }


    @Override
    public void updateFromDTO(DTO cdto) {
        CompanyDTO dto = (CompanyDTO) cdto;
        setMail(dto.getMail());
        setPhone(dto.getPhone());
        setName(dto.getName());
        setStatus(dto.getStatus());
    }
}
