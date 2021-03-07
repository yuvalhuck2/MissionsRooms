package missions.room;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("CrudRepositories")//let spring to read from repositories
@EntityScan("missions/room/Domain")// let spring to scan all the entities and crete or update table accordingly
@CommonsLog
public class RoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomApplication.class, args);
        //log.info("server starts");
    }



}
