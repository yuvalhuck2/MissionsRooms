//package Domain;
//
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
////TODO talk about delete that class
//@Entity
//public class ChatRoom implements Serializable {
//
//    @Id
//    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @JoinColumn(name="CLASSROOM_ALIAS")
//    private Room room;
//
//    @OneToMany
//    private List<RoomMessage> roomMessages;
//
//    public ChatRoom() {
//        roomMessages=new ArrayList<>();
//    }
//
//
//
//}
