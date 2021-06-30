package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Reports {
    private static String PATH_REPORT;
    private static String PATH_IMG;
    private static ExtentHtmlReporter htmlReport;
    private static ExtentReports _instance = new ExtentReports();
    
    static {
	    if( isWindows() ) {
	    	PATH_REPORT = System.getProperty("user.dir") + "\\test-output\\ExtentReports\\";
	    	PATH_IMG = PATH_REPORT + "img\\";
	    	htmlReport = new ExtentHtmlReporter(PATH_REPORT + "Reporte - " + dateNow() + ".html");
	    }else {
	    	PATH_REPORT = System.getProperty("user.dir") + "/test-output-report";
	    	PATH_IMG = PATH_REPORT + "/img/";
	    	htmlReport = new ExtentHtmlReporter(PATH_REPORT + "/Reporte" + dateNowUnix() + ".html");    	
	    }
    }

    public Reports(XmlTest xml) {
        cleanDirectorys(PATH_REPORT);
        cleanDirectorys(PATH_IMG);
        _instance.attachReporter(htmlReport);
        Object systemInfo = xml.getAllParameters(); //.getTestContext().getCurrentXmlTest().getAllParameters();
        ((Map) systemInfo).forEach((k, v) -> _instance.setSystemInfo((String) k, (String) v));
    }

    public void generateReport(ITestResult result, ThreadLocal<Driver> driver) {
        String testName = result.getName();
        String category = result.getMethod().getTestClass().getName().replace("framework.", "").replace("KeywordFramework", "");
        String description = result.getTestContext().getName();
        ExtentTest test = _instance.createTest(testName, description).assignCategory(category);

        if (driver.get().isMobile()) {
            test.assignDevice("Mobile");
        } else {
            test.assignDevice("Web");
        }

        int status = result.getStatus();

        Status logstatus = null;

        switch (status) {
            case 1:
                logstatus = Status.PASS;
                break;
            case 2:
                logstatus = Status.FAIL;
                break;
            default:
                break;
        }

        test.log(logstatus, "The test has ended with status " + logstatus);

        if (logstatus == Status.FAIL) {
            try {
                String _fileName = testName + " " + dateNow() + ".png";
                addScreenshotToReport(driver.get().getWebDriver(), _fileName);
                test.log(logstatus, "Last step screenshot", MediaEntityBuilder.createScreenCaptureFromPath("../ExtentReports/img/" + _fileName).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        _instance.flush();
    }

    private static void cleanDirectorys(String path) {
        File directorio = new File(path);
        File[] files = directorio.listFiles();
        File f;

        if (directorio.exists()) {
            for (int i = 0; i < files.length; i++) {
                f = new File(files[i].toString());
                f.delete();
            }

        }
    }

    private static String dateNow() {
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-MMM-aaaa kkmmss a");
        return objSDF.format(new Date());
    }
    
    private static String dateNowUnix() {
        SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return objSDF.format(new Date());
    }

    private static void addScreenshotToReport(WebDriver driver, String fileName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File finalScreenshotLocation = new File(PATH_IMG + fileName);
            FileUtils.copyFile(screenshot, finalScreenshotLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     private static String getOsName() {
        return  System.getProperty("os.name"); 
     }
     
     private static boolean isWindows() {
        return getOsName().startsWith("Windows");
     }

}