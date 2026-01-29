package gendev.it.serenity.users.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.users.infrastructure.entity.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, String> {

    Users findByUserID(String userID);

    Users findByPhone(String phone);

    // User findByPhoneAndPassword(String phone, String password);

    // User findByName(String name);

    // User findByProfilID(String profilID);

    // User findByStatus(Integer status);

    // User findByJoinedDate(String joinedDate);

    // User findByPassword(String password);

    // User findByNameAndPassword(String name, String password);
}
