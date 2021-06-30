package framework;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MBMMobileKeywordFramework extends KeywordFramework {

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = { "MBIM-259" , "regression"})
	public void Android_MBIM_259_ChangeLanguageAndroidOreoOrHigherInsideSection_ES(
			@Optional("MBM-MasterAccountFile.xls") String dataFile, @Optional("35937") String dataId) {
		runTest(dataFile, dataId);
	}
	
	@Parameters({ "dataFile", "dataId" })
	@Test(groups = { "MBIM-259" , "regression" })
	public void Android_MBIM_259_ChangeLanguageAndroidSmallerThanOreoWithoutLogin_EN(
			@Optional("MBM-MasterAccountFile.xls") String dataFile, @Optional("35934") String dataId) {
		runTest(dataFile, dataId);
	}
    
	@Parameters({ "dataFile", "dataId" })
	@Test(groups = { "MBM-2435" , "regression"})
	public void Android_MBM_2435_PushCcaAlertsJointOrRelation_SmsActive_EnablePushOnMobile_ES(
			@Optional("MBM-MasterAccountFile.xls") String dataFile, @Optional("45460") String dataId) {
		runTest(dataFile, dataId);
	}
	
}
