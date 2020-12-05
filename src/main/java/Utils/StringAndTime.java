package Utils;

import java.time.LocalDateTime;

/**
 * Object to save string and a timestamp
 */
public class StringAndTime {
    private String string;
    private LocalDateTime time;

    public StringAndTime(String string) {
        this.string = string;
        time=LocalDateTime.now();
    }

    public String getString() {
        return string;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
