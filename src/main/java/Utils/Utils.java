package Utils;

import DataAPI.UserType;
import javafx.util.Pair;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Set;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String YUD = "י ";
    private static final String YUD_ALEF = "יא ";
    private static final String YUD_BET = "יב ";

    //TODO update leoback suffix
    final static String LEOBAECK_SUFFIX="@leobaeck.net";
    // TODO delete when testing is done
    final static String TEST_SUFFIX = "@post.bgu.ac.il";

    final static Random randomizer = new Random();

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
        // return alias+LEOBAECK_SUFFIX;
        // TODO: remove when testing is done
        return alias+TEST_SUFFIX;
    }

    public static String getAlias(String email){
        String pattern = "([^\\/]+)" + TEST_SUFFIX;

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
    public static boolean stringsArrayEquals(String[] s1, String[] s2) {
        if (s1 == s2)
            return true;

        if (s1 == null || s2 == null)
            return false;

        int n = s1.length;
        if (n != s2.length)
            return false;

        for (int i = 0; i < n; i++) {
            if (!s1[i].equals(s2[i]))
                return false;
        }

        return true;
    }

    public static boolean checkStringArray(List<String> answers) {
        return answers.size() > 0 && answers.stream().allMatch(ans -> checkString(ans));
    }

    /*public static int getNextRandom(int number){
        return random.nextInt(number);
    }*/
    public static<T> T getRandomFromSet(Set<T> set) {
        T[] asArray = (T[]) set.toArray();
        return asArray[randomizer.nextInt(asArray.length)];
    }

    public static String getRootDirectory(){
//        Path currentRelativePath = Paths.get("").toAbsolutePath();
//        String rootDirectory = currentRelativePath.getRoot().toString();
//        return rootDirectory;
        return "/home/user1";
    }

    public static String getClassHebrewName(String className){
        String[] classStrings = className.split("=");
        return getClassNameByNumber(classStrings[0])+classStrings[1];
    }

    private static String getClassNameByNumber(String classString) {
        if(classString.equals("0")){
            return YUD;
        }
        if(classString.equals("1")){
            return YUD_ALEF;
        }
        return YUD_BET;

    }
}
