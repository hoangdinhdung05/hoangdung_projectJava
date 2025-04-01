package vn.hoangdung.projectJava.modules.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoangdung.projectJava.modules.users.enities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    

}
