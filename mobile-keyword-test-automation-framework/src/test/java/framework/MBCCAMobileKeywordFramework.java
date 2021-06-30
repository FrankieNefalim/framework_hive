package framework;

import org.testng.annotations.Test;

public class MBCCAMobileKeywordFramework extends KeywordFramework {

    @Test(groups = {"MBCC-13"})
    public void MBCC_13_iOS_marketPlaceProducts_commercial_negativo_EN() {
        runTest("MBCCA-MasterAccountFile.xls", "52248");
    }

    @Test(groups = {"MBCC-13"})
    public void MBCC_13_iOS_marketPlaceProducts_NonTransactional_negativo_ES() {
        runTest("MBCCA-MasterAccountFile.xls", "52254");
    }

    @Test(groups = {"MBCC-13"})
    public void MBCC_13_iOS_marketPlaceProducts_retail_positivo_ES() {
        runTest("MBCCA-MasterAccountFile.xls", "52238");
    }

    @Test(groups = {"MBCC-13"})
    public void MBCC_13_iOS_marketPlaceProducts_wealth_positivo_EN() {
        runTest("MBCCA-MasterAccountFile.xls", "52240");
    }

    @Test(groups = {"MBCC-14", "regression"})
    public void MBCC_14_android_marketPlaceProducts_commercial_negativo_ES() {
        runTest("MBCCA-MasterAccountFile.xls", "52259");
    }

    @Test(groups = {"MBCC-14", "regression"})
    public void MBCC_14_android_marketPlaceProducts_nonTransactional_negativo_EN() {
        runTest("MBCCA-MasterAccountFile.xls", "52261");
    }

    @Test(groups = {"MBCC-14", "regression"})
    public void MBCC_14_android_marketPlaceProducts_retail_positivo_ES() {
        runTest("MBCCA-MasterAccountFile.xls", "52262");
    }

    @Test(groups = {"MBCC-14", "regression"})
    public void MBCC_14_android_marketPlaceProducts_wealth_positivo_EN() {
        runTest("MBCCA-MasterAccountFile.xls", "52264");
    }

    @Test(groups = {"MBCC-15"})
    public void MBCC_15_iOS_marketplace_retail_positivo_pr_EN() {
        runTest("MBCCA-MasterAccountFile.xls", "52481");
    }

    @Test(groups = {"MBCC-15"})
    public void MBCC_15_iOS_marketplace_retail_positivo_usvi_ES() {
        runTest("MBCCA-MasterAccountFile.xls", "52488");
    }

    @Test(groups = {"MBCC-16"})
    public void MBCC_16_android_marketplaceMenu_RetailCustomerBank001PR_IDACCALoans_EN() {
        runTest("MBCCA-MasterAccountFile.xls", "52483");
    }

    @Test(groups = {"MBCC-16"})
    public void MBCC_16_android_marketplaceMenu_RetailCustomerBank002USVI_IDA_EN() {
        runTest("MBCCA-MasterAccountFile.xls", "52500");
    }

    @Test(groups = {"MBCC-16"})
    public void MBCC_16_android_marketplaceMenu_WealthCustomerBank001PR_IDACCALoans_EN() {
        runTest("MBCCA-MasterAccountFile.xls", "52501");
    }

    @Test(groups = {"MBCC-16"})
    public void MBCC_16_android_marketplaceMenu_RetailCustomerBank001PR_CCA_ES() {
        runTest("MBCCA-MasterAccountFile.xls", "52489");
    }

    @Test(groups = {"MBCC-16"})
    public void MBCC_16_android_marketplaceMenu_RetailCustomerBank006Culebra_IDA_ES() {
        runTest("MBCCA-MasterAccountFile.xls", "52490");
    }

    @Test(groups = {"MBCC-16"})
    public void MBCC_16_android_marketplaceMenu_WealthCustomerBank001PR_IDACCALoans_ES() {
        runTest("MBCCA-MasterAccountFile.xls", "52491");
    }

    @Test(groups = {"MBCC-19"})
    public void MBCC_19_iOS_marketplace_TyC_retail_positivo_usvi_EN() {
        runTest("MBCCA-MasterAccountFile.xls", "52615");
    }
    @Test(groups = {"MBCC-19"})
    public void MBCC_19_iOS_marketplace_TyC_retail_positivo_PR_ES() {
        runTest("MBCCA-MasterAccountFile.xls", "52604");
    }
}