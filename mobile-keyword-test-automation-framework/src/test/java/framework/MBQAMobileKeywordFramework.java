package framework;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MBQAMobileKeywordFramework extends KeywordFramework {

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.user.regainaccess.regainaccesschsav.ios"))
	public void IOSRegainAccessForgotPasswordNoChangePasswordCHOrSAVSpanish(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("45819") String dataId) {
		/*
		 * Manual Test Name: iPhone App - Regain Access - Change password - Checking or
		 * Savings - ES Manual Test ID: 234
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.user.ios.regainaccess"))
	public void IOSRegainAccessForgotPasswordChangePasswordCHOrSAVSpanish(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("45943") String dataId) {
		/*
		 * Manual Test Name: MBMT - Regain Access through Forgot Password - Account
		 * Authentication and Password Change - Checking or Savings - ES Manual Test ID:
		 * 29889
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.regainaccess.user.ios"))
	public void IOSRegainAccessForgotUsernameNoChangePasswordCHOrSAVSpanish(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("45820") String dataId) {
		/*
		 * Manual Test Name: iPhone App - Regain Access - Without changing password -
		 * Checking or Savings - ES Manual Test ID: 233
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.nontransactional.ios"))
	public void IOSCIBPNonTransactionalBannerEnglish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45824") String dataId) {
		/*
		 * Manual Test Name: MBCA 296 Non Transactional Customers iOS Banner (Non
		 * Transactional Bank 002) EN Manual Test ID: 39164
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	// No encontrado
	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.transfer.ios.singletransfer"))
	public void IOSMBMTTransfersMakeTransfersSameDaySp(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45944") String dataId) {
		/*
		 * IOSMBMTTransfersMakeTransfersSameDaySp
		 */
		/*
		 * Manual Test Name: MBMT - Transfers - Make Transfers - ES Manual Test ID:
		 * 29869
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	// No encontrado
	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.accountopening.ios"))
	public void IOSCustomerYoungerThan21YearsOldWithOnlyCCASpanish(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("45948") String dataId) {
		/*
		 * Manual Test Name: MBSB 448- Date of Birth filter_Customers Younger than 21
		 * years old with only CCA- ES Manual Test ID: 37041
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.accountopening.tortola.ios"))
	public void IOSAddTortolaFilterTortolaEnglish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45949") String dataId) {
		/*
		 * Manual Test Name: MBSB 691- Add Tortola filter in IOS APP- EN- Tortola Manual
		 * Test ID: 37030
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.retiromovil.ios"))
	public void IOSRetiroMovilEnrollmentNonCustomerDevicePasscodeNotConfiguredSpanish(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("45864") String dataId) {
		/*
		 * Manual Test Name: MBMT - 726 - iPhone - Enrollment - Non Customer - Device
		 * Passcode Not Configured - ES Manual Test ID: 25116
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.commercial.ios"))
	public void IOSFilterCommercialProfilesEnglish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45950") String dataId) {
		/*
		 * Manual Test Name: MBSB-1365-IOS filter for Commercial profiles-IOS-APP-PR-EN
		 * Manual Test ID: 38381
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.enrollmentchsav.ios"))
	public void IOSEnrollmentCheckingSavingsAccountSpanish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45865") String dataId) {
		/*
		 * Manual Test Name: MBMT - Enrollment Process with Checking or Savings Account
		 * - ES Manual Test ID: 29873
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.nontransactionalenrollment.nontransactional.ios"))
	public void IOSEnrollmentPRNonTransactionalMortgageSpanish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45858") String dataId) {
		/*
		 * Manual Test Name: MBCA 92 - MB iOS Enrollment with Mortgage Account Bank 001-
		 * ES Manual Test ID: 35533
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.nontransactionalenrollmentbvi..nontransactional.ios"))
	public void IOSEnrollmentBVINonTransactionalMortgageEnglish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45859") String dataId) {
		/*
		 * Manual Test Name: MBCA 92 - MB iOS Enrollment with Mortgage Account Ipad Bank
		 * 004 - EN Manual Test ID: 39138
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.loginmaskedtest.ios.debug"))
	public void FFIOSAppLoginRememberUserMaskUserEnglish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45862") String dataId) {
		/*
		 * Manual Test Name: 'MBMT - 1426 - iOS - Login With Remember Username And Mask
		 * User Name in Mi Banco Mobile - EN Manual Test ID: 35776
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.payments.balance.refresh.ios"))
	public void IOSPaymentsBalanceRefreshSpanish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45863") String dataId) {
		/*
		 * Manual Test Name: MBMT - 511 - iOS - Payments balance refresh - Claro - ES
		 * Manual Test ID: 21886
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	// *********************
	// These are Alejandro's tests. Need to include ORs that I don't have
	// Also need the FFs

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.alerts.ios"))
	public void FFIOSCIBPNonTransactionalAlertsSpanish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45867") String dataId) { // done
		/*
		 * Manual Test Name: MBM - 1600 - iOS - Non Transactional Alerts and
		 * Notifications Screen - Alerts Section - ES Manual Test ID: 38137
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.transfer.transferreceipts.ios"))
	public void FFIOSCIBPTransfersReceiptsSpanish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45945") String dataId) { // done
		/*
		 * Manual Test Name: MBMT - Transfers - Transfers Receipts - ES Manual Test ID:
		 * 29871
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.payments.ios"))
	public void FFIOSCIBPPaymentsReceiptsSpanish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45866") String dataId) { // done
		/*
		 * Manual Test Name: MBMT - Payments - Payments Receipts - ES Manual Test ID:
		 * 29858
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	// *********************
	// These are Rodolfos's tests.

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.login.loginworsa.ios.rofolfo"))
	public void FFIOSCIBPLoginWithoutRSAChallengeEnglish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45953") String dataId) { // done
		/*
		 * Tester: Rodolfo Depena Manual Test Name: iOS - Login Without RSA Challenge -
		 * EN Manual Test ID: 36445
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.help..login.ios.rofolfo"))
	public void FFIOSCIBPMBM1951PushHistoryLogWithoutMessagesEnglish(@Optional("") String dataFile,
			@Optional("45954") String dataId) {// ----- Done
		/*
		 * Tester: Rodolfo Depena Manual Test Name: MBM - 1951 - iPhone - Push History
		 * in Mi Banco Mobile (HTML) - Without Push Messages - EN Manual Test ID: 42999
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.help.login.ios.rofolfo"))
	public void FFIOSCIBPMBM1080HelpSectionSpanish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("45955") String dataId) { // ----- Done
		/*
		 * Tester: Rodolfo Depena Manual Test Name: MBMT - 1080 - iOS - Help Section -
		 * ES MBM - 1669 - iOS - Push Disclaimers in Alerts and Notifications Screen
		 * (HTML) - EN Manual Test ID: 36580 & 37268
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.help.login.ios.rofolfo"))
	public void FFIOSCIBPMBM1951PushHistoryLogWithMessagesEnglish(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("45956") String dataId) { // -----
																											// missing
		/*
		 * Tester: Rodolfo Depena Manual Test Name: MBM - 1951 - iPhone - Push History
		 * in Mi Banco Mobile (HTML) - Push History Log with Messages - EN Manual Test
		 * ID: 43004
		 */
		// Run test
		runTest(dataFile, dataId);
	}

	////////////////////////////////// ANDROID
	////////////////////////////////// ///////////////////////////////////////////

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.login.androidregacc.android"))
	public void FFAndroidCIBPRegainAccessChangePassChOrSavEnglish(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("46555") String dataId) {
		/*
		 * Tester: Joel Garcia Manual Test Name: Android App - Regain Access - Change
		 * password - Checking or Savings - EN Manual Test ID: 113
		 */
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.android.login"))
	public void FFAndroidCIBPLoginWithRSAChallengeRememberDeviceSpanish(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("46741") String dataId) {
		/*
		 * Tester: Alejandro Figueroa Manual Test Name: Android - Login With RSA
		 * Challenge And Remember Device - ES Manual Test ID: 36525
		 */
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.android.login"))
	public void FFAndroidCIBPEnrollmentChSavEnglish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("46821") String dataId) {
		/*
		 * Tester: Joel Garcia Manual Test Name: Android App - Enrollment - Checking or
		 * Savings Account - EN Manual Test ID: 123
		 */
		runTest(dataFile, dataId);
	}

	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("regression.cibp.android.payment.payments"))
	public void FFAndroidCIBPPaymentsReceiptsDeletePaymentEnglish(@Optional("MBQA-MasterAccountFile.xls") String dataFile,
			@Optional("46918") String dataId) {
		/*
		 * Tester: Alejandro Figueroa Manual Test Name: Android App - Payments -
		 * Payments Receipts - Delete Payment - EN Manual Test ID: 87
		 */
		runTest(dataFile, dataId);
	}
	
	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("Smoke-Android"))
	public void Android_ChangeLanguageAfterLogin_ES(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("1") String dataId) {
		/*
		 * Manual Test Name: iPhone App - Regain Access - Change password - Checking or
		 * Savings - ES Manual Test ID: 234
		 */
		// Run test
		runTest(dataFile, dataId);
	}
	
	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("Smoke-Android"))
	public void Android_ChangeLanguageBeforeLogin_EN(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("2") String dataId) {
		/*
		 * Manual Test Name: iPhone App - Regain Access - Change password - Checking or
		 * Savings - ES Manual Test ID: 234
		 */
		// Run test
		runTest(dataFile, dataId);
	}
	
	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("Smoke-Android"))
	public void Android_MobileWithdrawalFlow_Smoke(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("3") String dataId) {
		/*
		 * Manual Test Name: iPhone App - Regain Access - Change password - Checking or
		 * Savings - ES Manual Test ID: 234
		 */
		// Run test
		runTest(dataFile, dataId);
	}
	
	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("Smoke-Android"))
	public void Android_EnrollmentFlow_Smoke(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("4") String dataId) {
		/*
		 * Manual Test Name: iPhone App - Regain Access - Change password - Checking or
		 * Savings - ES Manual Test ID: 234
		 */
		// Run test
		runTest(dataFile, dataId);
	}
	
	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("Smoke-iOS"))
	public void iOS_ChangeLanguageAfterLogin_ES(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("1") String dataId) {
		/*
		 * Manual Test Name: iPhone App - Regain Access - Change password - Checking or
		 * Savings - ES Manual Test ID: 234
		 */
		// Run test
		runTest(dataFile, dataId);
	}
	
	@Parameters({ "dataFile", "dataId" })
	@Test(groups = ("Smoke-iOS"))
	public void iOS_ChangeLanguageBeforeLogin_EN(
			@Optional("MBQA-MasterAccountFile.xls") String dataFile, @Optional("2") String dataId) {
		/*
		 * Manual Test Name: iPhone App - Regain Access - Change password - Checking or
		 * Savings - ES Manual Test ID: 234
		 */
		// Run test
		runTest(dataFile, dataId);
	}
	
	

}
