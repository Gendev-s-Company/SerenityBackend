package gendev.it.serenity.users.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gendev.it.serenity.users.infrastructure.entity.Profil;


@Repository
public interface ProfilRepo extends JpaRepository<Profil, String>
{

    Profil findByProfilID(String profilID);

}
