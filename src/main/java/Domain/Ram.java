package Domain;

import Utils.StringAndTime;

import java.util.concurrent.ConcurrentHashMap;

public class Ram {

    //save apikey and alias for trace and clean not connected users
    private static final ConcurrentHashMap<String, StringAndTime> apiToAlias = new ConcurrentHashMap<>();


}
