package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestBridge;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsProxyBridge;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsRealBridge;

public class AcceptanceTestDriver {

        public static AcceptanceTestBridge getBridge() {
            AcceptanceTestsProxyBridge bridge = new AcceptanceTestsProxyBridge();
            //bridge.setRealBridge(new AcceptanceTestsRealBridge());
            return bridge;
    }
}
