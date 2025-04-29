package vn.hoangdung.projectJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectJavaApplication.class, args);
	}

}
