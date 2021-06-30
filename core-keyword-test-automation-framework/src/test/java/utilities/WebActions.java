package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import javax.mail.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebActions extends CommonFunctions {

    private static final String ESCAPED_INPUT_PARAMETER_DELIMITER = "\\|\\|";

    public static final String SOURCE_CODE_PATH = "src/test/resources/htmlSource/";

    // HTML object types
    public static final String SELECT_TYPE = "select";
    public static final String CHECKBOX_TYPE = "checkbox";
    public static final String RADIO_TYPE = "radio";
    public static final String TEXT_INPUT_TYPE = "text";
    public static final String TABLE_TYPE = "table";

    /**
     * Implementation of the "exit" keyword action.
     * Will exit all browser windows.
     */
    protected static void exit(Driver driver) {
        driver.closeAllWindows();
    }
    
    protected static void selectByIndex(WebElement object, String input) {
    	int index;
		try {
			index = Integer.parseInt(input);
			System.out.println("Indice es:"+index);
		} catch (Exception e) {
			index = 0;
			System.out.println("Hubo un error");
		}
		new Select(object).selectByIndex(index);
    }

    /**
     * Implementation of the "input" keyword action.
     * Will handle the different input types based on the type of web element that is being interacted with.
     *
     * @param object     the WebElement we will be interacting with
     * @param objectType the type of WebElement we will be interacting with
     * @param input      the value we will need to input into the WebElement
     */
    protected static void input(WebElement object, String objectType, String input) {

        if (input != null) {
            // handle check box selection
            if (input.equals("ON")) {
                if (!object.isSelected()) {
                    object.click();
                }
                return;
            } else if (input.equals("OFF")) {
                if (object.isSelected()) {
                    object.click();
                }
                return;
            }

            if (objectType.equalsIgnoreCase(RADIO_TYPE)) {
                if (!object.isSelected()) {
                    object.click();
                }
                return;
            }

            //handle select drop down
            if (objectType.equalsIgnoreCase(SELECT_TYPE)) {

                Select selectObject = new Select(object);

                String[] args = input.toLowerCase().split(ESCAPED_INPUT_PARAMETER_DELIMITER);

                if (args.length > 1 && args[0].trim().equals("bypartialtext")){
                /*  Dropdown selection by partial text.
                  Requirements: Two arguments are needed.
                  First argument must be 'partialtext'. Second and next arguments must be the text to search on the dropdown option
                  All arguments must be found in order to select the evaluated option.

                  Example 1: partialtext||argument-1
                  Example 2: partialtext||argument 1||argument 2
                */
                    String optionText;
                    int numberOfOptionsFound = 0;

                    // Obtain the dropdown options
                    List <WebElement> options = selectObject.getOptions();

                    for(int o = 0; o < options.size()-1; o++){ //int o -> options
                        numberOfOptionsFound = 0; // Counter for the number of options found. Set to 0 for every evaluated option.

                        optionText = options.get(o).getText().toLowerCase().trim();

                        // Loop through arguments options(starting from the second argument). Note: 1st argument is 'bypartialtext'
                        for (int a = 1; a <= args.length - 1; a++) { // int a -> arguments
                            if (optionText.contains(args[a].toLowerCase())) {
                                numberOfOptionsFound++;
                            }
                            else{ break; } // Break as soon as an option does not match.
                        }
                        if (numberOfOptionsFound == args.length - 1) { // Verify if all the arguments were found
                            selectObject.selectByIndex(o);
                            return;
                        }
                    }
                    if(numberOfOptionsFound == 0){ // Report and Fail when no option matching all arguments was found.
                        Reporter.log("No option was found for partial input of: " + input+ "<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                        Assert.fail("No option was found for partial input of: " + input);
                    }
                } // END-by-partial-text
                else{
                    selectObject.selectByVisibleText(input);
                    return;
                }
            }

            // handle text input
            //handle clear based on type
            if (objectType.equals(TEXT_INPUT_TYPE)) {
                object.clear();
            }
            object.sendKeys(input);
        }
    }

    /**
     * Perform the input action only if the specified object exists.
     *
     * @param object     The WebElement we will be interacting with.
     * @param objectType The type of WebElement we will be interacting with.
     * @param input      The value we will need to input into the WebElement.
     */
    protected static void inputIfExists(WebElement object, String objectType, String input) {
        try {
            if (object != null && object.isDisplayed()) {
                WebActions.input(object, objectType, input);
            }else{
                Reporter.log("Object does not exist.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
            }
        }catch(Exception ex){
            Reporter.log("Unexpected exception when checking for object.  Please review feature file.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
            ex.printStackTrace();
            Assert.fail("Unexpected exception when checking for object.  Please review feature file.");
        }
    }

    /**
     * Perform the input action only if the specified object exists and send ENTER key.
     *
     * @param object     The WebElement we will be interacting with.
     * @param objectType The type of WebElement we will be interacting with.
     * @param input      The value we will need to input into the WebElement.
     */
    protected static void inputIfExistsAndPressEnter(WebElement object, String objectType, String input) {
        if (object != null) {
            WebActions.inputIfExists(object, objectType, input);
            object.sendKeys(Keys.ENTER);
        }
    }

    /**
     * Implementation of the "inputDateOnCalendar" keyword action.
     * Will handle the different input types based on the type of web element that is being interacted with.
     *
     * @param object     the WebElement we will be interacting with
     * @param objectType the type of WebElement we will be interacting with
     * @param input      the value we will need to input into the WebElement with the format xx/xx/xxxx month/day/year
     */
    protected static void inputDateOnCalendar(WebElement object, String objectType, String input) {

        String date[] = input.split("/");
        int month = Integer.parseInt(date[0]) - 1;
        int day = Integer.parseInt(date[1]) - 1;
        String year = date[2];

        WebElement yearSelector = object.findElement(By.className("ui-datepicker-year"));
        Select sm = new Select(yearSelector);
        sm.selectByValue(year);

        WebElement monthSelector = object.findElement(By.className("ui-datepicker-month"));
        new Select(monthSelector).selectByValue(String.valueOf(month));

        List<WebElement> numbersDay = object.findElements(By.xpath("//a[@class='ui-state-default']"));
        numbersDay.get(day).click();
    }

    /**
     * Implementation of the "click" keyword action.
     * @param object the WebElement we will be interacting with
     * @param objectType the type of WebElement we will be interacting with
     */
    protected static void click(WebDriver driver, WebElement object, String objectType) {
        try {
            if(objectType.equals(TABLE_TYPE)){
                List<WebElement> rows = object.findElements(By.cssSelector("tr"));
                WebElement row;
                //set table to specific row, or first row if none specified
                if(tableRowNumber != 0){
                    row = rows.get(tableRowNumber);
                }
                else{
                    row = rows.get(1);
                }
                Actions actions = new Actions(driver);
                actions.moveToElement(row).click().perform();
            }
            else{
                object.click();
            }
        }
        // If the normal click function does not work, then attempt the click via javascript execution
        catch(WebDriverException e){
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", object);
        }
    }

    protected static void clickOnElementTable(WebDriver driver, WebElement table, String input) {

        try {
            String[] args = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);
            List<WebElement> tableRows;
            String searchValue = "";
            String verificationType = "";
            if (args.length > 1) {
                verificationType = args[0];
                searchValue = args[1];
            }
            List<WebElement> rows = table.findElements(By.cssSelector("tr"));
            WebElement row;

            row = rows.get(tableRowNumber);

            tableRows = row.findElements(By.tagName(verificationType));
            for (int rowIndex = 0; rowIndex < tableRows.size(); rowIndex++) {

                String cellText2 = tableRows.get(rowIndex).getText();
                if (cellText2.equals(searchValue)) {

                    click(driver, tableRows.get(rowIndex), "");

                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Implementation of the "click" keyword action using JavaScript.
     *
     * @param object the WebElement we will be interacting with
     */
    protected static void clickJS(WebDriver driver, WebElement object) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", object);
    }

    /**
     * Perform the click action only if the specified object exists.
     * @param object the WebElement we will be interacting with.
     * @param objectType the type of WebElement we will be interacting with.
     */
    protected static void clickIfExists(WebDriver driver, WebElement object, String objectType) {
        if(object != null){
            click(driver, object, objectType);
        }
    }

    /**
     *
     * @param driver
     * @param table
     * @param input
     */
    protected static void clickOnCheckboxInTable(WebDriver driver, WebElement table, String input){
        String[] args = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);
        List<WebElement> tableRows;
        String searchValue = "";
        String verificationType="";
        if(args.length > 1){
            verificationType = args[0];
            searchValue = args[1];
        }

        tableRows = table.findElements(By.tagName(verificationType));
        for(int rowIndex = 0; rowIndex < tableRows.size(); rowIndex++){

            String cellText = tableRows.get(rowIndex).getAttribute("value");
            if(cellText.equals(searchValue)){

                click(driver, tableRows.get(rowIndex), "");

            }
        }
    }

    /**
     * Implementation of the "hover" keyword action.
     * @param driver     The test driver that will be used to execute the action.
     * @param object     the WebElement we will be interacting with
     * @param objectType the type of WebElement we will be interacting with
     */
    protected static void hover(WebDriver driver, WebElement object, String objectType){
        if(object != null){
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                    "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                    "arguments[0].dispatchEvent(evObj);";
            executor.executeScript(javaScript, object);
        }
    }

    /**
     * Implentation of the "getValue" keyword action.
     * Will save the value of web element to a hastable of output values.
     *
     * @param object     the WebElement we will be interacting with.
     * @param objectType the type of WebElement we will be interacting with.
     * @param input      the name of the property type to get the value from.
     * @param output     the name of the variable we will be saving the value to.
     */
    protected static void getValue(WebElement object, String objectType, String input, String output, Hashtable<String, String> outputVariables) {
        outputVariables.put(output, object.getAttribute(input));
    }

    /**
     * Implementation of the "getText" keyword action.
     * Will save the visible inner text of web element to a hashtable of output values.
     *
     * @param object the WebElement we will be interacting with.
     * @param output the name of the variable we will be saving the value to.
     */
    protected static void getText(WebElement object, String output, Hashtable<String, String> outputVariables) {
        outputVariables.put(output, object.getText().trim().replaceAll("\n", ""));
        System.out.println("[GetText] Text to be saved into [" + output + "]: \'" + object.getText().trim().replaceAll("\n", "") + "\'");
    }

    /**
     * Will get a value from an email via pop3s protocol.
     *
     * @param input     The parameters needed to login to the email account and to get the value from the email.
     * @param outputKey The name of the variable we will be saving the value to.
     */
    protected static void getEmail(String input, String outputKey, Hashtable<String, String> outputVariables) {
        String[] args = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);

        //Setting up configurations for the email connection to the Google SMTP server using TLS
        Properties props = new Properties();
        props.put("mail.pop3.host", "pop.gmail.com");
        props.put("mail.pop3.starttls.enable", "true");
        props.put("mail.pop3.port", "995");

        Session session = Session.getInstance(props);
        try {
            Store store = session.getStore("pop3s");
            store.connect("pop.gmail.com", args[0], args[1]);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();
            //Get the first message
            Message message = messages[0];
            String messageText = message.getContent().toString();

            if (args[2].equalsIgnoreCase("regex")) {
                if (args.length > 3) {
                    Pattern pattern = Pattern.compile(args[3]);
                    Matcher matches = pattern.matcher(messageText);
                    matches.find();
                    outputVariables.put(outputKey, matches.group(1));
                } else {
                    outputVariables.put(outputKey, messageText);
                }
            } else {
                outputVariables.put(outputKey, messageText);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Will click in the checkobx if is unchecked.
     * @param object
     */
    protected static void clickIfCheckboxIsUnselected(WebElement object) {
        if (!object.isSelected()) {
            object.click();
        }
    }

    /**
     * Will save the state of checkbox to a hash table of output values.
     * @param object the WebElement we will be interacting with
     * @param output the name of the variable we will be saving the value to.
     */
    protected static void getCheckboxState(WebElement object, String output, Hashtable<String, String> outputVariables) {
        Boolean state = object.isSelected();
        outputVariables.put(output, String.valueOf(state));
    }

    /**
     * Implementation of the "navigateTo" keyword action.
     *
     * @param url the url of the page to navigate to.
     */
    protected static void navigateTo(WebDriver driver, String url) {
        driver.get(url);
    }

    /**
     * Implementation of the "navigateTo" keyword action.
     *
     * @param url the url of the page to navigate to.
     */
    protected static void goToUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    /**
     * Will open a new browser tab and navigate that tab to the specified URL, if one is specified.
     *
     * @param url the url of the page to navigate to.
     */
    protected static void openNewTab(WebDriver driver, String url) {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        switchTab(driver, "newtab");
        if (!url.isEmpty()) {
            navigateTo(driver, url);
        }
    }

    /**
     * Will select option of a element by visble text, value or index.
     * @param driver
     * @param object web element object.
     * @param input need two parameters separated for a ||.
     */
    protected static void selectElement(WebDriver driver, WebElement object, String input) {
        if (object != null) {
            Select select = new Select(object);
            String[] args = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);
            String verificationType = args[0];
            String value = args[1];

            switch (verificationType.toLowerCase()) {
                case "byvisibletext":
                    select.selectByVisibleText(value);
                    break;
                case "byvalue":
                    select.selectByValue(value);
                    break;
                case "byindex":
                    select.selectByIndex(Integer.valueOf(value));
                    break;
                default:
                    Reporter.log("No verification arguments supplied to the SelectElement action.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("No verification arguments supplied to the SelectElement action.<br>");
            }
        } else {
            Reporter.log("No element supplied. Must specify a web element", VERIFICATION_FAILURE_LOG_LEVEL);
            addScreenshotToReport(driver);
            Assert.fail("No element supplied. Must specify a web element");
        }
    }

    /**
     * Will do a wait for an object to appear, if one is specified, otherwise will do a hard wait during test execution.
     *
     * @param object The object to wait for to appear.
     * @param input  The type of verification to perform, wait time and possibly a list of values to use for the verification.
     */
    protected static void waitFor(WebDriver driver, WebElement object, String input, By locator) throws Exception {
        int waitTimeInSeconds = 5;

        if (object != null) {
            String[] args = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);
            String verificationType = args[0];
            String time = "";
            String condition = "";

            if (args.length > 1) {
                time = args[1];
                waitTimeInSeconds = Integer.parseInt(time);
                if (args.length > 2) {
                    condition = args[2];
                }
            }

            switch (verificationType.toLowerCase()) {
                case "visibilityof":
                    new WebDriverWait(driver, waitTimeInSeconds).until(ExpectedConditions.visibilityOf(object));
                    break;
                case "invisibilityof":
                    new WebDriverWait(driver, waitTimeInSeconds).until(ExpectedConditions.invisibilityOf(object));
                    break;
                case "elementtobeclickable":
                    new WebDriverWait(driver, waitTimeInSeconds).until(ExpectedConditions.elementToBeClickable(object));
                    break;
                case "elementtobeselected":
                    new WebDriverWait(driver, waitTimeInSeconds).until(ExpectedConditions.elementToBeSelected(object));
                    break;
                case "alertispresent":
                    new WebDriverWait(driver, waitTimeInSeconds).until(ExpectedConditions.alertIsPresent());
                    break;
                case "frametobeavailableandswitchtoit":
                    new WebDriverWait(driver, waitTimeInSeconds).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(object));
                    break;
                case "texttobepresentinelement":
                    new WebDriverWait(driver, waitTimeInSeconds).until(ExpectedConditions.textToBePresentInElement(object, condition));
                    break;
                case "texttobepresentinelementlocated":
                    new WebDriverWait(driver, waitTimeInSeconds).until(ExpectedConditions.textToBePresentInElementLocated(locator, condition));
                    break;
                case "texttobepresentinelementvalue":
                    new WebDriverWait(driver, waitTimeInSeconds).until(ExpectedConditions.textToBePresentInElementValue(object, condition));
                    break;
                default:
                    Reporter.log("No verification arguments supplied to the WaitFor action.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("No verification arguments supplied to the WaitFor action.<br>");
            }

        } else if (input.toLowerCase().contains("implicit")){
            String[] args = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);
            waitTimeInSeconds = Integer.valueOf(args[1]);
            Thread.sleep(waitTimeInSeconds * 1000);
        } else {
            throw new Exception("Wait For: Cannot find element");
        }
    }

    /**
     * Will perform a verification on the passed in object, based on the type of verification passed in as input.
     * Will log to the test report based on the currently set verbosity.
     *
     * @param object     The web element to perform the verification upon.
     * @param objectType The type of the web element.
     * @param input      The type of verification to perform and possibly a list of values to use for the verification.
     */
    protected static void verify(WebDriver driver, WebElement object, String objectType, String input){
        String[] args = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);
        String verificationType = args[0];
        String verificationValue = "";
        String attributeType;
        String attributeValue;
        String cssAttribute;
        String actualValue;
        int rowValue;
        String colValue;

        if(args.length > 1){
            verificationValue = args[1];
        }

        // Get the value from the object to verify against based on th type of the object
        String objectValue= "";
        if (object != null){
            switch (objectType){
                case CHECKBOX_TYPE:
                case RADIO_TYPE:
                case TEXT_INPUT_TYPE:
                    objectValue = object.getAttribute("value");
                    break;
                case SELECT_TYPE:
                    objectValue = new Select(object).getFirstSelectedOption().getText();
                    break;
                case TABLE_TYPE:
                    // If there are 2 table lookup inputs, then use those cell values
                    // Otherwise, use the table number set via the settablerow keyword
                    if(args.length > 3){
                        objectValue = getCellText(object, Integer.parseInt(args[2]), args[3]);
                    }
                    else{
                        //TODO need additional error checking for when args are not supplied
                        objectValue = getCellText(object, tableRowNumber+1, args[2]);
                    }
                    break;
                default:
                    objectValue = object.getText();
            }
        }

        switch (verificationType.toLowerCase()){
            case "exists":
                if(object.isDisplayed()){
                    Reporter.log("Expected object '"+objectValue+"'is displayed.<br>", VERIFICATION_PASS_LOG_LEVEL);
                }
                else{
                    Reporter.log("Expected object '"+objectValue+"' is not displayed.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("Expected object '"+objectValue+"' is not displayed.<br>");
                }
                break;
            case "doesnotexist":
                if(object == null || !object.isDisplayed()){
                    Reporter.log("Unexpected object '"+objectValue+"'is not displayed.<br>", VERIFICATION_PASS_LOG_LEVEL);
                }
                else{
                    Reporter.log("Unexpected object '"+objectValue+"' is displayed.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("Unexpected object '"+objectValue+"' is displayed.<br>");
                }
                break;
            case "is":
                if(objectValue.equals(verificationValue)){
                    Reporter.log("The object has the expected text '"+verificationValue+"'.<br>", VERIFICATION_PASS_LOG_LEVEL);
                }
                else {
                    Reporter.log("The object does not have the expected text '"+verificationValue+"', instead the object text is '"+objectValue+"'.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("The object does not have the expected text '"+verificationValue+"', instead the object text is '"+objectValue+"'.<br>");
                }
                break;
            case "isnot":
                if(!objectValue.equals(verificationValue)){
                    Reporter.log("The object does not have the unexpected text '"+verificationValue+"'.<br>", VERIFICATION_PASS_LOG_LEVEL);
                }
                else {
                    Reporter.log("The object has the unexpected text '"+verificationValue+"', the object text is '"+objectValue+"'.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("The object has the unexpected text '"+verificationValue+"', the object text is '"+objectValue+"'.<br>");
                }
                break;
            case "textequal":
                String currentText = args[2];
                if(verificationValue.equals(currentText)){
                    Reporter.log("The object has the expected text '"+currentText+"'.<br>", VERIFICATION_PASS_LOG_LEVEL);
                }
                else {
                    Reporter.log("The object does not have the expected text '"+currentText+"', instead the object text is '"+verificationValue+"'.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("The object does not have the expected text '"+currentText+"', instead the object text is '"+verificationValue+"'.<br>");
                }
                break;
            case "contains":
                if (args.length > 2) {
                    objectValue = args[2];
                }

                if(objectValue.contains(verificationValue)){
                    Reporter.log("The object contains the expected text '"+verificationValue+"'.<br>", VERIFICATION_PASS_LOG_LEVEL);
                }
                else{
                    Reporter.log("The object does not contain the expected text '"+verificationValue+", instead the object text is '"+objectValue+"'.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("The object does not contain the expected text '"+verificationValue+", instead the object text is '"+objectValue+"'.<br>");
                }
                break;
            case "doesnotcontain":
                if(!objectValue.contains(verificationValue)){
                    Reporter.log("The object does not contain the unexpected text '"+verificationValue+"'.<br>", VERIFICATION_PASS_LOG_LEVEL);
                }
                else{
                    Reporter.log("The object contains the unexpected text '"+verificationValue+"', the object text is '"+objectValue+"'.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("The object contains the unexpected text '"+verificationValue+"', the object text is '"+objectValue+"'.<br>");
                }
                break;
            case "url":
                if(driver.getCurrentUrl().equals(verificationValue)){
                    Reporter.log("The expected current URL '"+verificationValue+"' is the current URL.<br>", VERIFICATION_PASS_LOG_LEVEL);
                }
                else{
                    Reporter.log("The expected current URL '"+verificationValue+"' is not the current URL, instead the URL is '"+driver.getCurrentUrl()+"'.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("The expected current URL '"+verificationValue+"' is not the current URL, instead the URL is '"+driver.getCurrentUrl()+"'.<br>");
                }
                break;
            case "urlcontains":
                if(driver.getCurrentUrl().contains(verificationValue)){
                    Reporter.log("The current URL contains the expected string '"+verificationValue+"'.<br>", VERIFICATION_PASS_LOG_LEVEL);
                }
                else{
                    Reporter.log("The current URL does not contains the string expected '"+verificationValue+"', instead the text displayed in the URL is '"+driver.getCurrentUrl()+"'.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("The current URL does not contains the string expected '"+verificationValue+"', instead the text displayed in the URL is '"+driver.getCurrentUrl()+"'.");
                }
                break;
            case "titlepageis":
                if(driver.getTitle().equals(verificationValue)){
                    Reporter.log("The expected current Title '"+verificationValue+"' is the current Title.<br>", VERIFICATION_PASS_LOG_LEVEL);
                }
                else{
                    Reporter.log("The expected current Title '"+verificationValue+"' is not the current Title, instead the Title is '"+driver.getTitle()+"'.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("The expected current Title '"+verificationValue+"' is not the current Title, instead the Title is '"+driver.getTitle()+"'.");
                }
                break;
            case "rowcount":
                if(getRowCount(object) == Integer.parseInt(verificationValue)){
                    Reporter.log("The expected row count '"+verificationValue+"' is the current row count.<br>", VERIFICATION_PASS_LOG_LEVEL);
                }
                else{
                    Reporter.log("The expected row count '"+verificationValue+"' is not the table's row count, instead the row count is '"+getRowCount(object)+"'.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                    addScreenshotToReport(driver);
                    Assert.fail("The expected row count '"+verificationValue+"' is not the table's row count, instead the row count is '"+getRowCount(object)+"'.<br>");
                }
                break;
            case "attributeis":
                //Validate that we have the right number of arguments.  If not, fail and exit the function.
                if(args.length > 2) {
                    if(args.length == 5 && objectType == TABLE_TYPE){
                        //If the user has listed the row and column values and the object is a table type, verify the cell
                        //attribute
                        attributeType = args[1];
                        rowValue = Integer.parseInt(args[3]);
                        colValue = args[4];
                        actualValue = getCellAttribute(object, rowValue, colValue, attributeType);
                        verificationValue = args[2];
                    }else {
                        attributeType = args[1];
                        actualValue = object.getAttribute(attributeType);
                        verificationValue = args[2];
                    }
                }
                else {
                    Assert.fail("For the verification type AttributeIs, THREE arguments are required.  Only [" + args.length + "] were given.");
                    return;
                }

                if(actualValue.equals(verificationValue)){
                    Reporter.log("The given attribute[" + attributeType +"] with the  expected value[" + verificationValue + "] matches the actual value[" + actualValue + "]");
                }else{
                    Reporter.log("Failed to match the actual attribute value[" + actualValue + "] against the expected attribute[" + attributeType + "] value[" + verificationValue + "]");
                    Assert.fail("Failed to match the actual attribute value[" + actualValue + "] against the expected attribute[" + attributeType + "] value[" + verificationValue + "]<br>");
                }
                break;
            case "attributeisnot":
                //Validate that we have the right number of arguments.  Fail and exist if we don't.
                if(args.length > 2) {
                    if(args.length == 5 && objectType == TABLE_TYPE){
                        //If the user has listed the row and column values and the object is a table type, verify the cell
                        //attribute
                        attributeType = args[1];
                        rowValue = Integer.parseInt(args[3]);
                        colValue = args[4];
                        actualValue = getCellAttribute(object, rowValue, colValue, attributeType);
                        verificationValue = args[2];
                    }else {
                        attributeType = args[1];
                        actualValue = object.getAttribute(attributeType);
                        verificationValue = args[2];
                    }
                }
                else {
                    Assert.fail("For the verification type AttributeIs, THREE arguments are required.  Only [" + args.length + "] were given.");
                    return;
                }
                if(!object.getAttribute(attributeType).equals(verificationValue)){
                    Reporter.log("The given attribute[" + attributeType +"] with the expected value[" + verificationValue + "] doesn't matches the actual value[" + actualValue + "] as expected.");
                }
                else {
                    Reporter.log("Unexpectedly matched the expected value[" + verificationValue + "] against the actual value[" + actualValue + "] for the attribute[" + attributeType + "].");
                    Assert.fail("Unexpectedly matched the expected value[" + verificationValue + "] against the actual value[" + actualValue + "] for the attribute[" + attributeType + "].");
                }
                break;
            case "attributecontains":
                //Validate that we have the right number of arguments.  If not, fail and exit the function.
                if(args.length > 2) {
                    if(args.length == 5 && objectType == TABLE_TYPE){
                        //If the user has listed the row and column values and the object is a table type, verify the cell
                        //attribute
                        attributeType = args[1];
                        rowValue = Integer.parseInt(args[3]);
                        colValue = args[4];
                        actualValue = getCellAttribute(object, rowValue, colValue, attributeType);
                        verificationValue = args[2];
                    }else {
                        attributeType = args[1];
                        actualValue = object.getAttribute(attributeType);
                        verificationValue = args[2];
                    }
                }
                else {
                    Assert.fail("For the verification type AttributeIs, THREE arguments are required.  Only [" + args.length + "] were given.");
                    return;
                }

                if(actualValue.contains(verificationValue)){
                    Reporter.log("The given attribute[" + attributeType +"] with the  expected value[" + verificationValue + "] matches the actual value[" + actualValue + "]");
                }else{
                    Reporter.log("Failed to match the actual attribute value[" + actualValue + "] against the expected attribute[" + attributeType + "] value[" + verificationValue + "]");
                    Assert.fail("Failed to match the actual attribute value[" + actualValue + "] against the expected attribute[" + attributeType + "] value[" + verificationValue + "]");
                }
                break;
            case "attributeisempty":
                //Validate that we have the right number of arguments.  If not, fail and exit the function.
                if(args.length > 1) {
                    if(args.length == 4 && objectType == TABLE_TYPE){
                        //If the user has listed the row and column values and the object is a table type, verify the cell
                        //attribute
                        attributeType = args[1];
                        rowValue = Integer.parseInt(args[2]);
                        colValue = args[3];
                        actualValue = getCellAttribute(object, rowValue, colValue, attributeType);
                        //verificationValue = args[2];
                    }else {
                        attributeType = args[1];
                        actualValue = object.getAttribute(attributeType);
                        //verificationValue = args[2];
                    }
                }
                else {
                    Assert.fail("For the verification type AttributeIsEmpty, TWO arguments are required.  Only [" + args.length + "] were given.");
                    return;
                }

                if(actualValue.isEmpty()){
                    Reporter.log("The given attribute is Empty.");
                }else{
                    Reporter.log("Failed to match the actual attribute value[" + actualValue + "] against the expected attribute[" + attributeType + "] should be empty");
                    Assert.fail("Failed to match the actual attribute value[" + actualValue + "] against the expected attribute[" + attributeType + "] should be empty <br>");
                }
                break;
            case "cssvalueis":
                //Validate that we have the right number of arguments.  If not, fail and exit the function.
                if(args.length > 2) {
                    cssAttribute = args[1];
                    if(cssAttribute.contains("color")){
                        actualValue = Color.fromString(object.getCssValue(cssAttribute)).asHex();
                    }else{
                        actualValue = object.getCssValue(cssAttribute);
                    }
                    verificationValue = args[2];
                }
                else {
                    Assert.fail("For the verification type CssValueIs, THREE arguments are required.  Only [" + args.length + "] were given.<br>");
                    return;
                }

                if(actualValue.contains(verificationValue)){
                    Reporter.log("The given cssAttribute[" + cssAttribute +"] with the  expected value[" + verificationValue + "] matches the actual value[" + actualValue + "]");
                }else{
                    Reporter.log("Failed to match the actual CSS attribute value[" + actualValue + "] against the expected attribute[" + cssAttribute + "] value[" + verificationValue + "]");
                    Assert.fail("Failed to match the actual CSS attribute value[" + actualValue + "] against the expected attribute[" + cssAttribute + "] value[" + verificationValue + "].<br>");
                }
                break;
            default:
                Reporter.log("No verification arguments supplied to the Verify action.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
                addScreenshotToReport(driver);
                Assert.fail("No verification arguments supplied to the Verify action.<br>");
        }
        // If the test is already set to take a screenshot on all steps, there is no need add an additional screenshot.
        if(screenShotOnPass){
            addScreenshotToReport(driver);
        }
    }

    /**
     * Will get the number of table rows.
     *
     * @param table The table table we need to interact with
     */
    private static int getRowCount(WebElement table) {
        return table.findElements(By.tagName("tr")).size();
    }

    /**
     * Will get the text in the target cell of the passed in table object.
     *
     * @param tableRoot   The table object.
     * @param rowIndex    The target cell's row number.
     * @param columnIndex The target cell's column number.
     * @return String The text inside of the target table cell.
     */
    private static String getCellText(WebElement tableRoot, int rowIndex, int columnIndex) {
        return tableRoot.findElement(By.xpath("./tbody/tr[" + rowIndex + "]/td[" + columnIndex + "]")).getText();
    }

    /**
     * Will get the text in the target cell of the passed in table object.
     *
     * @param tableRoot  The table object.
     * @param rowIndex   The target cell's row number.
     * @param columnName The target cell's column header name.
     * @return String The text inside of the target table cell.
     */
    private static String getCellText(WebElement tableRoot, int rowIndex, String columnName) {
        // If column input value is a number, then convert the column into a number and get the cell via column number instead of via name
        if (isNumeric(columnName)) {
            return getCellText(tableRoot, rowIndex, Integer.parseInt(columnName));
        } else {
            // get all table header elements
            List<WebElement> tableHeaders = tableRoot.findElements(By.xpath("//th"));

            // find the column number that corresponds to the header name of the column
            for (int columnIndex = 0; columnIndex < tableHeaders.size(); columnIndex++) {
                if (tableHeaders.get(columnIndex).getText().equals(columnName)) {
                    return getCellText(tableRoot, rowIndex, columnIndex + 1);
                }
            }
        }
        return null;
    }

    /**
     * Function Name: getCell
     * Author: Khary Popplewell
     * Created On: 24/05/2019
     * Description:
     *
     * @param myTable     - WebElement object with a class type "table"
     * @param rowIndex    -  integer representing the row you want to access
     * @param columnIndex -  String (or a number) representing the column you want to access
     * @return WebElement object representing the cell in the row of the column identified by the
     * columnIndex
     */
    private static WebElement getCell(WebElement myTable, int rowIndex, String columnIndex) {
        //Define necessary variables
        List<WebElement> tableHeaders = myTable.findElements(By.xpath("//th"));
        List<WebElement> tableRows = myTable.findElements(By.xpath("//tr"));
        WebElement myCell;
        int maxColumns = tableHeaders.size();
        int maxRows = tableRows.size();
        int columnNumber = -1;

        //Error check arguments
        if (!isNumeric(columnIndex)) {

            //Get the numerical index of the given string
            for (int cIndex = 0; cIndex < maxColumns; cIndex++) {
                if (tableHeaders.get(cIndex).getText().equals(cIndex)) {
                    cIndex = maxColumns + 1;
                    columnNumber = cIndex;
                }
            }

            //If we didn't find the column index, fail the test (can't move on if we can't see the object)
            if (columnNumber == -1) {
                Reporter.log("Failed to find the column[" + columnIndex + "] in the list of table headers[" + tableHeaders.toString() + "].");
                Assert.fail("Failed to find the column[" + columnIndex + "] in the list of table headers[" + tableHeaders.toString() + "].");
                return null;
            }
        } else {
            columnNumber = Integer.parseInt(columnIndex);
        }

        //  ->Verify that both the row and column indices are less than the max row/column respectively
        if (rowIndex >= maxRows) {
            Reporter.log("Given row[" + rowIndex + "] is greater than the number of available rows[" + maxRows + "].  Please review feature file.");
            Assert.fail("Given row[" + rowIndex + "] is greater than the number of available rows[" + maxRows + "].  Please review feature file.");
            return null;
        }
        if (columnNumber >= maxColumns) {
            Reporter.log("Given column[" + columnNumber + "] with columnName[" + columnIndex + "] is greater than the number of available columns[" + maxColumns + "].  Please review feature file.");
            Assert.fail("Given column[" + columnNumber + "] with columnName[" + columnIndex + "] is greater than the number of available columns[" + maxColumns + "].  Please review feature file.");
            return null;
        }

        //catch any exceptions that are thrown when attempting to access the cell and exit gracefully.
        try {
            myCell = tableRows.get(rowIndex).findElements(By.xpath("//td")).get(columnNumber);
        } catch (Exception ex) {
            Reporter.log("Found the cell coordinates[row:" + rowIndex + ", col:" + columnIndex + "] but the cell is not available as expected.");
            Assert.fail("Found the cell coordinates[row:" + rowIndex + ", col:" + columnIndex + "] but the cell is not available as expected.");
            ex.printStackTrace();
            return null;
        }
        //Return the webelement that represents the cell in the given (rowIndex, columnIndex) coordinates.
        return myCell;
    }

    /**
     * @param myTable     - WebElement object with a class type "table"
     * @param rowIndex    -  integer representing the row you want to access
     * @param columnIndex -  String (or a number) representing the column you want to access
     * @param myAttribute - Attribute that you want to get
     * @return The string representation of an attribute of a cell in a table
     */
    private static String getCellAttribute(WebElement myTable, int rowIndex, String columnIndex, String myAttribute) {
        WebElement myCell = getCell(myTable, rowIndex, columnIndex);
        String returnAttribute = "";

        //If we found the cell get the attribute of the cell
        if (myCell != null) {
            try {
                returnAttribute = myCell.getAttribute(myAttribute);
            } catch (Exception ex) {
                //If we can't access the attribute for some reason, fail the test and return nothing.
                Reporter.log("Attribute[" + myAttribute + "] is not available for the cell[" + rowIndex + ", " + columnIndex + "].");
                Assert.fail("Attribute[" + myAttribute + "] is not available for the cell[" + rowIndex + ", " + columnIndex + "].");
            }
        }
        return returnAttribute;
    }

    /**
     * Implementation of the "setTableRow" keyword action.
     * Will search the table for the row with all of the matching search inputs.
     * Will then set the global table row for use in other common functions.
     * @param table The table table we need to interact with
     * @param objectType the type of table we are interacting with
     * @param input the values we need to search for in a specific table row
     */
    public static void setTableRow(WebDriver driver, WebElement table, String objectType, String input){
        if(input != null){
            String[] searchValues = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);
            List<WebElement> tableRows;
            List<WebElement> rowCells;

            boolean allValuesFound = false;
            int matchingRow = -1;

            // to find a row, by having to click result and having to check a value use this format in input column of test

            // loop through each input value
            for(String searchValue : searchValues){
                tableRows = table.findElements(By.tagName("tr"));

                // loop through each row
                for(int rowIndex = 0; rowIndex < tableRows.size(); rowIndex++){
                    // The instruction `findElements(By.tagName("td"))` takes to long when no <td> element is found.
                    // `getAttribute("innerHTML")` executes faster. The new approach first validates that the String <td>
                    //      is inside the <tr> before performing the find By.tagName
                    if (tableRows.get(rowIndex).getAttribute("innerHTML").toLowerCase().contains("<td>")) {

                        rowCells = tableRows.get(rowIndex).findElements(By.tagName("td"));

                        // loop through each column in the row
                        for(int columnIndex = 0; columnIndex < rowCells.size(); columnIndex++){

                            // if cell text matches the current search value, check if all search values are inside of the row
                            String cellText = rowCells.get(columnIndex).getText();
                            if(cellText.contains(searchValue)){

                                // see if the matching row is the lowest matching row number
                                if(matchingRow != -1 && rowIndex < matchingRow ){
                                    matchingRow = rowIndex;
                                }

                                // if there more than one search parameter, check if all search values are inside the row
                                if (searchValues.length > 1){
                                    int numberOfValuesFound = 0;

                                    // loop through the row's cells again to see if all search values are in the row
                                    for(int cellIndex = 0; cellIndex < rowCells.size(); cellIndex++){

                                        // see if the cell text matches one of the passed in search values
                                        cellText = rowCells.get(cellIndex).getText();
                                        for(String searchFor : searchValues){
                                            if(cellText.contains(searchFor)){
                                                numberOfValuesFound++;
                                                // once all search values have been found in the row, finish the table search
                                                if(numberOfValuesFound == searchValues.length){
                                                    matchingRow = rowIndex;
                                                    allValuesFound = true;
                                                }
                                            }
                                            if(allValuesFound) break;
                                        }
                                        if(allValuesFound) break;
                                    }
                                }
                                else{
                                    // finish the table search
                                    matchingRow = rowIndex;
                                    allValuesFound = true;
                                }
                            }
                            if(allValuesFound) break;
                        }
                        if(allValuesFound) break;
                    }
                }
                if(allValuesFound) break;
            }
            tableRowNumber = matchingRow;
        }
    }
    /**
     * Closes the current browser tab.
     * Will then switch to the newest tab.
     * @param driver The test driver that will be used to execute the action.
     */
    protected static void closeTab(WebDriver driver) {
        driver.close();
        switchTab(driver, "newtab");
    }

    /**
     * Switches browser focus based on the value passed into the method.
     * Will switch newest tab if the newtab string is passed in.
     * Will switch to the tab based on number or the name of the tab if any other value is passed in.
     * @param input The value to determine which browser tab to switch to.
     */
    public static void switchTab(WebDriver driver, String input){
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if(!input.isEmpty()) {
            if (input.equalsIgnoreCase("newtab")) {
                int newestTabNumber = tabs.size() - 1;
                driver.switchTo().window((tabs.get(newestTabNumber)));
            } else {
                if (isNumeric(input)) {
                    driver.switchTo().window(tabs.get(Integer.parseInt(input)));
                } else {
                    driver.switchTo().window(tabs.get(tabs.indexOf(input)));
                }
            }
        }
        else{
            driver.switchTo().window(tabs.get(0));
        }
    }

    /**
     * Will switch driver focus to the specified iframe.
     * @param driver The driver to switch the frame on.
     * @param object the iframe web element.
     * @param input The frame to switch focus to. Could be frame name or frame number. With no input will switch to the first frame.
     */
    public static void switchFrame(WebDriver driver, WebElement object, String input) throws InterruptedException {

        if (object != null) {
            driver.switchTo().frame(object);
            Thread.sleep(1000);
        } else if (!input.isEmpty()) {
            if (isNumeric(input)) {
                driver.switchTo().frame(Integer.parseInt(input));
            } else {
                switch (input.toLowerCase()) {
                    case "active":
                    case "activeelement":
                        driver.switchTo().activeElement();
                        break;
                    case "parent":
                    case "parentframe":
                        driver.switchTo().parentFrame();
                        break;
                    case "default":
                    case "defaultcontent":
                        driver.switchTo().defaultContent();
                        break;
                    default:
                        driver.switchTo().frame(input);
                }
            }
        } else {
            int numberOfIFrames = driver.findElements(By.tagName("iframe")).size();
            if (numberOfIFrames > 0) {
                driver.switchTo().frame(0);
            }
        }
    }

    /**
     * Will handle browser alert messages that are not a part of the web page.
     * @param driver The test driver that will be used to execute the action.
     * @param input The command to execute upon the browser alert window.
     */
    public static void handleAlert(WebDriver driver, String input) {
        switch (input.toLowerCase()) {
            case "ok":
            case "accept":
                driver.switchTo().alert().accept();
                break;
            case "no":
            case "dismiss":
                driver.switchTo().alert().dismiss();
                break;
            default:
                driver.switchTo().alert().sendKeys(input);
        }
    }

    /**
     * Will delete the browser's cookies by name if specified, otherwise will delete all cookies.
     * @param input The name of the cookie(s) to be deleted.
     */
    protected static void deleteCookies(WebDriver driver, String input){
        if(input.isEmpty()){
            driver.manage().deleteAllCookies();
        }
        else {
            driver.manage().deleteCookieNamed(input);
        }
    }

    /**
     * Will simulate scrolling down a screen.
     * @param driver The test driver that will be used to execute the action.
     */
    protected static void scrollDown(WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollBy(0,500)");
    }

    // *** HTML Source Methods - May be deprecated

    /**
     * get html code of a webpage
     *
     * @param driver The test driver that will be used to execute the action.
     * @return String of the source code for the current page
     */
    public static String getHtmlSource(WebDriver driver) throws IOException{
        String pageSource = driver.getPageSource();

        if(!pageSource.isEmpty() && pageSource != null){
            return pageSource;
        }
        else{
            return "";
        }
    }

    /**
     * will get the save html code for the page
     * @param incomingFileName the name of the file to load
     * @return ArrayList of the saved html code line by line
     */
    public static List<String> getSavedHtmlSource(String incomingFileName)throws IOException{
        List<String> sourceCodeLines = new ArrayList<>();

        String fileName = SOURCE_CODE_PATH+incomingFileName;

        File file = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        while((line = bufferedReader.readLine()) != null){
            sourceCodeLines.add(line);
        }
        bufferedReader.close();
        return sourceCodeLines;
    }

    /**
     * will compare the code for the pages we are testing have not changed.
     * @param urlToVerify the page we need to verify
     */
    public static void verifySourceCode(String urlToVerify)throws  IOException{
	  /*  StringBuilder stringBuilder = new StringBuilder();
        List<String> savedHtmlSourceCode = new ArrayList<>();
        String htmlSourceCode;
        String fileName = urlToVerify.replaceAll("[\\/:*?\"<>|]","");
        fileName += ".txt";


        // find specific file for the web page we are trying to verify
        try{

            savedHtmlSourceCode = getSavedHtmlSource(fileName);

        } catch (FileNotFoundException fnf){
            Reporter.log("File not found for page "+urlToVerify);
        }


        // get html source code
        htmlSourceCode = getHtmlSource();
        String htmlSourceCodeNoSpaces = htmlSourceCode.replaceAll("\\s+","");

        //put the list of lines from the save code together in a string to compare with current source code
        // and compare lines
		String currentSourceCodeLine;
        for(int index = 0;index < savedHtmlSourceCode.size();index++){
            stringBuilder.append(savedHtmlSourceCode.get(index));
			stringBuilder = removeBlankSpace(stringBuilder);
            currentSourceCodeLine = stringBuilder.toString();


            if(!htmlSourceCodeNoSpaces.toLowerCase().startsWith(currentSourceCodeLine.toLowerCase())){
                Reporter.log("There is a difference between the save source code and current source code on line "+(index+1));
            }
        }*/
    }
}