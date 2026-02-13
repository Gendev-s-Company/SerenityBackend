package gendev.it.serenity.customer.infrastructure.entity;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.customer.domain.dto.CustomerDTO;
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
public class Customer extends BaseEntity<CustomerDTO>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String customerID;
    @Column(nullable = false)
    private String name;
    @Column
    private String phone;
    @Column
    private String mail;
    
    public Customer(String customerID) {
        this.customerID = customerID;
    }
    public Customer(String customerID, String name, String phone, String mail, int status) {
        this.customerID = customerID;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        setStatus(status);
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return customerID;
    }
    @Override
    public void updateFromDTO(DTO cdto) {
        // TODO Auto-generated method stub
        CustomerDTO dto = (CustomerDTO) cdto;
        setMail(dto.getMail());
        setName(dto.getName());
        setPhone(dto.getPhone());
    }
    @Override
    public CustomerDTO entityToDTO() {
        // TODO Auto-generated method stub
        return new CustomerDTO(customerID, name, phone, mail, status);
    }

}
