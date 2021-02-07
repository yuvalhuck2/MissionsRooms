package missions.room.Communications.Publisher;

public class SinglePublisher {

    private static Publisher publisher=null;
    public static  Publisher getInstance() {
        return publisher;
    }

    public static void initPublisher(Publisher pub){
            publisher=pub;
    }



}
