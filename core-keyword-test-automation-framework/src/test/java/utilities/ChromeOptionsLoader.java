package utilities;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class ChromeOptionsLoader {

    public ChromeOptions getOptions(Map<String, String> optionsMap, boolean runHeadless, Config config) {

        boolean mobileEmulation = Boolean.valueOf(config.getConfig(Config.Param.MOBILEEMULATION.getKey()));
        String deviceName = config.getConfig(Config.Param.DEVICENAME.getKey());

        ChromeOptions chromeOptions = new ChromeOptions();

        if (mobileEmulation) {
            Map<String, String> mobileEmulationMap = new HashMap<>();
            mobileEmulationMap.put("deviceName", deviceName);
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulationMap);
        }

        if (optionsMap != null) {
            for (Map.Entry<String, String> option : optionsMap.entrySet()) {
                String value = option.getValue();
                String key = option.getKey();
                switch (key.toLowerCase()) {
                    case "locale":
                        chromeOptions.addArguments("--lang=" + value);
                        break;
                }
            }
        }

        chromeOptions.setHeadless(runHeadless);

        return chromeOptions;
    }

}
