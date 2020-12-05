package Domain;

import java.time.LocalDateTime;

/**
 * Object to save verifications code and there timestamp
 */
public class CodeAndTime {
    private String code;
    private LocalDateTime time;

    public CodeAndTime(String code) {
        this.code = code;
        time=LocalDateTime.now();
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
