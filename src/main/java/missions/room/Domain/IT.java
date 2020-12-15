package missions.room.Domain;

import javax.persistence.Entity;

@Entity
public class IT extends User {
    public IT() {
    }

    public IT(String alias, String password) {
        super(alias,password);
    }
    
}
