package Utils;

import DataAPI.UserType;

import java.util.Random;

public class Utils {

    final static String LEOBAECK_SUFFIX="@leobaeck.net";
    final static Random random=new Random();

    public static boolean checkString(String string){
        return (string!=null && !string.isEmpty());
    }

    public static String getMailFromAlias(String alias) {
        return alias+LEOBAECK_SUFFIX;
    }

    public static int getNextRandom(int number){
        return random.nextInt(number);
    }
}
