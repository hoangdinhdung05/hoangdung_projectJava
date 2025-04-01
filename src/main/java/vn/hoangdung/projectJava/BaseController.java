package vn.hoangdung.projectJava;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api")
public class BaseController {
    
    private JdbcTemplate jdbcTemplate;

    public BaseController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("test")
    public String test() {
        String sql = "CREATE TABLE IF NOT EXISTS `test_table` ("
            + "`id` INT(11) NOT NULL AUTO_INCREMENT,"
            + "`name` VARCHAR(255) NOT NULL,"
            + "PRIMARY KEY (`id`)"
            + ")";
    
        jdbcTemplate.execute(sql);
    
        return "test";
    }
    

}
