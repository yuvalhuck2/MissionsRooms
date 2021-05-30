package ExternalSystems;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UniqueStringGenerator {

    public static String getUniqueCode(String alias){

        String code=getTimeNameCode(alias);
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

    public static String getTimeNameCode(String toAdd){
        return LocalDateTime.now().toString().replace(':','_').replace('-','_')+toAdd;
    }

}
