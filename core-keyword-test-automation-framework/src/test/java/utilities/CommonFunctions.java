package utilities;

import filereaders.DataReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;
import org.testng.Reporter;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFunctions {

    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private static final Pattern INPUT_VARIABLE_ENCLOSURE = Pattern.compile("<<(.+)>>");

    public static final String ESCAPED_INPUT_PARAMETER_DELIMITER = "\\|\\|";
    public static final String INPUT_PARAMETER_DELIMITER = "||";

    public static final String ESCAPED_INPUT_VARIABLE_START_DELIMITER = "[${]";
    public static final String INPUT_VARIABLE_START_DELIMITER = "${";
    public static final String INPUT_VARIABLE_END_DELIMITER = "}";

    public static final String SCREENSHOT_PATH = "test-output\\html\\screenshots\\";
    public static final String REPORT_SCREENSHOT_PATH = "screenshots\\";
    public static final String SOURCE_CODE_PATH = "src/test/resources/htmlSource/";
    public static final String DEFAULT_SCREENSHOT_HEIGHT = "50%";
    public static final String DEFAULT_SCREENSHOT_WIDTH = "50%";

    public static final String SELECT_TYPE = "select";
    public static final String CHECKBOX_TYPE = "checkbox";
    public static final String RADIO_TYPE = "radio";
    public static final String TEXT_INPUT_TYPE = "text";
    public static final String BUTTON_TYPE = "button";
    public static final String IMAGE_TYPE = "image";
    public static final String TABLE_TYPE = "table";

    public static final int VERIFICATION_FAILURE_LOG_LEVEL = 1;
    public static final int VERIFICATION_PASS_LOG_LEVEL = 2;
    public static final int PERFORM_ACTION_LOG_LEVEL = 3;

    public static boolean screenShotOnPass = false;
    public static boolean screenShotOnAllSteps = false;

    public static int tableRowNumber = -1;

    public static final String SAVED_HTML_FILE_NAMES = "htmlFileNames.txt";

    /**
     * Will perform the test step based on the parameters passed in from the feature file.
     *
     * @param keyword    The name of the action that should be performed.
     * @param object     The web element to perform the action upon.
     * @param objectType The type of the web element.
     * @param theInput   One input or a list of input parameters to be used when performing the action.
     * @param output     The parameter name to output a value to.
     */
    public static void performActions(ThreadLocal<Driver> driver, String keyword, String objectName, WebElement object, String objectType, String theInput, String output, DataReader repositoryDataReader, Hashtable<String, String> outputVariables, By locator) throws Exception {

        String input = processInput(theInput, repositoryDataReader, outputVariables);

        // Log the current step being performed
        String actionLogStatement = "Performing the '"+keyword+"' action";
        if (object != null){
            actionLogStatement = actionLogStatement+" upon the '"+objectName+"' object";
        }
        if (!theInput.isEmpty()){
            actionLogStatement = actionLogStatement+" with an input of '"+input+"'";
        }
        if (!output.isEmpty()){
            actionLogStatement = actionLogStatement+" and with an output of '"+output+"'";
        }
        Reporter.log(actionLogStatement+".<br>", PERFORM_ACTION_LOG_LEVEL);

        // If the type has not been passed in, then assume the object type by accessing the web element properties
        String type = "";
        if (objectType.isEmpty()){
            if (object != null) {
                type = inferObjectType(object);
            }
        }
        else{
            type = objectType;
        }

        switch (keyword.toLowerCase().trim()){
        	case "selectbyindex":
        		WebActions.selectByIndex(object,input);
        		break;
            case "input":
                 WebActions.input(object, type, input);
                break;
            case "inputifexists":
                WebActions.inputIfExists(object, type, input);
                break;
            case "inputifexistsandpressenter":
                WebActions.inputIfExistsAndPressEnter(object, type, input);
                break;
            case "inputdateoncalendar":
                WebActions.inputDateOnCalendar(object, type, input);
                break;
            case "click":
                WebActions.click(driver.get().getWebDriver(), object, type);
                break;
            case "clickinsidetable":
                WebActions.clickOnElementTable(driver.get().getWebDriver(), object, input);
                break;
            case "clickjs":
                WebActions.clickJS(driver.get().getWebDriver(), object);
                break;
            case "clickifexists":
                WebActions.clickIfExists(driver.get().getWebDriver(), object, type);
                break;
            case "clickoncheckboxintable":
                WebActions.clickOnCheckboxInTable(driver.get().getWebDriver(), object, input);
                break;
            case "hover":
                WebActions.hover(driver.get().getWebDriver(), object, type);
                break;
            case "getvalue":
                WebActions.getValue(object, type, input, output, outputVariables);
                break;
            case "gettext":
                WebActions.getText(object, output, outputVariables);
                break;
            case "getemail":
                WebActions.getEmail(input, output, outputVariables);
                break;
            case "clickifcheckboxisunselected":
                WebActions.clickIfCheckboxIsUnselected(object);
                break;
            case "getcheckboxstate":
                WebActions.getCheckboxState(object, output, outputVariables);
                break;
            case "navigateto":
                WebActions.navigateTo(driver.get().getWebDriver(), input);
                break;
            case "gotourl":
                WebActions.goToUrl(driver.get().getWebDriver(), input);
                break;
            case "switchwindow":
            case "switchtab":
                WebActions.switchTab(driver.get().getWebDriver(), input);
                break;
            case "opennewtab":
                WebActions.openNewTab(driver.get().getWebDriver(), input);
                break;
            case "closetab":
                WebActions.closeTab(driver.get().getWebDriver());
                break;
            case "handlealert":
                WebActions.handleAlert(driver.get().getWebDriver(), input);
                break;
            case "deletecookies":
                WebActions.deleteCookies(driver.get().getWebDriver(), input);
                break;
            case "waitfor":
                WebActions.waitFor(driver.get().getWebDriver(), object, input, locator);
                break;
            case "verify":
                WebActions.verify(driver.get().getWebDriver(), object, type, input);
                break;
            case "selectelement":
                WebActions.selectElement(driver.get().getWebDriver(), object, input);
                break;
            case "settablerow":
                WebActions.setTableRow(driver.get().getWebDriver(), object, type, input);
                break;
            case "exit":
                WebActions.exit(driver.get());
                break;
            case "takescreenshot":
                // If the test is already set to take a screenshot on all steps, there is no need add an additional screenshot.
                if (!screenShotOnAllSteps) {
                    addScreenshotToReport(driver.get().getWebDriver());
                }
                break;
            case "switchframe":
                WebActions.switchFrame(driver.get().getWebDriver(), object,input);
                break;
            case "skip":
            	break;
            case "press":
                MobileActions.press(driver.get().getMobileDriver(),object);
                break;
            case "tap":
                MobileActions.tapScreen(driver.get().getMobileDriver(), object, input);
                break;            
            case "tapifexists":
                MobileActions.tapIfExists(driver.get().getMobileDriver(), object, input);
                break;
            case "tapifattributeis":
				MobileActions.tapIfAttributeIs(driver.get().getMobileDriver(), object, input);
				break;
            case "getaccountviewandtapoption":
            	MobileActions.getAccountViewAndTapOption(driver.get().getMobileDriver(), input);
            	break;
            case "longPress":
                MobileActions.longPress(driver.get().getMobileDriver(),object);
                break;
            case "swipeupwards":
            case "swipeup":
                MobileActions.swipeUpwards(driver.get().getMobileDriver(), object, input);
                break;
            case "swipedownwards":
            case "swipedown":
                MobileActions.swipeDownwards(driver.get().getMobileDriver(), object, input);
                break;
            case "pressandswipe":
                MobileActions.pressAndSwipe(driver.get().getMobileDriver(), object, input);
                break;
            case "scrolldown":
                if (driver.get().isMobile()){
                    MobileActions.scrollDown(driver.get().getMobileDriver(), object, input);
                } else {
                    WebActions.scrollDown(driver.get().getWebDriver());
                }
                break;
            case "hidekeyboard":
                MobileActions.hideKeyboard(driver.get().getMobileDriver());
                break;
            case "switchcontext":
                MobileActions.switchContext(driver.get().getMobileDriver(), input);
                break;
            case "rotatedevice":
                MobileActions.rotateDevice(driver.get().getMobileDriver(), input);
                break;
            default:
                if (!keyword.toLowerCase().equals("exit") && !keyword.toLowerCase().equals("callto") && !keyword.toLowerCase().equals("openapp")
                && !keyword.toLowerCase().equals("ifelsecondition")) {
                    Reporter.log("The Keyword specified does not exist. <br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    Assert.fail("The Keyword specified does not exist. <br>");
                }
        }
        // Add a screenshot after each step is executed, if the setting is turned on.
        if(screenShotOnAllSteps && !keyword.equalsIgnoreCase("exit") && !keyword.toLowerCase().equals("callto")){
            addScreenshotToReport(driver.get().getWebDriver());
        }
    }

    /**
     * Will determine object type based off the object passed in.
     *
     * @param object The object we need to get the type from.
     * @return String, the object type.
     */
    private static String inferObjectType(WebElement object) {
        String outgoingObjectType;
        String incomingObjectType = object.getTagName();

        switch (incomingObjectType == null ? "null" : incomingObjectType.toLowerCase()) {
            case "img":
                outgoingObjectType = IMAGE_TYPE;
                break;
            case "link":
                outgoingObjectType = "Link";
                break;
            case "button":
                outgoingObjectType = BUTTON_TYPE;
                break;
            case "list":
                outgoingObjectType = "WebList";
                break;
            case "select":
                outgoingObjectType = SELECT_TYPE;
                break;
            case "table":
                outgoingObjectType = TABLE_TYPE;
                break;
            case "input":
                //if tag name is input, check type attribute to determine what type of input.
                // can add to the this if more that buttons
                String objectTypeAttribut = object.getAttribute("type");
                switch (objectTypeAttribut.toLowerCase()) {
                    case "checkbox":
                        outgoingObjectType = CHECKBOX_TYPE;
                        break;
                    case "button":
                        outgoingObjectType = BUTTON_TYPE;
                        break;
                    case "radio":
                        outgoingObjectType = RADIO_TYPE;
                        break;
                    case "image":
                        outgoingObjectType = IMAGE_TYPE;
                        break;
                    case "table":
                        outgoingObjectType = TABLE_TYPE;
                        break;
                    case "text":
                    case "tel":
                    case "password":
                    case "search":
                        outgoingObjectType = TEXT_INPUT_TYPE;
                        break;
                    default:
                        outgoingObjectType = "Unable to determine input type.";
                }
                break;
            default:
                outgoingObjectType = "Unable to determine object type.";
        }
        return outgoingObjectType;
    }

    /**
     * Will process each of the input arguments to replace specified input variables with outputted variable values.
     *
     * @param input The input string passed in from the test sheet.
     */
    public static String processInput(String input, DataReader dataReader, Hashtable<String, String> outputVariables) {
        String processedInput = "";
        String[] args = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);

        for (String arg : args) {

            Matcher matches = INPUT_VARIABLE_ENCLOSURE.matcher(arg);
            // If the input has matches to the variable enclosure, then get the variable name from the list of output variables
            if (matches.find()) {
                processedInput = processedInput + INPUT_PARAMETER_DELIMITER + outputVariables.get(matches.group(1));
            } else if (arg.contains(INPUT_VARIABLE_START_DELIMITER)) {
                processedInput = processedInput + processVariableInputFromDataFile(arg, dataReader);
            } else {
                processedInput = processedInput + INPUT_PARAMETER_DELIMITER + arg;
            }
        }
        // Remove the extraneous delimiter strings from the final string before returning the value
        return processedInput.substring(INPUT_PARAMETER_DELIMITER.length());
    }

    /**
     * Will process each of the input arguments to replace specified input variables with data file variable values.
     *
     * @param input The input string passed in from the test sheet.
     */
    public static String processVariableInputFromDataFile(String input, DataReader repositoryData) {
        String processedInput = "";
        String[] argsVar = input.split(ESCAPED_INPUT_VARIABLE_START_DELIMITER);
        for (String var : argsVar) {
            if (var.contains(INPUT_VARIABLE_END_DELIMITER)) {
                String variable = var.substring(0, var.indexOf(INPUT_VARIABLE_END_DELIMITER));
                if (variable.toLowerCase().contains("random")) {
                    variable = stringOfRandomNumbers(variable);
                    String endVar = var.substring(var.indexOf(INPUT_VARIABLE_END_DELIMITER)).replace(INPUT_VARIABLE_END_DELIMITER, "");
                    processedInput = processedInput + variable + endVar;
                } else {
                    String endVar = var.substring(var.indexOf(INPUT_VARIABLE_END_DELIMITER)).replace(INPUT_VARIABLE_END_DELIMITER, "");
                    processedInput = processedInput + repositoryData.getValueForEnvironment(variable) + endVar;
                }
            } else {
                processedInput = processedInput + var;
            }
        }
        return INPUT_PARAMETER_DELIMITER + processedInput;
    }

    /**
     * this method generates a number of N digits all random.
     *
     * @param random_n .
     */
    private static String stringOfRandomNumbers(String random_n) {
        if (random_n.contains("_")) {
            String[] argsVar = random_n.split("_");
            String value = "";
            int i = 0;

            while (i < Integer.parseInt(argsVar[1])) {
                Random r = new Random();
                value += r.nextInt(10);
                i++;
            }
            return value;
        } else {
            Reporter.log("Unable to determine the length of the variable. Please, check the input format. ${Random N} where N must be a number");
            throw new IllegalArgumentException("Unable to determine the length of the variable. Please, check the input format. ${Random N} where N must be a number");
        }
    }

    /**
     * Will set the screenshot level for the current test run.
     *
     * @param screenshotLogLevel The string needed to represent the desired screenshot log level.
     */
    public static void setScreenshotLogLevel(String screenshotLogLevel) {
        switch (screenshotLogLevel.toLowerCase()) {
            case "onallsteps":
            case "onall":
            case "allsteps":
            case "all":
                screenShotOnAllSteps = true;
                break;
            case "onpasssteps":
            case "onpass":
            case "passsteps":
            case "pass":
                screenShotOnPass = true;
        }
    }

    /**
     * Will take a screenshot and add it to the testng report.
     */
    public static void addScreenshotToReport(WebDriver driver) {
        // Do not take a screenshot if the driver is HtmlUnit. (Breaks test run)
        if (!(driver instanceof HtmlUnitDriver)) {
            String timeStamp = TIMESTAMP_FORMAT.format(new Timestamp(System.currentTimeMillis()));
            String screenshotName = timeStamp + Thread.currentThread().getId();

            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                File finalScreenshotLocation = new File(SCREENSHOT_PATH + screenshotName + ".png");
                try {
                    FileUtils.copyFile(screenshot, finalScreenshotLocation);
                    Reporter.log("<a href=" + REPORT_SCREENSHOT_PATH + screenshotName + ".png><img src='" + REPORT_SCREENSHOT_PATH + screenshotName + ".png" + "' height='" + DEFAULT_SCREENSHOT_HEIGHT + "' width='" + DEFAULT_SCREENSHOT_WIDTH + "' /></a><br><br>");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Will determine whether or not the passed in string is a numeric value.
     *
     * @param str The string that needs to be determined.
     * @return boolean The boolean signifying whether or not the string is numeric.
     */
    public static boolean isNumeric(String str) {
        if (str == null)
            return false;
        for (char c : str.toCharArray())
            if (c < '0' || c > '9')
                return false;
        return true;
    }

    public static StringBuilder removeBlankSpace(StringBuilder stringBuilder) {
        int j = 0;
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (!Character.isWhitespace(stringBuilder.charAt(i))) {
                stringBuilder.setCharAt(j++, stringBuilder.charAt(i));
            }
        }
        stringBuilder.delete(j, stringBuilder.length());
        return stringBuilder;
    }

}