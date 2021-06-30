package framework;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MBSDMobileKeywordFramework extends KeywordFramework {

    @Parameters({"dataFile", "dataId"})
//    @Test(groups = {"mbsd.business.android"})
    public void MBSD_3755_Authentication_Challenge_AddPayee_Email_Change(@Optional("MBSD-MasterAccountFile.xls") String dataFile, @Optional("1") String dataId) {
        runTest(dataFile, dataId);
    }

    @Parameters({"dataFile", "dataId"})
    @Test(groups = {"MBSD-3807-WebView"})
    public void MBSD_3807_Cookies_Update_Terms_And_Conditions_EN_WebView(@Optional("MBSD-MasterAccountFile.xls") String dataFile, @Optional("45206") String dataId) {
        runTest(dataFile, dataId);
    }

    @Parameters({"dataFile", "dataId"})
    @Test(groups = {"MBSD-3807-WebView"})
    public void MBSD_3807_Cookies_Update_Terms_And_Conditions_ES_WebView(@Optional("MBSD-MasterAccountFile.xls") String dataFile, @Optional("45209") String dataId) {
        runTest(dataFile, dataId);
    }

}
