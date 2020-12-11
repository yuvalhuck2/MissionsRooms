package Domain.missions;

import java.util.concurrent.ConcurrentHashMap;

public class Locker {
    private static final ConcurrentHashMap<String, StringLocker> map= new ConcurrentHashMap();

    public static void writeLockString(String id){
        StringLocker stringLocker=map.putIfAbsent(id,new StringLocker());
        if(stringLocker!=null){
            stringLocker.increaseLocker();
        }
    }

    public static void  writeReleaseString(String id){
        StringLocker stringLocker=map.get(id);
        if(stringLocker.decreaseLocker()<=0){
            map.remove(id);
        }
    }
}
