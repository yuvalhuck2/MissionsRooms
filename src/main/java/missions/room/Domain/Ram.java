package missions.room.Domain;

import Utils.StringAndTime;

import java.util.concurrent.ConcurrentHashMap;

public class Ram {

    //save apikey and alias for trace and clean not connected users
    private static final ConcurrentHashMap<String, StringAndTime> apiToAlias = new ConcurrentHashMap<>();

    public void addApi(String api,String alias){
        apiToAlias.put(api,new StringAndTime(alias));

    }


    public String getApi(String apiKey) {
        if(apiToAlias.containsKey(apiKey)) {
            return apiToAlias.get(apiKey).getString();
        }
        return null;
    }
}