package DataObjects.FlatDataObjects;

import java.time.LocalDateTime;

public class PasswordAndTime {
    private final String password;
    private final LocalDateTime time;

    public PasswordAndTime(String password) {
        this.password=password;
        time=LocalDateTime.now();
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getPassword() {
        return password;
    }
}
