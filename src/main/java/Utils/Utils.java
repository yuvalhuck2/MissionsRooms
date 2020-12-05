package Utils;

import DataAPI.UserType;

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
}
