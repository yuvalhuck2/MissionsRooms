package missions.room;

import ExternalSystems.VerificationCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("Pack_Try")//let spring to read from repositories
@EntityScan("Pack_Try")// let spring to scan all the entities and crete or update table accordingly
public class RoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomApplication.class, args);
    }

}
