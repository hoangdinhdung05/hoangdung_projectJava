package vn.hoangdung.projectJava.modules.users.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hoangdung.projectJava.modules.users.entities.UserCatalogue;



@Repository
public interface UserCatalogueRepository extends JpaRepository<UserCatalogue, Long> {
    
    Optional<UserCatalogue> findById(Long id);

}
