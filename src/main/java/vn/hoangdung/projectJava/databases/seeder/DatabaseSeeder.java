package vn.hoangdung.projectJava.databases.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String ... args) throws Exception {

        if(isTableEmpty()) {

            String passwordEncoded = passwordEncoder.encode("password");
            entityManager.createNativeQuery("INSERT INTO users (name, email, password, user_catalogue_id, phone) VALUES (?, ?, ?, ?, ?)")
                    .setParameter(1, "Hoàng Đình Dũng")
                    .setParameter(2, "hoangdinhdung0205@gmail.com")
                    .setParameter(3, passwordEncoded)
                    .setParameter(4, 1)
                    .setParameter(5, "0354870745")
                    .executeUpdate();
            System.out.println("password: " + passwordEncoded);
        }
        

    }

    private boolean isTableEmpty() {
        Long count = (Long) entityManager.createQuery("select count(id) from User").getSingleResult();
        return count == 0;
    }

}
