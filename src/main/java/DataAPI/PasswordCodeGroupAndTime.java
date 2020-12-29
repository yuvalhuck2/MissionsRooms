package DataAPI;

import missions.room.Domain.GroupType;

import java.time.LocalDateTime;

public class PasswordCodeGroupAndTime {
    private final String password;
    private final String code;
    private final LocalDateTime time;

    public PasswordCodeGroupAndTime(String code, String password) {
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
