import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"za.co.global.domain", "za.co.global.persistence", "za.co.global.controllers", "za.co.global.services"})
@EntityScan("za.co.global.domain")
@EnableJpaRepositories(basePackages = {"za.co.global.persistence"})
public class GirsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GirsaApplication.class, args);
	}

}
