package Domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//TODO talk about delete that class
@Entity
public class ChatRoom implements Serializable {

    @Id
    private String roomId;

    @OneToMany
    private List<RoomMessage> roomMessages;

    public ChatRoom() {
    }

    public ChatRoom(String room) {
        this.roomId=room;
        roomMessages=new ArrayList<>();
    }


}
