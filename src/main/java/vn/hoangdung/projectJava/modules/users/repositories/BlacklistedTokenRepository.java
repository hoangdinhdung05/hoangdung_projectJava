package vn.hoangdung.projectJava.modules.users.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hoangdung.projectJava.modules.users.entities.BlacklistedToken;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

    boolean existsByToken(String token);
    int deleteByExpiryDateBefore(LocalDateTime expiryDate);

}
