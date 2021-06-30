package utilities;

import org.openqa.selenium.ie.InternetExplorerOptions;

import java.util.Map;

public class IEOptionsLoader {

    InternetExplorerOptions options;

    public IEOptionsLoader(Map<String, String> defaultCapabilitiesMap) {
        options = new InternetExplorerOptions();
        options = getOptions(defaultCapabilitiesMap);
    }

    public InternetExplorerOptions getOptions(Map<String, String> optionsMap) {
        if (optionsMap != null) {
            for (Map.Entry<String, String> option : optionsMap.entrySet()) {
                String value = option.getValue();
                String key = option.getKey();
                if (value.toLowerCase().equals("true") || value.toLowerCase().equals("false")) {
                    options.setCapability(key, Boolean.valueOf(value));
                } else {
                    options.setCapability(key, value);
                }
            }
        }
        return options;
    }
}
