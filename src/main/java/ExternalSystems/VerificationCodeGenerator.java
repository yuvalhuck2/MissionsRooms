package ExternalSystems;
import org.apache.commons.lang3.RandomStringUtils;

public class VerificationCodeGenerator {

    public static String ret(){
        return RandomStringUtils.randomAlphabetic(20);
    }
}
