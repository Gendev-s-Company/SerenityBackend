package gendev.it.serenity.users.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.users.infrastructure.entity.Users;



@Repository
public interface UserRepo extends JpaRepository<Users, String> {

   Users findByUserID(String userID);

}
