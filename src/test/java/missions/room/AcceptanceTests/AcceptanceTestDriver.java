package missions.room.AcceptanceTests;

import missions.room.AcceptanceTestsBridge.AcceptanceTestBridge;
import missions.room.AcceptanceTestsBridge.AcceptanceTestsProxyBridge;

public class AcceptanceTestDriver {

        public static AcceptanceTestBridge getBridge() {
            AcceptanceTestsProxyBridge bridge = new AcceptanceTestsProxyBridge();
            //bridge.setRealBridge(new AcceptanceTestsRealBridge());
            return bridge;
    }
}
