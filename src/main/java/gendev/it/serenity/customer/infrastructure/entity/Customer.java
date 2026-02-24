package gendev.it.serenity.customer.infrastructure.entity;

import gendev.it.serenity.common.dto.DTO;
import gendev.it.serenity.common.infrastructure.BaseEntity;
import gendev.it.serenity.customer.domain.dto.CustomerDTO;
import gendev.it.serenity.users.domain.dto.CompanyDTO;
import gendev.it.serenity.users.infrastructure.entity.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends BaseEntity<CustomerDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String customerID;
    @Column(nullable = false)
    private String name;
    @Column
    private String phone;
    @Column
    private String mail;
    @ManyToOne
    @JoinColumn(name = "companyid", nullable = true)
    private Company company;
    @Column
    private String cin;
    @Column
    private String address;

    public Customer(String customerID) {
        this.customerID = customerID;
    }

    public Customer(String customerID, String name, String phone, String mail, Company company, String cin,
            String address, int status) {
        this.customerID = customerID;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.company = company;
        this.cin = cin;
        this.address = address;
        setStatus(status);
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
        setCin(dto.getCin());
        setAddress(dto.getAddress());
    }

    @Override
    public CustomerDTO entityToDTO() {
        // TODO Auto-generated method stub
        CompanyDTO dto = null;
        if (company!=null) {
            dto = company.entityToDTO();
        }
        return new CustomerDTO(customerID, name, phone, mail, dto, cin, address, status);
    }

}
