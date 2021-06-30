package utilities;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Driver {

    private static final String IE_DRIVER_LOCATION = "../core-keyword-test-automation-framework/src/test/resources/drivers/IEDriverServer.exe";
    private static final String FIREFOX_DRIVER_LOCATION = "../core-keyword-test-automation-framework/src/test/resources/drivers/geckodriver.exe";
    private static  String CHROME_DRIVER_LOCATION;
    
    private static final String PROPERTY_DELIMITER = ",";

    private WebDriver driver;
    private Config config;
    private boolean mobile;

    private DesiredCapabilities capabilities;

    private static final String KEY_VALUE_DELIMITER = "=";
    
    static {
	    if( Driver.isWindows() ) {
	    	CHROME_DRIVER_LOCATION = "../core-keyword-test-automation-framework/src/test/resources/drivers/chromedriver.exe";
	    }else {
	    	CHROME_DRIVER_LOCATION = "/usr/bin/chromedriver";
	    }
    }

    /**
     * Constructor that takes no arguments
     */
    public Driver(Config config) {
        this.config = config;
        System.setProperty("webdriver.ie.driver", IE_DRIVER_LOCATION);
        System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_LOCATION);
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LOCATION);
    }

    /**
     * Getter that returns the current webDriver
     *
     * @return WebDriver, the current WebDriver
     */
    public WebDriver getWebDriver() {
        return driver;
    }

    /**
     * Getter that returns the current MobileDriver.
     *
     * @return MobileDriver the currently set MobileDriver.
     */
    public io.appium.java_client.MobileDriver getMobileDriver() {
        return (io.appium.java_client.MobileDriver) driver;
    }


    /**
     * Opens a web driver session using the specified browser driver.
     * Defaults to using Chrome if the browserName is not valid.
     * Firefox and Chrome can be set to run in headless mode.
     *
     */
    public void openBrowser() {
        Map<String, String> capabilitiesMap = null;
        String custom_capabilities = config.getConfig(Config.Param.CUSTOM_CAPABILITIES.getKey());
        boolean runHeadless = Boolean.valueOf(config.getConfig(Config.Param.RUNHEADLESS.getKey()));

        if (!custom_capabilities.isEmpty()) {
            capabilitiesMap = loadCapabilities(custom_capabilities);
        }
        switch (config.getConfig(Config.Param.BROWSER.getKey()).toLowerCase()) {
            case "ie":
            case "internet explorer":
                Map<String, String> defaultCapabilitiesMap = loadCapabilities("capabilities/default_IEOptions.properties");
                InternetExplorerOptions option = new IEOptionsLoader(defaultCapabilitiesMap).getOptions(capabilitiesMap);
                driver = new InternetExplorerDriver(option);
                break;

            case "firefox":
                driver = new FirefoxDriver(new FirefoxOptions().setHeadless(runHeadless));
                break;
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptionsLoader().getOptions(capabilitiesMap, runHeadless, config);
                driver = new ChromeDriver(chromeOptions);
                break;
            default:
                driver = new ChromeDriver(new ChromeOptions().setHeadless(runHeadless));
        }
    }

    /**
     * Opens a htmlunit driver session that simulates the specified browser.
     * By default Javascript is enabled.
     *
     * @param browserName The name of the web browser for htmlunit to simulate.
     */
    public void openHtmlUnit(String browserName) {
        openHtmlUnit(browserName, true);
    }

    /**
     * Opens a htmlunit driver session that simulates the specified browser.
     * Javascript support can be toggled.
     *
     * @param browserName      The name of the web browser for htmlunit to simulate.
     * @param enableJavascript Sets whether or not javascript is enabled.
     */
    public void openHtmlUnit(String browserName, boolean enableJavascript) {

        BrowserVersion browserVersion = BrowserVersion.BEST_SUPPORTED;
        // Set browser version based on the specified browser name. Otherwise keeps default browser setting or best supported.
        switch (browserName.toLowerCase()) {
            case "ie":
            case "internet explorer":
                browserVersion = BrowserVersion.INTERNET_EXPLORER;
                break;
            case "firefox":
                browserVersion = BrowserVersion.FIREFOX_60;
                break;
            case "chrome":
                browserVersion = BrowserVersion.CHROME;
                break;
        }

        driver = new HtmlUnitDriver(browserVersion, enableJavascript) {
            // Use a customized version of the HtmlUnit client for the driver
            @Override
            protected WebClient modifyWebClient(WebClient client) {
                final WebClient webClient = super.modifyWebClient(client);
                // Remove the default refresh handler that HtmlUnit uses
                webClient.setRefreshHandler(new HtmlUnitRefreshHandler());
                return webClient;
            }
        };
    }

    /**
     * Open the mobile device driver using the currently set capabilities.
     *
     */
    public void openDevice() throws MalformedURLException {
        String appiumServerUrl = config.getConfig(Config.Param.KOBITONSERVERURL.getKey()); // Set the server URL to be used in subsequent driver actions in the test script
        String currentDirectory = System.getProperty("user.dir");
        currentDirectory = currentDirectory + "\\src\\test\\resources\\drivers";

        switch (config.getConfig(Config.Param.PLATFORMNAME.getKey()).toLowerCase()) {
            case "android":
            case "droid":
                capabilities.setCapability("chromedriverExecutableDir", currentDirectory);
                driver = new AndroidDriver(new URL(appiumServerUrl), capabilities);
                break;
            case "ios":
                driver = new IOSDriver(new URL(appiumServerUrl), capabilities);
                break;
            default:
                driver = new AndroidDriver(new URL(appiumServerUrl), capabilities);
        }
    }

    /**
     * Initialize the required desired capabilities for the Appium driver.
     */
    public void initializeAppiumDriverCapabilities() {
        mobile = true;
        capabilities = new DesiredCapabilities();
        // The generated session will be visible to you only.
        capabilities.setCapability("sessionName", "Automation test session");
        capabilities.setCapability("deviceOrientation", config.getConfig(Config.Param.DEVICEORIENTATION.getKey()));
        capabilities.setCapability("captureScreenshots", Boolean.valueOf(config.getConfig(Config.Param.CAPTURESCREENSHOTS.getKey()).toLowerCase()));
        capabilities.setCapability("deviceGroup", config.getConfig(Config.Param.DEVICEGROUP.getKey()));
        capabilities.setCapability("autoAcceptAlerts", Boolean.valueOf(config.getConfig(Config.Param.AUTOACCEPTALERTS.getKey()).toLowerCase()));
        capabilities.setCapability("noReset", config.getConfig(Config.Param.NORESET.getKey()));
        capabilities.setCapability("fullReset", config.getConfig(Config.Param.FULLRESET.getKey()));
        // For deviceName, platformVersion Kobiton supports wildcard
        // character *, with 3 formats: *text, text* and *text*
        // If there is no *, Kobiton will match the exact text provided
        capabilities.setCapability("deviceName", config.getConfig(Config.Param.DEVICENAME.getKey()));
        capabilities.setCapability("platformVersion", config.getConfig(Config.Param.PLATFORMVERSION.getKey()));
        capabilities.setCapability("autoGrantPermissions", Boolean.valueOf(config.getConfig(Config.Param.AUTOGRANTPERMISSIONS.getKey()).toLowerCase()));
        capabilities.setCapability("platformName", config.getConfig(Config.Param.PLATFORMNAME.getKey()));
        capabilities.setCapability("appWaitActivity", config.getConfig(Config.Param.APPWAITACTIVITY.getKey()));
        // Open either a specified app or a web browser in the mobile device
        if (config.getConfig(Config.Param.APP.getKey()).isEmpty()) {
            capabilities.setCapability("browserName", config.getConfig(Config.Param.BROWSERNAME.getKey()));
        } else {
            // The maximum size of application is 500MB
            // By default, HTTP requests from testing library are expired
            // in 2 minutes while the app copying and installation may
            // take up-to 30 minutes. Therefore, you need to extend the HTTP
            // request timeout duration in your testing library so that
            // it doesn't interrupt while the device is being initialized.
            capabilities.setCapability("app", config.getConfig(Config.Param.APP.getKey()));
        }
    }

    /**
     * Sets the driver's desired capabilities via a string of keys and values.
     *
     * @param capabilityString A string of desired capabilities delimited by a comma. String should follow "key1=value1,key2=123" format for keys and values.
     */
    public void setCapabilities(String capabilityString) {
        String[] capabilities = capabilityString.split(PROPERTY_DELIMITER);

        String[] capabilityKeys = new String[capabilities.length];
        String[] capabilityValues = new String[capabilities.length];

        for (int i = 0; i < capabilities.length; i++) {
            String[] capability = capabilities[i].split(KEY_VALUE_DELIMITER);
            capabilityKeys[i] = capability[0];
            capabilityValues[i] = capability[1];
        }
        setCapabilities(capabilityKeys, capabilityValues);
    }

    /**
     * Sets the driver's desired capabilities using the passed in names and values.
     *
     * @param capabilityNames  A string array of the names of the capabilities.
     * @param capabilityValues A string array of the values of the capabilities.
     */
    public void setCapabilities(String[] capabilityNames, String[] capabilityValues) {
        for (int index = 0; index < capabilityNames.length; index++) {
            capabilities.setCapability(capabilityNames[index], capabilityValues[index]);
        }
    }

    /**
     * Will close the current active browser window
     */
    public void closeSingleWindow() {
        driver.close();
    }

    /**
     * will close all browser windows
     */
    public void closeAllWindows() {
        try {
            Set<String> windows = driver.getWindowHandles();
            Iterator<String> iter = windows.iterator();
            String[] winNames = new String[windows.size()];
            int i = 0;
            while (iter.hasNext()) {
                winNames[i] = iter.next();
                i++;
            }
            if (winNames.length > 1) {
                for (i = winNames.length; i > 1; i--) {
                    driver.switchTo().window(winNames[i - 1]);
                    driver.close();
                }
            }
            driver.switchTo().window(winNames[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method closes the browser window on which the focus is set and close the session of webdriver
     */
    public void quitDriver() {
        driver.quit();
    }

    /**
     * Will disable the driver's implicit wait setting.
     */
    public void disableImplicitWait() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    /**
     * Will enable the driver's implicit wait setting.
     */
    public void enableImplicitWait() {
        int implicitWaitTime = Integer.parseInt(config.getConfig(Config.Param.IMPLICIT_TIMEOUT.getKey()));
        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    }

    /**
     * maximizes the browser window
     */
    public void setBrowserWindowSize() {
        String windowSize = config.getConfig(Config.Param.WINDOWS_SIZE.getKey());
        //TODO: verify this method
        if (!windowSize.isEmpty()) {
            if (windowSize.equalsIgnoreCase("fullscreen")) {
                driver.manage().window().fullscreen();
            } else if (windowSize.equalsIgnoreCase("maximize")) {
                driver.manage().window().maximize();
            } else {
                String[] args = windowSize.split(PROPERTY_DELIMITER);
                int width = Integer.parseInt(args[0]);
                int height = Integer.parseInt(args[1]);
                driver.manage().window().setSize(new Dimension(width, height));
            }
        }
    }

    public Map<String, String> loadCapabilities(String fileName) {
        Properties props = new Properties();
        URL baseResource = ClassLoader.getSystemResource(fileName);
        try {
            if (baseResource != null) {
                props.load(baseResource.openStream());
            } else {
                throw new RuntimeException("Unable to find custom capabilities file '" + fileName + "'!");
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load custom capabilities from '" + baseResource.getPath() + "'!", e);
        }

        Map<String, String> capabilitiesMap = new HashMap(props);

        for (Map.Entry<String, String> entry : capabilitiesMap.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
        }

        return capabilitiesMap;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }
    
    private static String getOsName() {
        return  System.getProperty("os.name"); 
    }
     
     private static boolean isWindows() {
        return Driver.getOsName().startsWith("Windows");
     }
}
