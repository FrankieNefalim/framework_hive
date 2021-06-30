package framework;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MBSEMobileKeywordFramework extends KeywordFramework {
	
	@Parameters({"dataFile", "dataId"})
	@Test(groups= {"MBSE-1713"})
	public void Android_MBSE_1713_add_desktopversion_menuitem(@Optional("MBSE-MasterAccountFile.xls") String dataFile, @Optional("46288") String dataId) {
	    runTest(dataFile, dataId);
	}
	
}
