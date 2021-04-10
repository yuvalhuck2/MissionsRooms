package DataAPI;

import java.time.LocalDateTime;

public class PasswordCodeAndTime extends PasswordAndTime {
    private final String code;

    public PasswordCodeAndTime(String code, String password) {
        super(password);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
