package ExternalSystems;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UniqueStringGenerator {

    public static String getUniqueCode(String alias){

        String code=LocalDateTime.now().toString().replace(':','_').replace('-','_')+alias;
        return shuffleString(code);

    }

    private static String shuffleString(String string){
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        StringBuilder builder = new StringBuilder();
        for (String letter : letters) {
            builder.append(letter);
        }
        return builder.toString();
    }

}
