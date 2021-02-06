package missions.room;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;


@SpringBootApplication
@EnableJpaRepositories("CrudRepositories")//let spring to read from repositories
@EntityScan("missions/room/Domain")// let spring to scan all the entities and crete or update table accordingly
public class RoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomApplication.class, args);
        System.out.println("started!!!!!!!!!!!!!!!!!");
    }



}
