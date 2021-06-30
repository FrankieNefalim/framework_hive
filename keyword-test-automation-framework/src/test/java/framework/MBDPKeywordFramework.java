package framework;

import org.testng.annotations.*;

public class MBDPKeywordFramework extends KeywordFramework {

    //region MBDP-2226
    @Parameters({"dataFile", "dataId"})
    @Test(groups = {"MBDP_2226"})
    public void MBDP_2226_WebCustomerCode047ForeignCustomerWithInvalidSSNWarningTextModificationEN(@Optional("MBDP-MasterAccountFile.xls") String dataFile, @Optional("40032") String dataId) {
        runTest(dataFile, dataId);
    }

    @Test(groups = {"MBDP_2226"})
    public void MBDP_2226_WebCustomerCode047ForeignCustomerWithInvalidSSNWarningTextModificationES() {
        String dataFile = "MBDP-MasterAccountFile.xls";
        String dataId = "40033";
        runTest(dataFile, dataId);
    }
    //endregion

    //region MBDP-2583
    @Test(groups = {"MBDP_2583"})
    public void MBDP_2583_OAO_IdentificationInfo_CustomerApplicationFormRedesign_VerifyButtonsNavigationEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2583"})
    public void MBDP_2583_OAO_IdentificationInfo_CustomerApplicationFormRedesign_VerifyButtonsNavigationES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2584
    @Test(groups = {"MBDP_2584"})
    public void MBDP_2584_OAO_ContactInfo_CustomerApplicationFormRedesign_VerifyFieldEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2584"})
    public void MBDP_2584_OAO_ContactInfo_CustomerApplicationFormRedesign_VerifyFieldES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }

    @Test(groups = {"MBDP_2584"})
    public void MBDP_2584_OAO_ContactInfo_CustomerApplicationFormRedesign_VerifyButtonsNavigationEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2584"})
    public void MBDP_2584_OAO_ContactInfo_CustomerApplicationFormRedesign_VerifyButtonsNavigationES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2585
    @Test(groups = {"MBDP_2585"})
    public void MBDP_2585_OAO_EmploymentInfo_CustomerApplicationFormRedesign_VerifyErrorsOnFieldsEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2585"})
    public void MBDP_2585_OAO_EmploymentInfo_CustomerApplicationFormRedesign_VerifyErrorsOnFieldsES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }

    @Test(groups = {"MBDP_2585"})
    public void MBDP_2585_OAO_EmploymentInfo_CustomerApplicationFormRedesign_VerifyFieldsEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2585"})
    public void MBDP_2585_OAO_EmploymentInfo_CustomerApplicationFormRedesign_VerifyFieldsES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }

    @Test(groups = {"MBDP_2585"})
    public void MBDP_2585_OAO_EmploymentInfo_CustomerApplicationFormRedesign_VerifyNavigationsEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2585"})
    public void MBDP_2585_OAO_EmploymentInfo_CustomerApplicationFormRedesign_VerifyNavigationsES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2586
    @Test(groups = {"MBDP_2586"})
    public void MBDP_2586_OAO_EnrollmentInfo_CustomerApplicationFormRedesign_VerifyFieldsEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2586"})
    public void MBDP_2586_OAO_EnrollmentInfo_CustomerApplicationFormRedesign_VerifyFieldsES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }

    @Test(groups = {"MBDP_2586"})
    public void MBDP_2586_OAO_EnrollmentInfo_CustomerApplicationFormRedesign_VerifyNavigationsEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2586"})
    public void MBDP_2586_OAO_EnrollmentInfo_CustomerApplicationFormRedesign_VerifyNavigationsES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2604
    @Test(groups = {"MBDP_2604"})
    public void MBDP_2604_OAO_ContactInfo_AddressValidations_CustomerApplicationFormRedesignEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2604"})
    public void MBDP_2604_OAO_ContactInfo_AddressValidations_CustomerApplicationFormRedesignES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2627
    @Test(groups = {"MBDP_2627"})
    public void MBDP_2627_OAO_IdentificationInfoUpdate_CustomerApplicationFormRedesign_VerifyErrorsOnInputFieldsEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2627"})
    public void MBDP_2627_OAO_IdentificationInfoUpdate_CustomerApplicationFormRedesign_VerifyErrorsOnInputFieldsES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }

    @Test(groups = {"MBDP_2627"})
    public void MBDP_2627_OAO_IdentificationInfoUpdate_CustomerApplicationFormRedesign_VerifyFieldEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2627"})
    public void MBDP_2627_OAO_IdentificationInfoUpdate_CustomerApplicationFormRedesign_VerifyFieldES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2633
    @Test(groups = {"MBDP_2633"})
    public void MBDP_2633_OAO_KBADisplayPlaceholderEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2633"})
    public void MBDP_2633_OAO_KBADisplayPlaceholderES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2639
    @Test(groups = {"MBDP_2639"})
    public void MBDP_2639_NewAccountOpeningStepsProgressAndBanner_VerifyProgressEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2639"})
    public void MBDP_2639_NewAccountOpeningStepsProgressAndBanner_VerifyProgressES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2641
    @Test(groups = {"MBDP_2641"})
    public void MBDP_2641_OAO_UsernameReservation_VerifyErrorsOnFieldsEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2641"})
    public void MBDP_2641_OAO_UsernameReservation_VerifyErrorsOnFieldsES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2640
    @Test(groups = {"MBDP_2640"})
    public void MBDP_2640_OAO_UsernameReservationSuggestions_VerifySuggestionsEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2640"})
    public void MBDP_2640_OAO_UsernameReservationSuggestions_VerifySuggestionsES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2743
    @Test(groups = {"MBDP_2743"})
    public void MBDP_2743_OAO_UsernameReservationUpdates_VerifySuggestionsEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2743"})
    public void MBDP_2743_OAO_UsernameReservationUpdates_VerifySuggestionsES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2745, MBDP-2598
    @Test(groups = {"MBDP_2745"})
    public void MBDP_2745_MBDP_2598_OAO_DCIScreenReplacementEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2745"})
    public void MBDP_2745_MBDP_2598_OAO_DCIScreenReplacementES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }

    @Test(groups = {"MBDP_2745"})
    public void MBDP_2745_MBDP_2598_OAO_DCIScreenReplacementCancelOptionEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2745"})
    public void MBDP_2745_MBDP_2598_OAO_DCIScreenReplacementCancelOptionES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2745, MBDP-2599
    @Test(groups = {"MBDP_2745_HeadlessModeOff"})
    public void MBDP_2745_MBDP_2599_OAO_TermsAndConds_ReplacementEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2745_HeadlessModeOff"})
    public void MBDP_2745_MBDP_2599_OAO_TermsAndConds_ReplacementES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }

    @Test(groups = {"MBDP_2745"})
    public void MBDP_2745_MBDP_2599_OAO_TermsAndConds_Replacement_CancelOptionEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2745"})
    public void MBDP_2745_MBDP_2599_OAO_TermsAndConds_Replacement_CancelOptionES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2688
    @Test(groups = {"MBDP_2688"})
    public void MBDP_2688_NewAccountOpeningStepsBanner_Mobile_VerifyProgressEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2688"})
    public void MBDP_2688_NewAccountOpeningStepsBanner_Mobile_VerifyProgressES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2763
    @Test(groups = {"MBDP_2763"})
    public void MBDP_2763_NewAccountOpeningStepsBanner_Web_VerifyProgressEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2763"})
    public void MBDP_2763_NewAccountOpeningStepsBanner_Web_VerifyProgressES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion

    //region MBDP-2744
    @Test(groups = {"MBDP_2744"})
    public void MBDP_2744_OAO_DateOfBirthParameterLimitPersonalInfoEN() {
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2744"})
    public void MBDP_2744_OAO_DateOfBirthParameterLimitPersonalInfoES() {
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    //endregion
    @Test(groups = {"MBDP_2804"})
    public void MBDP_2804_ApplicationCreationAtQueueEN(){
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }
    @Test(groups = {"MBDP_2823"})
    public void MBDP_2823_NewClientAccountConfirmationScreenReferred(){
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }
    @Test(groups = {"MBDP_2845"})
    public void MBDP_2845_NewClientAccountConfirmationScreenReferred(){
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }
    @Test(groups = {"MBDP_2849"})
    public void MBDP_2849_DriversLicenseIDPassportIDdataEntryRestrictionsEN(){
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }
    @Test(groups = {"MBDP_2849"})
    public void MBDP_2849_DriversLicenseIDPassportIDdataEntryRestrictionsES(){
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    @Test(groups = {"MBDP_2855"})
    public void MBDP_2855_IDComboBoxAndOutputEN(){
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }
    @Test(groups = {"MBDP_2855"})
    public void MBDP_2855_IDComboBoxAndOutputES(){
        runTest("MBDP-MasterAccountFile.xls", "44916");
    }
    @Test(groups = {"MBDP_2812"})
    public void MBDP_2812_TimeOut(){
        runTest("MBDP-MasterAccountFile.xls", "44917");
    }

    @Test(groups = {"MBDP_2904"})
    public void MBDP_2904_MiBancoUserRegistrationApproveApprovedByOfficerEN(){

        runTest("MBDP-MasterAccountFile.xls", "44917");
    }

    @Test(groups = {"MBDP_2904"})
    public void MBDP_2904_MiBancoUserRegistrationApproveApprovedByOfficerES(){

        runTest("MBDP-MasterAccountFile.xls", "44916");
    }

    @Test(groups = {"MBDP_2990"})
    public void MBDP_2990_KBAsCustomComboBoxOptionEN(){

        runTest("MBDP-MasterAccountFile.xls", "44918");
    }

    @Test(groups = {"MBDP_2990"})
    public void MBDP_2990_KBAsCustomComboBoxOptionES(){

        runTest("MBDP-MasterAccountFile.xls", "44919");
    }
    @Test(groups = {"MBDP_2927"})
    public void MBDP_2927_CancelButtonPopUpMessageEN(){

        runTest("MBDP-MasterAccountFile.xls", "44917");
    }

    @Test(groups = {"MBDP_2927"})
    public void MBDP_2857_CancelButtonPopUpMessageES(){

        runTest("MBDP-MasterAccountFile.xls", "44916");
    }


    @Test(groups = {"MBDP_2857"})
    public void MBDP_2857_ClientValidationStatusIndicatorAtQueueEN(){

        runTest("MBDP-MasterAccountFile.xls", "44917");
    }

    @Test(groups = {"MBDP_2857"})
    public void SYN_USN_Login(){

        runTest("MBDP-MasterAccountFile.xls", "44916");
    }
}
    