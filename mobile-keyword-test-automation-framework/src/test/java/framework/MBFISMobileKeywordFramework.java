package framework;

import org.testng.annotations.Test;

public class MBFISMobileKeywordFramework extends KeywordFramework {

    @Test(groups = {"MBFIS-82"})
    public void MBFIS_82_MBAndroid_MenuBlockedSections_EN() {
        runTest("MBFIS-MasterAccountFile.xls", "42933");
    }

    @Test(groups = {"MBFIS-82"})
    public void MBFIS_82_MBAndroid_MenuBlockedSections_ES() {
        runTest("MBFIS-MasterAccountFile.xls", "42934");
    }

    @Test(groups = {"MBFIS-461"})
    public void Android_MBFIS_461_MBAndroidRedirectToRetirementPlanPlatformAllAccounts_EN() {
        runTest("MBFIS-MasterAccountFile.xls", "46836");
    }

    @Test(groups = {"MBFIS-640"})
    public void MBFIS_640_MBAndroid_NewAlertForTheRetirementPlanRelation_EN() {
        runTest("MBFIS-MasterAccountFile.xls", "46804");
    }

    @Test(groups = {"MBFIS-640"})
    public void MBFIS_640_MBAndroid_NewAlertForTheRetirementPlanRelation_ES() {
        runTest("MBFIS-MasterAccountFile.xls", "46820");
    }

    @Test(groups = {"MBFIS-768"})
    public void Android_MBFIS_768_MBAndroid_CSRTool_RetirementBalanceDowntime_ActivateTheButton_EN() {
        runTest("MBFIS-MasterAccountFile.xls", "5960");
    }

    @Test(groups = {"MBFIS-810"})
    public void Android_MBFIS_810_MBAndroid_RetirementPlanDowntimeMessage_EN() {
        runTest("MBFIS-MasterAccountFile.xls", "5962");
    }

    @Test(groups = {"MBFIS-810"})
    public void Android_MBFIS_810_MBAndroid_RetirementPlanDowntimeMessage_ES() {
        runTest("MBFIS-MasterAccountFile.xls", "5959");
    }

    @Test(groups = {"MBFIS-639"})
    public void MBFIS_639_MBiOS_NewAlertForTheRetirementPlanRelation_EN() {
        runTest("MBFIS-MasterAccountFile.xls", "47087");
    }

    @Test(groups = {"MBFIS-639"})
    public void MBFIS_639_MBiOS_NewAlertForTheRetirementPlanRelation_ES() {
        runTest("MBFIS-MasterAccountFile.xls", "47088");
    }

    @Test(groups = {"MBFIS-767"})
    public void MBFIS_767_MBiOS_CSRTool_RetirementBalanceDowntime_DeactiveTheButton_EN() {
        runTest("MBFIS-MasterAccountFile.xls", "5969");
    }

    @Test(groups = {"MBFIS-767"})
    public void MBFIS_767_MBiOS_CSRTool_RetirementBalanceDowntime_DeactiveTheButton_ES() {
        runTest("MBFIS-MasterAccountFile.xls", "5968");
    }

    @Test(groups = {"MBFIS-840"})
    public void MBFIS_840_MBiOS_RetirementPlanNewDisclaimer_EN() {
        runTest("MBFIS-MasterAccountFile.xls", "47632");
    }

    @Test(groups = {"MBFIS-840"})
    public void MBFIS_840_MBiOS_RetirementPlanNewDisclaimer_ES() {
        runTest("MBFIS-MasterAccountFile.xls", "47559");
    }

    @Test(groups = {"MBFIS-841"})
    public void MBFIS_841_MBAndroid_RetirementPlanNewDisclaimer_EN() {
        runTest("MBFIS-MasterAccountFile.xls", "46947");
    }

    @Test(groups = {"MBFIS-841"})
    public void MBFIS_841_MBAndroid_RetirementPlanNewDisclaimer_ES() {
        runTest("MBFIS-MasterAccountFile.xls", "47741");
    }

    @Test(groups = {"MBFIS-883"})
    public void MBFIS_883_MBAndroid_WealthscapeTermsAndConditions_EN() {
        runTest("MBFIS-MasterAccountFile.xls", "48287");
    }

    @Test(groups = {"MBFIS-883"})
    public void MBFIS_883_MBAndroid_WealthscapeTermsAndConditions_ES() {
        runTest("MBFIS-MasterAccountFile.xls", "48268");
    }
}