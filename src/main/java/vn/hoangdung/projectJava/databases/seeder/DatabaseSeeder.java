package vn.hoangdung.projectJava.databases.seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.hoangdung.projectJava.modules.users.enities.User;
import vn.hoangdung.projectJava.modules.users.repositories.UserRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void run(String ... args) throws Exception {

        if(isTableEmpty()) {

            String passwordEncoded = passwordEncoder.encode("password");
            // entityManager.createNativeQuery("INSERT INTO users (name, email, password, user_catalogue_id, phone) VALUES (?, ?, ?, ?, ?)")
            //         .setParameter(1, "Hoàng Đình Dũng")
            //         .setParameter(2, "hoangdinhdung0205@gmail.com")
            //         .setParameter(3, passwordEncoded)
            //         .setParameter(4, 1)
            //         .setParameter(5, "0354870745")
            //         .executeUpdate();
            // System.out.println("password: " + passwordEncoded);

            //Cách 2: Dùng entity thêm data
            // User newUser = new User();
            // newUser.setName("Hoàng Đình Dũng");
            // newUser.setEmail("hoangdinhdung0205@gmail.com");
            // newUser.setPassword(passwordEncoded);
            // newUser.setUserCatalogueId(1L);
            // newUser.setPhone("0354870745");
            // entityManager.persist(newUser);

            //Cách 3: Dùng repo thêm data
            User newUser = new User(1L, "Hoàng Đình Dũng", "hoangdinhdung0205@gmail.com", passwordEncoded, "0354870745");
            this.userRepository.save(newUser);
            logger.info("Inserted new user: " + newUser);
        }
        

    }

    private boolean isTableEmpty() {
        Long count = (Long) entityManager.createQuery("select count(id) from User").getSingleResult();
        return count == 0;
    }

}
