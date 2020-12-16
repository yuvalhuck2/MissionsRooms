package missions.room.AcceptanceTests.AcceptanceTests;

import missions.room.AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestBridge;
import missions.room.AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsProxyBridge;

public class AcceptanceTestDriver {

        public static AcceptanceTestBridge getBridge() {
            AcceptanceTestsProxyBridge bridge = new AcceptanceTestsProxyBridge();
            //bridge.setRealBridge(new AcceptanceTestsRealBridge());
            return bridge;
    }
}
