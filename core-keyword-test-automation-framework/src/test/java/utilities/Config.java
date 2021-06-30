package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final String PROPERTIES_FILE_LOCATION = "../core-keyword-test-automation-framework/src/test/resources/config/Configuration.properties";

    private Properties properties = null;

    public Config() {
        properties = new Properties();
    }

    /**
     * Will load the properties for the browser
     */
    public void loadProperties(String driverType, String browser, String runHeadless, String enableJavascript,
                               String env, String mobileEmulation, String deviceName, String custom_capabilities,
                               String platformName, String platformVersion, String kobitonServerUrl, String deviceOrientation,
                               String captureScreenshots, String deviceGroup, String browserName, String app,
                               String autoAcceptAlerts, String autoGrantPermissions, String appWaitActivity,
                               String desiredCapabilities, String noReset, String fullReset) {
        InputStream input = null;
        try {
            input = new FileInputStream(PROPERTIES_FILE_LOCATION);
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!driverType.isEmpty()) {
            properties.put(Param.DRIVERTYPE.getKey(), driverType);
        }
        if (!browser.isEmpty()) {
            properties.put(Param.BROWSER.getKey(), browser);
        }
        if (!runHeadless.isEmpty()) {
            properties.put(Param.RUNHEADLESS.getKey(), runHeadless);
        }
        if (!enableJavascript.isEmpty()) {
            properties.put(Param.ENABLEJAVASCRIPT.getKey(), enableJavascript);
        }
        if (!env.isEmpty()) {
            properties.put(Param.ENV.getKey(), env);
        }
        if (!mobileEmulation.isEmpty()) {
            properties.put(Param.MOBILEEMULATION.getKey(), mobileEmulation);
        }
        if (!deviceName.isEmpty()) {
            properties.put(Param.DEVICENAME.getKey(), deviceName);
        }
        if (!custom_capabilities.isEmpty()) {
            properties.put(Param.CUSTOM_CAPABILITIES.getKey(), custom_capabilities);
        }
        if (!platformName.isEmpty()) {
            properties.put(Param.PLATFORMNAME.getKey(), platformName);
        }
        if (!platformVersion.isEmpty()) {
            properties.put(Param.PLATFORMVERSION.getKey(), platformVersion);
        }
        if (!kobitonServerUrl.isEmpty()) {
            properties.put(Param.KOBITONSERVERURL.getKey(), kobitonServerUrl);
        }
        if (!deviceOrientation.isEmpty()) {
            properties.put(Param.DEVICEORIENTATION.getKey(), deviceOrientation);
        }
        if (!captureScreenshots.isEmpty()) {
            properties.put(Param.CAPTURESCREENSHOTS.getKey(), captureScreenshots);
        }
        if (!deviceGroup.isEmpty()) {
            properties.put(Param.DEVICEGROUP.getKey(), deviceGroup);
        }
        if (!browserName.isEmpty()) {
            properties.put(Param.BROWSERNAME.getKey(), browserName);
        }
        if (!app.isEmpty()) {
            properties.put(Param.APP.getKey(), app);
        }
        if (!autoAcceptAlerts.isEmpty()) {
            properties.put(Param.AUTOACCEPTALERTS.getKey(), autoAcceptAlerts);
        }
        if (!autoGrantPermissions.isEmpty()) {
            properties.put(Param.AUTOGRANTPERMISSIONS.getKey(), autoGrantPermissions);
        }
        if (!appWaitActivity.isEmpty()) {
            properties.put(Param.APPWAITACTIVITY.getKey(), appWaitActivity);
        }
        if (!desiredCapabilities.isEmpty()) {
            properties.put(Param.DESIREDCAPABILITIES.getKey(), desiredCapabilities);
        }
        if (!noReset.isEmpty()) {
            properties.put(Param.NORESET.getKey(), noReset);
        }
        if (!fullReset.isEmpty()) {
            properties.put(Param.FULLRESET.getKey(), fullReset);
        }
    }

    public String getConfig(String param){
        return properties.getProperty(param);
    }

    public void setConfig(String param, String value){
        properties.put(param, value);
    }

    public enum Param {
        BROWSER("browser"),
        IMPLICIT_TIMEOUT("implicitTimeout"),
        WINDOWS_SIZE("windowSize"),
        DRIVERTYPE("driverType"),
        RUNHEADLESS("runHeadless"),
        ENABLEJAVASCRIPT("enableJavascript"),
        ENV("env"),
        MOBILEEMULATION("mobileEmulation"),
        DEVICENAME("deviceName"),
        CUSTOM_CAPABILITIES("custom_capabilities"),
        PLATFORMNAME("platformName"),
        PLATFORMVERSION("platformVersion"),
        KOBITONSERVERURL("kobitonServerUrl"),
        DEVICEORIENTATION("deviceOrientation"),
        CAPTURESCREENSHOTS("captureScreenshots"),
        DEVICEGROUP("deviceGroup"),
        BROWSERNAME("browserName"),
        APP("app"),
        AUTOACCEPTALERTS("autoAcceptAlerts"),
        AUTOGRANTPERMISSIONS("autoGrantPermissions"),
        APPWAITACTIVITY("appWaitActivity"),
        DESIREDCAPABILITIES("desiredCapabilities"),
        NORESET("noReset"),
        FULLRESET("fullReset");

        private final String key;

        Param(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

}
