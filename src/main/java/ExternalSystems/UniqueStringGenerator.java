package ExternalSystems;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class UniqueStringGenerator {

    public static String getUniqueCode(){
        return LocalDateTime.now().toString().replace(':','_').replace('-','_');
    }

}
