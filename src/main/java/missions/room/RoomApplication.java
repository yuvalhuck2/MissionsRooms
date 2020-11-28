package missions.room;

import Domain.Room;
import Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("Repositories")//let spring to read from repositories
@EntityScan("Domain")// let spring to scan all the entities and crete or update table accordingly
public class RoomApplication {



    public static void main(String[] args) {
        SpringApplication.run(RoomApplication.class, args);
    }



}
