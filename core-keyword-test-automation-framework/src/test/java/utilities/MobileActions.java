package utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.Reporter;
import java.util.HashMap;
import java.util.Set;

public class MobileActions extends CommonFunctions{

    private static final String ESCAPED_INPUT_PARAMETER_DELIMITER = "\\|\\|";

    private static final double DEFAULT_PERCENT_OF_SCREEN_TO_SWIPE = .5;
    private static final String DEFAULT_SCROLL_PERCENT_OF_SCREEN_TO_SWIPE = "85";

    /**
     * Will simulate a tap action upon the specified object.
     * An object value is required, and the input value is optional for this keyword.
     * If an input is specified, then it will be used as x,y coordinates to determine the tap location offset from the top left of the object.
     * The input should have 2 parameters specified, and both parameters must be a whole number.
     * By default, the center of the object will be tapped.
     * @param driver The test driver that will be used to execute the action.
     * @param object The object to perform the action upon.
     * @param coordinates (optional) The x,y coordinates within the object to perform the tap upon.
     */
    public static void tapScreen(MobileDriver driver, WebElement object, String coordinates){
        TouchAction touchAction = new TouchAction(driver);

        // If an input is specified, use the 2 input args as x,y coordinates for the tap location
        if(!coordinates.isEmpty()) {

            int xOffSet = 0;
            int yOffSet = 0;
            String[] args = coordinates.split(ESCAPED_INPUT_PARAMETER_DELIMITER);
            if(args.length > 1) {
                xOffSet = Integer.parseInt(args[0]);
                yOffSet = Integer.parseInt(args[1]);
            }
            else{
                Reporter.log("Incorrect number of input arguments for tap action. Need 2 input arguments for the x,y coordinates to tap.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
            }

            // If an object is specified, use the x,y coordinates in relation to the object location
            if(object != null){
                xOffSet += object.getRect().getX();
                yOffSet += object.getRect().getY();
            }

            touchAction.tap(PointOption.point(xOffSet, yOffSet)).perform();
        }
        else {
            touchAction.tap(new ElementOption().withElement(object)).perform();
        }
    }

    /**
     * Will perform the tap action only if the specified object exists within the specified timeout span.
     * An object value is required, and the input value is optional for this keyword.
     * If an input is specified, then it will be used as x,y coordinates to determine the tap location offset from the top left of the object.
     * The input should have 2 parameters specified, and both parameters must be a whole number.
     * By default, the center of the object will be tapped.
     * @param driver The test driver that will be used to execute the action.
     * @param object The object to perform the action upon.
     * @param input (optional) The x,y coordinates within the object to perform the tap upon.
     */
    public static void tapIfExists(MobileDriver driver, WebElement object, String input){

        try {
            if (object != null && object.isDisplayed()) {
                tapScreen(driver, object, input);
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
	 * Will perform the tap action only if the account exists in the list An object
	 * value is required. The input should have 2 parameters specified By default,
	 * the center of the object will be tapped.
	 * 
	 * @param driver The test driver that will be used to execute the action.
	 * @param input  will receive the optionSelected and the verificationValue, the
	 *               verificationValue is the value of the account and
	 *               optionSelected is 'more' or 'less'
	 */
	public static void getAccountViewAndTapOption(MobileDriver driver, String input) {

		String[] argum = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);
		String verificationValue = "";
		String plastic = "";
		String optionSelected = "";

		try {

			// Validate that we have the right number of arguments. If not, fail and exit
			// the function.
			if (argum.length > 1) {
				verificationValue = argum[0];
				optionSelected = argum[1];
				if (argum.length > 2) {
					plastic = argum[2];
				}
			} else {
				Assert.fail("For the verification type AttributeIs, TWO arguments are required.  Only [" + argum.length
						+ "] were given.");
				return;
			}

			// if optionSelected is equal to 'more' will tap the is more than option, unless
			// wil tap is less than option
			if (optionSelected.equalsIgnoreCase("credit")) {

				tapScreen(driver,
						driver.findElement(By.xpath("//android.view.View[@content-desc='"+verificationValue+"']//following::android.view.View[2][@content-desc='"+plastic+"']//following::android.view.View[2]")),
						"");
			} else if (optionSelected.equalsIgnoreCase("more")) {
				tapScreen(driver,
						driver.findElement(By.xpath("//android.view.View[@resource-id='account-number'][@content-desc='"
								+ verificationValue + "']//following::android.view.View")),
						"");
			} else {
				tapScreen(driver,
						driver.findElement(By.xpath("//android.view.View[@resource-id='account-number'][@content-desc='"
								+ verificationValue + "']//following::android.view.View[2]")),
						"");
			}

		} catch (Exception ex) {
			Reporter.log("Unexpected exception when checking for object.  Please review feature file.<br>",
					CommonFunctions.VERIFICATION_FAILURE_LOG_LEVEL);
			ex.printStackTrace();
			Assert.fail("Unexpected exception when checking for object.  Please review feature file.");
		}
	}

	/**
	 * Will perform the tap action only if the specified object has the specified
	 * atribute. An object value is required. The input should have 2 parameters
	 * specified By default, the center of the object will be tapped.
	 * 
	 * @param driver The test driver that will be used to execute the action.
	 * @param object The object to perform the action upon.
	 * @param input  will receive the attributeType and the verificationValue, if
	 *               the condition is true tap action will perform.
	 */
	public static void tapIfAttributeIs(MobileDriver driver, WebElement object, String input) {

		String[] argum = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);
		String verificationValue = "";
		String attributeType = "";
		String actualValue = "";

		try {

			// Validate that we have the right number of arguments. If not, fail and exit
			// the function.
			if (argum.length > 1) {
				attributeType = argum[0];
				actualValue = object.getAttribute(attributeType);
				verificationValue = argum[1];
			} else {
				Assert.fail("For the verification type AttributeIs, TWO arguments are required.  Only [" + argum.length
						+ "] were given.");
				return;
			}

			if (actualValue.equals(verificationValue)) {
				tapScreen(driver, object, "");
				Reporter.log("The given attribute[" + attributeType + "] with the  expected value[" + verificationValue
						+ "] matches the actual value[" + actualValue + "]");
			} else {
				Reporter.log("Failed to match the actual attribute value[" + actualValue
						+ "] against the expected attribute[" + attributeType + "] value[" + verificationValue + "]");

			}

		} catch (Exception ex) {
			Reporter.log("Unexpected exception when checking for object.  Please review feature file.<br>",
					CommonFunctions.VERIFICATION_FAILURE_LOG_LEVEL);
			ex.printStackTrace();
			Assert.fail("Unexpected exception when checking for object.  Please review feature file.");
		}
	}

    /**
     * Will simulate a press action upon the specified object.
     * An object value is required, and no input value is taken for this keyword.
     * @param driver The test driver that will be used to execute the action.
     * @param object The object to perform the action upon.
     */
    public static void press(MobileDriver driver, WebElement object){
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new ElementOption().withElement(object)).perform();
    }

    /**
     * Will simulate a long press action upon the specified object.
     * An object value is required, and no input value is taken for this keyword.
     * @param driver The test driver that will be used to execute the action.
     * @param object The object to perform the action upon.
     */
    public static void longPress(MobileDriver driver, WebElement object){
        TouchAction touchAction = new TouchAction(driver);
        touchAction.longPress(new ElementOption().withElement(object)).perform();
    }


    public static void swipeDownwards(MobileDriver driver, WebElement object, String input){
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new PointOption().withCoordinates(200, 0)).waitAction().moveTo(new PointOption().withCoordinates(200, 500)).release().perform();
    }

    /**
     * Will simulate an upwards swipe action.
     * Object and input values are optional for this keyword.
     * By default, a swipe will occur along the center of the screen for about half of the screen's length.
     * If an object is specified, then it will be used as the start point of the swipe.
     * The input value will be the percentage of the screen dimensions that will be used as the swipe length.
     * Input value should be a number between 1-100. For example, enter 50 to do a swipe that is 50% of the screen's height.
     * @param driver The test driver that will be used to execute the action.
     * @param object (optional) The object to start the swipe from.
     * @param screenSwipePercentage (optional) The percentage of the screen dimensions that will be used as the swipe length.
     */
    public static void swipeUpwards(MobileDriver driver, WebElement object, String screenSwipePercentage){

        // Swipe length is a product of the screen's dimensions
        int swipeLength;

        // Determine the length of the swipe, using either the input value or a default value
        if(!screenSwipePercentage.isEmpty()){
            swipeLength = (int) (driver.manage().window().getSize().getHeight() * ((double)Integer.parseInt(screenSwipePercentage)/100));
        }
        else{
            swipeLength = (int) (driver.manage().window().getSize().getHeight() * DEFAULT_PERCENT_OF_SCREEN_TO_SWIPE);
        }

        int anchorX = 0; // x coordinate will not change for this vertical swipe
        int startY; // Lower on the screen (higher y coordinate)
        int endY; // Higher on the screen (lower y coordinate)

        // If an object is passed in, use it as the start point of the swipe
        if(object != null){
            Point objectCenter = getObjectCenterCoordinate(object);
            anchorX = objectCenter.getX();
            startY = objectCenter.getY();
            endY = startY - swipeLength;
        }
        else{ // Otherwise swipe vertically across the center of the screen
            anchorX = driver.manage().window().getSize().getWidth() / 2; // Middle of the screen
            int screenEdgeOffset = ((driver.manage().window().getSize().getHeight() - swipeLength) / 2);
            endY = screenEdgeOffset;
            startY = endY + swipeLength;
        }
        if(endY < 0) endY = 0; // The final y coordinate cannot be less than 0

        // Perform swipe
        swipe(driver, anchorX, startY, anchorX, endY);
    }

    /**
     * will make swipe with the webelement as start point.
     *
     * @param driver
     * @param object
     * @param input
     */
    protected static void pressAndSwipe(MobileDriver driver, WebElement object, String input) {
        String[] args = input.split(ESCAPED_INPUT_PARAMETER_DELIMITER);
        Point objectCenter = getObjectCenterCoordinate(object);
        int loop = Integer.parseInt(args[2]);
        int anchorX = objectCenter.getX();
        int startY = objectCenter.getY();
        int moveY = (int) (startY * ((double) Integer.parseInt(args[1]) / 100));
        if (args[0].toLowerCase().equals("down")) {
            moveY = moveY * -1;
        }
        TouchAction touchAction = new TouchAction(driver);

        for (int i = 0; i < loop; i++) {
            touchAction.press(new PointOption().withCoordinates(anchorX, startY)).waitAction()
                    .moveTo(new PointOption().withCoordinates(anchorX, startY + moveY)).release().perform();
        }
    }

    /**
     * Will simulate scrolling down a scrollable object.
     * Object and input values are optional for this keyword.
     * This scroll action operates differently for iOS and Android devices.
     * On iOS a scroll script is executed, on Android a swipe action is executed.
     * If an object is specified, then it will be used as the object to scroll (iOS only).
     * The input value will be the percentage of the screen dimensions that will be used as the swipe length.
     * Input value should be a number between 1-100. For example, enter 50 to do a swipe that is 50% of the screen's height.
     * @param driver The test driver that will be used to execute the action.
     * @param object (optional) The object to start the swipe from.
     * @param input (optional) The percentage of the screen dimensions that will be used as the swipe length.
     */
    public static void scrollDown(MobileDriver driver, WebElement object, String input){

        // If running against an iOS device, use a script to scroll
        if(driver instanceof IOSDriver){
            JavascriptExecutor js = (JavascriptExecutor) driver;
            HashMap<String, String> scrollObject = new HashMap<>();
            scrollObject.put("direction", "down"); // Will scroll down 1 screen unit
            // If an object is specified, then scroll down until it is visible
            if(object != null){
                RemoteWebElement remoteObject = (RemoteWebElement)object;
                // The parent element of element must be scrollable.
                scrollObject.put("element", remoteObject.getId());
                scrollObject.put("toVisible", "true");
            }
            else{
                // Otherwise use the input object to scroll to the object with the matching name property
                if(!input.isEmpty()){
                    scrollObject.put("name", input);
                    scrollObject.put("toVisible", "true");
                }
            }
            js.executeScript("mobile: scroll", scrollObject);
        } // Otherwise scroll by simulating a swipe
        else{
            swipeUpwards(driver, object, DEFAULT_SCROLL_PERCENT_OF_SCREEN_TO_SWIPE);
        }
    }

    // TODO: implement
    public static void scrollTo(){
        // Scroll to (no direction)
        //        RemoteWebElement element = (RemoteWebElement)driver. findElement(By.name("elementName"));
        //        String elementID = element.getId();
        //        HashMap<String, String> scrollObject = new HashMap<String, String>();
        //        scrollObject.put("element", elementID);
        //        scrollObject.put("toVisible", "not an empty string");
        //        driver.executeScript("mobile:scroll", scrollObject);
    }

    /**
     * Will hide the virtual keyboard that appears on mobile devices.
     * Does nothing if there is no virtual keyboard.
     * Object and input values are not taken for this keyword.
     * @param driver driver The test driver that will be used to execute the action.
     */
    public static void hideKeyboard(MobileDriver driver){
        try{
            driver.hideKeyboard();
        }
        catch (Exception e){
            Reporter.log("There is no keyboard to hide.<br>", VERIFICATION_FAILURE_LOG_LEVEL);
            e.printStackTrace();
        }
    }

    /**
     * Will switch the driver context to either the newest context or the specified context taken from the input value.
     * An object value is not taken, and the input value is optional.
     * By default, will switch to the newest context. (Probably newest web view or browser tab)
     * The input can be the name of the context, or a whole number designating the index of the context to switch to.
     * @param driver The test driver that will be used to execute the action.
     * @param contextNameOrIndex (optional) The name of the context, or a whole number designating the index of the context to switch to.
     */
    public static void switchContext(MobileDriver driver, String contextNameOrIndex){

        Set<String> contextNames = driver.getContextHandles();

        if(contextNameOrIndex.isEmpty()){
            // Switch to the newest context (The current safari tab)
            driver.context(contextNames.toArray()[contextNames.toArray().length-1].toString());
        }
        else{
            if(isNumeric(contextNameOrIndex)){
                driver.context(contextNames.toArray()[Integer.parseInt(contextNameOrIndex)].toString());
            }
            else{
                driver.context(contextNameOrIndex);
            }
        }
    }

    /**
     * This function rotates the device based on whether the device is in a portrait or
     * landscape view.
     * @param driver: Mobile driver for the application
     * @param input:  Must be either portrait(vertical) or landscape(horizontal)
     */
    public static void rotateDevice(MobileDriver driver, String input){
        String orientation = input.toLowerCase().trim();

        switch(orientation){
            case "portrait":
                driver.rotate(ScreenOrientation.PORTRAIT);
                break;
            case "landscape":
                driver.rotate(ScreenOrientation.LANDSCAPE);
                break;
            default:
                //Reporter.log("Rotate device - orientation[" + orientation + "] not supported.");
                break;
        }
    }


    /**
     * Performs a swipe using the appium driver. Swipes from the start coordinates to the end coordinates.
     * @param driver Mobile driver for the application
     * @param startXCoordinate
     * @param startYCoordinate
     * @param endXCoordinate
     * @param endYCoordinate
     */
    private static void swipe(MobileDriver driver, int startXCoordinate, int startYCoordinate, int endXCoordinate, int endYCoordinate){
        new TouchAction(driver).longPress(new PointOption().withCoordinates(startXCoordinate, startYCoordinate)).moveTo(new PointOption().withCoordinates(endXCoordinate, endYCoordinate)).release().perform();
    }

    /**
     * Returns the center x,y coordinate of the passed in object.
     * @param object The WebElement to get the coordinates from.
     * @return
     */
    private static Point getObjectCenterCoordinate(WebElement object){
        int x = object.getRect().getX() + (object.getRect().getWidth()/2);
        int y = object.getRect().getY() + (object.getRect().getHeight()/2);
        return new Point(x, y);
    }
}