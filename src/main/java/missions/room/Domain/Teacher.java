package missions.room.Domain;

import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Repo.MissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationContext;
import missions.room.*;

import javax.persistence.*;


@Entity
@Configurable
//@EntityListeners(RepositoryAwareListener.class)
public class Teacher extends SchoolUser {

    @ManyToOne
    private Classroom classroom;

    @Enumerated(EnumType.ORDINAL)
    private GroupType groupType;

    @Autowired
    private transient MissionRepo missionRepo;


    public Teacher() {
    }

    public Teacher(String alias, String firstName, String lastName, Classroom classroom, GroupType groupType) {
        super(alias, firstName, lastName);
        this.classroom = classroom;
        this.groupType = groupType;
    }

    public Teacher(String alias, String firstName, String lastName,String password) {
        super(alias, firstName, lastName);
        this.password=password;
    }


}