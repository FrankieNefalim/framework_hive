package filereaders;

import javafx.util.Pair;
import jxl.Sheet;
import jxl.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A wrapper class to make use of object repositories.
 * Will generate WebElements based on the object definitions in the object repo file.
 *
 * @author  Jonathan Diaz
 * @version 1.0
 * @since   02/13/2019
 */
public class ObjectRepositoryReader {

    private static final int OBJECT_NAME = 0;
    private static final int LOCATOR_TYPE = 1;
    private static final int LOCATOR_VALUE = 2;

    private static final int DEFAULT_SHEET_NUMBER = 0;

    private static final int FIRST_ROW_OF_SHEET = 1;

    private Hashtable<String, Pair<String, String>> objectNameToLocators;

    private Driver driver;

    /**
     * Get full path of specific Object Repository file name.
     * @param objectRepositoryFileName The name of the test file.
     */
    private String getObjectRepositoryPath(String objectRepositoryFileName) throws Exception {
        Path start = Paths.get("../keyword-test-automation-framework/src/test/resources/objectrepository/");
        Path startM = Paths.get("../mobile-keyword-test-automation-framework/src/test/resources/objectrepository/");
        List<String> collect;
        Stream<Path> streamWeb = Files.walk(start);
        Stream<Path> streamMobile = Files.walk(startM);
        Stream<Path> stream = Stream.concat(streamWeb,streamMobile);
        {
            collect = stream
                    .filter(s -> s.getFileName().toString().equals(objectRepositoryFileName + ".xls"))
                    .map(String::valueOf)
                    .collect(Collectors.toList());
            if (collect.size() == 1) {
                return collect.get(0);
            } else {
                throw new Exception("More of one file with the same name: " + objectRepositoryFileName);
            }
        }
    }

    /**
     * The constructor for ObjectRepositoryReader class
     * @param driver The Driver object being used to access the objects
     * @param objectRepoName The name object repository we need to look in.
     */
    public ObjectRepositoryReader(Driver driver, String objectRepoName) throws Exception {
        this.driver = driver;
        Workbook workbook = Workbook.getWorkbook(new File(getObjectRepositoryPath(objectRepoName)));
        Sheet objectRepoSheet = workbook.getSheet(DEFAULT_SHEET_NUMBER);
        // Load all of the object repository values into the internal dictionary object
        objectNameToLocators = new Hashtable<>(objectRepoSheet.getRows());
        for (int i = FIRST_ROW_OF_SHEET; i < objectRepoSheet.getRows(); i++) {
            objectNameToLocators.put(objectRepoSheet.getCell(OBJECT_NAME, i).getContents(), new Pair(objectRepoSheet.getCell(LOCATOR_TYPE, i).getContents(), objectRepoSheet.getCell(LOCATOR_VALUE, i).getContents()));
        }
        // Close the connection to the excel workbook once all values have been loaded
        workbook.close();
    }

    /**
     * Will get a WebElement object using the name of the object as defined in the object repo file.
     * A wait time can also be specified to try to grab an element if it appears within the allotted wait time.
     * @param objectName The name of the object to get from the object repository.
     * @param waitTime Optional wait time parameter. Wait time will be in seconds.
     * @return WebElement Will attempt to return a WebElement if an objectName is specified. Otherwise will return null.
     */
    public WebElement getObject(String objectName, String waitTime) {
        if(objectName.isEmpty()){
            return null;
        }
        else{
            By objectLocator = generateByLocator(objectNameToLocators.get(objectName).getKey(), objectNameToLocators.get(objectName).getValue());

            if(waitTime != null){
                WebElement object;
                // disable the global driver wait time so that a specific wait time can be used
                driver.disableImplicitWait();
                // get the web element if it appears within the allotted wait time
                try {
                    new WebDriverWait(driver.getWebDriver(), new Double(waitTime).intValue()).until(ExpectedConditions.visibilityOfElementLocated(objectLocator));
                    object = driver.getWebDriver().findElement(objectLocator);
                }
                catch (TimeoutException e){
                    object =  null;
                }
                // re-enable the global wait time
                driver.enableImplicitWait();
                return object;
            }
            else {
                try {
                    return driver.getWebDriver().findElement(objectLocator);
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    public By getLocator(String objectName){
       return generateByLocator(objectNameToLocators.get(objectName).getKey(), objectNameToLocators.get(objectName).getValue());
    }

    /**
     * Helper method to generate By locators using the locator and the locator value.
     * @param locatorType The type of locator to use for web element lookup.
     * @param locatorValue The locator value to use for web element lookup.
     * @return By Will return a By locator using the specified locators.
     */
    private By generateByLocator(String locatorType, String locatorValue){
        switch (locatorType.toLowerCase()){
            case "classname":
                return By.className(locatorValue);
            case "cssselector":
                return By.cssSelector(locatorValue);
            case "id":
                return By.id(locatorValue);
            case "linktext":
                return By.linkText(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "partiallinktext":
                return By.partialLinkText(locatorValue);
            case "tagname":
                return By.tagName(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            default:
                return By.id(locatorValue);
        }
    }
}