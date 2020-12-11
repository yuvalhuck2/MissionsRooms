package DataAPI;

import java.time.LocalDateTime;

public class PasswordCodeAndTime {
    private final String password;
    private final String code;
    private final LocalDateTime time;

    public PasswordCodeAndTime(String code,String password) {
        this.code = code;
        this.password=password;
        time=LocalDateTime.now();
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getPassword() {
        return password;
    }
}
