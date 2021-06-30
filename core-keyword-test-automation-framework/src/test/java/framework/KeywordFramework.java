package framework;

import filereaders.FeatureReader;
import filereaders.ObjectRepositoryReader;
import filereaders.DataReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;
import utilities.CommonFunctions;
import utilities.Config;
import utilities.Driver;
import utilities.Reports;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

public class KeywordFramework {

    public static final int ADD_TEST_SHEET_TO_REPORT_LOG_LEVEL = 2;
    public static final String CONDITIONAL_KEYWORD = "ifexists";
    private static Reports reports;

    private ThreadLocal<Driver> driver = new ThreadLocal<>();

    public ThreadLocal<Driver> getDriver() {
        return driver;
    }

    Config configurations = null;

    /**
     * The main method to drive the test with data file.
     */
    public void runTest(String dataFile, String dataId) {

        // Get the name of the test from the test method
        String testName = new Throwable().getStackTrace()[1].getMethodName();
        // Declare all test parameters
        String testAction;
        String testObjectName;
        WebElement testObject;
        String testObjectType;
        String testInput;
        String testOutput;
        By locator;
        // Initialize file readers
        Stack<FeatureReader> featureStack = new Stack<>(); // FeatureReader is a stack so that parent features can call upon other features
        ObjectRepositoryReader objectRepo = null;
        // Initialize current page parameter to keep track of the current page that we are working with
        String currentPage = "";

        Hashtable<String, String> outputVariables = new Hashtable<>();

        //Load Feature Data
        DataReader repositoryData = new DataReader(configurations);
        if (!dataFile.isEmpty()) {
            repositoryData.loadTestCaseVariablesData(dataFile, dataId);
        }

        // LoadFeatureFile (LoadTestFile)
        // Get all test steps for the test case
        try {
            // The initially called parent feature will always be at the bottom of the stack
            featureStack.push(new FeatureReader(testName));
            System.out.println("Test Case [" + testName + "] loaded.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to open TestFile [" + testName + ".xls] as expected.");
            System.exit(1);
        }
        addTestStepsToReport(featureStack.peek());

        // Continue running through each feature file that is on the stack
        while (!featureStack.empty()) {
            // Test loop - executes all steps in a test case
            do {
                // Load a new Object Repository only if the current test step needs an Object on a different page
                if (!currentPage.equals(featureStack.peek().getCurrentPage())) {
                    if (!featureStack.peek().getCurrentPage().isEmpty()) {
                        currentPage = featureStack.peek().getCurrentPage();
                        try {
                            objectRepo = new ObjectRepositoryReader(driver.get(), currentPage);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Assert.fail("Failed to load objects from the excel file[" + currentPage + "] as expected.  Exiting test.");
                            System.exit(1);
                        }
                    }
                }

                // Test Step Information
                testAction = featureStack.peek().getCurrentAction();
                testObjectName = featureStack.peek().getCurrentObject();
                testObjectType = ""; //TODO
                testInput = featureStack.peek().getCurrentInput();
                testOutput = featureStack.peek().getCurrentOutput();
                locator = null;

                // If the test step is a conditional step, only ge the object if it exists within the specified time out input
                if (testAction.toLowerCase().contains(CONDITIONAL_KEYWORD)) {
                    String[] delimitedInput = testInput.split(CommonFunctions.ESCAPED_INPUT_PARAMETER_DELIMITER);
                    String waitTime = delimitedInput[0];
                    // ReadObject (getObject) only if an object repository has been initialized
                    testObject = objectRepo != null ? objectRepo.getObject(testObjectName, waitTime) : null;
                    locator = objectRepo != null ? objectRepo.getLocator(testObjectName) : null;
                    // Remove the timeout parameter from the test input string
                    if (delimitedInput.length > 1) {
                        testInput = testInput.substring(waitTime.length() + CommonFunctions.INPUT_PARAMETER_DELIMITER.length());
                    } else {
                        testInput = "";
                    }
                } else {
                    // ReadObject (getObject) only if an object repository has been initialized
                    testObject = objectRepo != null ? objectRepo.getObject(testObjectName, null) : null;
                    locator = (objectRepo != null && !testObjectName.isEmpty()) ? objectRepo.getLocator(testObjectName) : null;
                }

                // ExecuteStep
                try {
                    if (testAction.toLowerCase().equals("navigateto")) { //Set a new web driver
                        createWebDriver();
                    } else if (testAction.toLowerCase().equals("openapp")) { //Set a new mobile driver
                        createMobileDriver();
                    }

                    CommonFunctions.performActions(driver, testAction, testObjectName, testObject, testObjectType, testInput, testOutput, repositoryData, outputVariables, locator);

                    // If current action is the 'callTo' keyword, then push the called feature file onto the feature stack
                    if (testAction.equalsIgnoreCase("callto") || testAction.toLowerCase().equalsIgnoreCase("ifelsecondition")) {
                        testInput = testAction.toLowerCase().equals("ifelsecondition") ? ifElseCondition(testInput, testObject, outputVariables, repositoryData) : testInput;
                        if (!testInput.equals("ignore")) {
                            featureStack.peek().nextStep(); // Advance the parent feature to the step after the 'callTo' step
                            featureStack.push(new FeatureReader(testInput, true));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CommonFunctions.addScreenshotToReport(driver.get().getWebDriver());
                    Assert.fail("Test Step [" + testAction + "] failed unexpectedly.");
                }
            } while (featureStack.peek().nextStep());
            // Once all steps in the feature at the top of the stack are ran, pop that feature off the stack to resume running the parent feature.
            featureStack.pop();
        }
    }

    private void createMobileDriver() {
        if (driver.get() != null) {
            driver.get().quitDriver(); //remove previous sessions
        }
        driver.set(new Driver(configurations));
        driver.get().initializeAppiumDriverCapabilities();
        // If the desired capabilities parameter is used, then set those capabilities
        // for the driver.
        if (!configurations.getConfig(Config.Param.DESIREDCAPABILITIES.getKey()).equals("null")) {
            driver.get().setCapabilities(configurations.getConfig(Config.Param.DESIREDCAPABILITIES.getKey()));
        }
        try {
            driver.get().openDevice();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.get().setMobile(true);
    }

    private void createWebDriver() {
        if (driver.get() != null) {
            driver.get().quitDriver(); //remove previous sessions
        }
        driver.set(new Driver(configurations));
        if (configurations.getConfig(Config.Param.DRIVERTYPE.getKey()).equalsIgnoreCase("htmlunit")) {
            driver.get().openHtmlUnit(configurations.getConfig(Config.Param.BROWSER.getKey()),
                    Boolean.parseBoolean(configurations.getConfig(Config.Param.ENABLEJAVASCRIPT.getKey())));
        } else {
            driver.get().openBrowser();
        }
        driver.get().setBrowserWindowSize();
        driver.get().enableImplicitWait();
        driver.get().setMobile(false);
    }

    /**
     * This method verify the conditions indicated and return name of the feature to run.
     * @param input
     * @param outputVariables
     */
    private static String ifElseCondition(String input, WebElement testObject, Hashtable<String, String> outputVariables, DataReader repositoryData) {
        input = CommonFunctions.processInput(input, repositoryData, outputVariables);
        String[] args = input.split(CommonFunctions.ESCAPED_INPUT_PARAMETER_DELIMITER);
        String verificationType = args[0];
        String value = "";
        String featureIf;
        String featureElse = "";
        String expectedValue = "";
        String ignore = "ignore";

        if (verificationType.toLowerCase().contains("text")) {
            value = args[1];
            expectedValue = args[2];
            featureIf = args[3];
            if (args.length == 5) {
                featureElse = args[4];
            }
        } else if (verificationType.toLowerCase().contains("true") || verificationType.contains("false")) {
            value = args[1];
            featureIf = args[2];
            if (args.length == 4) {
                featureElse = args[3];
            }
        } else {
            featureIf = args[1];
            if (args.length == 3) {
                featureElse = args[2];
            }
        }

        switch (verificationType.toLowerCase().trim()) {
            case "textequal":
                if (value.equals(expectedValue)) {
                    return featureIf;
                } else if (!featureElse.isEmpty()) {
                    return featureElse;
                } else {
                    featureIf = ignore;
                }
                break;
            case "textcontains":
                if (value.contains(expectedValue)) {
                    return featureIf;
                } else if (!featureElse.isEmpty()) {
                    return featureElse;
                } else {
                    featureIf = ignore;
                }
                break;
            case "true":
                if (value.toLowerCase().equals("true")) {
                    return featureIf;
                } else if (!featureElse.isEmpty()) {
                    return featureElse;
                } else {
                    featureIf = ignore;
                }
                break;
            case "false":
                if (value.toLowerCase().equals("false")) {
                    return featureIf;
                } else if (!featureElse.isEmpty()) {
                    return featureElse;
                } else {
                    featureIf = ignore;
                }
                break;
            case "exists":
                if (testObject != null && testObject.isDisplayed()) {
                    return featureIf;
                } else if (!featureElse.isEmpty()) {
                    return featureElse;
                } else {
                    featureIf = ignore;
                }
                break;
            case "doesnotexist":
                if (testObject == null || !testObject.isDisplayed()) {
                    return featureIf;
                } else if (!featureElse.isEmpty()) {
                    return featureElse;
                } else {
                    featureIf = ignore;
                }
                break;
            default:
                Reporter.log("No verification arguments supplied to the IfElseCondition action.<br>", 1);
                Assert.fail("No verification arguments supplied to the IfElseCondition action.<br>");
        }

        return featureIf;
    }

    /**
     * Clean up screen shot directory before test execution.
     */
    @BeforeSuite(alwaysRun = true)
    public void cleanUpPreviousScreenshots() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        try {
            File screenshotDirectory = new File(CommonFunctions.SCREENSHOT_PATH);
            // Make sure the screenshot directory exists before attempting to wipe it.
            if (screenshotDirectory.exists()) {
                FileUtils.cleanDirectory(screenshotDirectory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
    }

    /**
     * Initialize test settings before each the test is ran.
     *
     * @param screenshotSetting Sets the frequency screenshots will be added to the test report during execution.
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters("screenshotSetting")
    public void initializeTestSettings(@Optional("OnFail") String screenshotSetting) {
        CommonFunctions.setScreenshotLogLevel(screenshotSetting);
    }

    /**
     * Initialize test report.
     *
     * @param xml
     */
    @BeforeSuite(alwaysRun = true)
    public void initializeTestReports(XmlTest xml) {
        reports = new Reports(xml);
    }

    /**
     * Initialize the browser before each test is ran. Also the data values are loaded from the data file according
     * to the environment provided from the xml or into the globalVariables.properties file
     *
     * @param browser The name of the web browser to run the test upon.
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({"driver", "browser", "runHeadless", "enableJavascript", "env",
            "mobileEmulation", "deviceName", "custom_capabilities", "platformName",
            "platformVersion", "kobitonServerUrl", "deviceOrientation",
            "captureScreenshots", "deviceGroup", "browserName", "app", "autoAcceptAlerts",
            "autoGrantPermissions", "appWaitActivity", "desiredCapabilities", "noReset", "fullReset"})
    public void initializeBrowser(@Optional("") String driverType, @Optional("") String browser, @Optional("") String runHeadless,
                                  @Optional("") String enableJavascript, @Optional("") String env, @Optional("") String mobileEmulation,
                                  @Optional("") String deviceName, @Optional("") String custom_capabilities, @Optional("") String platformName,
                                  @Optional("") String platformVersion, @Optional("") String kobitonServerUrl, @Optional("") String deviceOrientation,
                                  @Optional("") String captureScreenshots, @Optional("") String deviceGroup, @Optional("") String browserName,
                                  @Optional("") String app, @Optional("") String autoAcceptAlerts, @Optional("") String autoGrantPermissions,
                                  @Optional("") String appWaitActivity, @Optional("") String desiredCapabilities, @Optional("") String noReset, @Optional("") String fullReset
    ) {

        configurations = new Config();
        configurations.loadProperties(driverType, browser, runHeadless, enableJavascript,
                env, mobileEmulation, deviceName, custom_capabilities,
                platformName, platformVersion, kobitonServerUrl, deviceOrientation,
                captureScreenshots, deviceGroup, browserName, app,
                autoAcceptAlerts, autoGrantPermissions, appWaitActivity,
                desiredCapabilities, noReset, fullReset);
        System.out.println();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult) {
        reports.generateReport(testResult, driver);
        driver.get().quitDriver();
    }

    /**
     * Will add the table of tests steps to the reportng report.
     *
     * @param feature The feature file that is being used for test execution.
     */
    public void addTestStepsToReport(FeatureReader feature) {
        List<String[]> steps = feature.getStepValuesList();

        String tableHTML = "<table border='1'><tbody><tr><th>Action</th><th>Object</th><th>Input</th><th>Output</th></tr>";

        for (String[] values : steps) {
            tableHTML = tableHTML + "<tr>";
            for (String value : values) {
                tableHTML = tableHTML + "<td>" + value + "</td>";
            }
            tableHTML = tableHTML + "</tr>";
        }
        tableHTML = tableHTML + "</tbody></table><br>";
        Reporter.log(tableHTML, ADD_TEST_SHEET_TO_REPORT_LOG_LEVEL);
    }
}
