package DataObjects.APIObjects;

import java.util.Objects;

public class MessageData {
    private final String id;
    private final String message;
    private final String writer;
    private final String date;
    private final String time;
    private String apiKey;
    private String target;

    public MessageData(String id, String message, String writer, String date, String time) {
        this.id = id;
        this.message = message;
        this.writer = writer;
        this.date=date;
        this.time=time;
    }

    public String getMessage() {
        return message;
    }

    public String getWriter() {
        return writer;
    }

    public String getId() {
        return id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getTarget() {
        return target;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageData m = (MessageData) o;
        return  Objects.equals(message, m.message) &&
                Objects.equals(writer, m.writer);
    }
}
