package Utils;

import DataAPI.UserType;
import javafx.util.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    //TODO update leoback suffix
    final static String LEOBAECK_SUFFIX="@leobaeck.net";

    public static boolean checkString(String string){
        return (string!=null && !string.isEmpty());
    }

    //TODO add leobeck suffix to mail check
    public static boolean checkMail(String mail){
        return checkString(mail) && mail.indexOf('@')!=-1;
    }

    public static boolean checkPhone(String phone){
        return checkString(phone) && phone.matches("\\d{10}");

    }

    public static String getMailFromAlias(String alias) {
        return alias+LEOBAECK_SUFFIX;
    }

    public static String getAlias(String email){
        String pattern = "([^\\/]+)@leobaeck.net";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(email);
        if (m.find( )) {
            return m.group(1);
        }else {
            return null;
        }
    }

    /**
     * `lbYY-CC@leobaeck.net`
     * Where:
     * - YY is the absolute year (i.e., year 70 is Yud Bet, year 71 is Yud Alef, and so on)
     * - CC is the class.
     */
    public static Pair<String,String> getYearAndClassFromEmail(String email){
        String alias = Utils.getAlias(email);
        String pattern = "(.)-(..?)";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(alias);
        if (m.matches()){
            return new Pair<>(m.group(1), m.group(2));
        }
        return null;
    }


    /*public static int getNextRandom(int number){
        return random.nextInt(number);
    }*/
}
