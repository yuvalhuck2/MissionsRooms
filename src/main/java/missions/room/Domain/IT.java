package missions.room.Domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class IT extends User {




    public IT() {
    }


    public IT( String alias,String password) {
        super(alias,password);

    }
    
}
