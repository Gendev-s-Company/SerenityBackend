package gendev.it.serenity.users.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.common.repo.CommonRepository;
import gendev.it.serenity.users.infrastructure.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserRepo extends CommonRepository<Users, String> {

    Users findByUserID(String userID);

    Users findByPhone(String phone);

    @Query("SELECT u FROM Users u WHERE u.status = :status AND u.profil.company.id = :company")
    List<Users> findAllByCompany(String company, int status);

    @Query("SELECT u FROM Users u WHERE u.status = :status AND u.profil.company.id = :company")
    Page<Users> paginateAllByStatusAndCompany(String company,int status, Pageable page);
    // User findByPhoneAndPassword(String phone, String password);

    // User findByName(String name);

    // User findByProfilID(String profilID);

    // User findByStatus(Integer status);

    // User findByJoinedDate(String joinedDate);

    // User findByPassword(String password);

    // User findByNameAndPassword(String name, String password);
}
