package ExternalSystems;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class VerificationCodeGenerator {

    public static String getUniqueCode(){
        return RandomStringUtils.randomAlphabetic(15)+getTimeString();
    }

    private static String getTimeString() {
        return LocalDateTime.now().toString().replace(':','_').replace('-','_');
    }
}
