package missions.room.Domain;

import DataObjects.APIObjects.MessageData;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Message {

    @Id
    private String id;

    private String sender;

    private String content;

    private String date;

    private String time;

    public Message() {
    }

    public Message(String id, String content, String sender) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        String[] times=id.split("T");
        this.date=times[0].replace('_','/');
        String[] hourTimes=times[1].split("_");
        this.time=hourTimes[0]+":"+hourTimes[1];
    }


    public MessageData getData() {
        return new MessageData(id,content,sender,date,time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message m = (Message) o;
        return  Objects.equals(content, m.content) &&
                Objects.equals(sender,m.sender);
    }

    public String getId() {
        return id;
    }
}
