package framework;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MBCAMobileKeywordFramework extends KeywordFramework {

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = { "MBCA-1477" })
	public void Android_MBCA_1477_AutomaticRedemptionActive_EN(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
			@Optional("46056") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = { "MBCA-1477" })
	public void Android_MBCA_1477_AutomaticRedemptionActive_ES(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
			@Optional("46058") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = { "MBCA-1477" })
	public void Android_MBCA_1477_AutomaticRedemptionDisable_EN(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
			@Optional("46055") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = { "MBCA-1477" })
	public void Android_MBCA_1477_AutomaticRedemptionDisable_ES(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
			@Optional("46057") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = { "MBCA-1516" })
	public void Android_MBCA_1516_ConfirmAutomaticRedemptionActive_EN(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
																@Optional("46055") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = { "MBCA-1516" })
	public void Android_MBCA_1516_ConfirmAutomaticRedemptionNotActive_EN(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
																@Optional("46055") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = { "MBCA-1516" })
	public void Android_MBCA_1516_ConfirmAutomaticRedemptionActive_ES(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
																@Optional("46056") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = { "MBCA-1516" })
	public void Android_MBCA_1516_ConfirmAutomaticRedemptionNotActive_ES(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
																@Optional("46056") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1629"})
	public void Android_MBCA_1629_ConfigureRedemption_StatementCreditCreditCardPrimary_EN(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("46907") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1629"})
	public void Android_MBCA_1629_ConfigureRedemption_StatementCreditCreditCardPrimary_ES(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("46908") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1629"})
	public void Android_MBCA_1629_ConfigureRedemption_AcctDepositCreditCardPrimary_EN(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("46909") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1629"})
	public void Android_MBCA_1629_ConfigureRedemption_AcctDepositCreditCardPrimary_ES(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("46910") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1330"})
	public void Android_MBCA_1330_Other_Bank_Payment_Commercial_EN(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("44890") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1330"})
	public void Android_MBCA_1330_Other_Bank_Payment_Commercial_ES(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("44891") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1330"})
	public void Android_MBCA_1330_Other_Bank_Payment_Retail_EN(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("44892") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1330"})
	public void Android_MBCA_1330_Other_Bank_Payment_Retail_ES(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("44893") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1330"})
	public void Android_MBCA_1330_Other_Bank_Payment_Wealth_EN(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("44894") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1330"})
	public void Android_MBCA_1330_Other_Bank_Payment_Wealth_ES(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("44895") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1591"})
	public void Android_MBCA_1591_Premia_Redirect_Loading_EN(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("52620") String dataId) {
		runTest(dataFile, dataId);
	}

	@Parameters({"dataFile", "dataId"})
	@Test(groups = {"MBCA-1591"})
	public void Android_MBCA_1591_Premia_Redirect_Loading_ES(@Optional("MBCA-MasterAccountFile.xls") String dataFile,
						  @Optional("52628") String dataId) {
		runTest(dataFile, dataId);
	}


}
