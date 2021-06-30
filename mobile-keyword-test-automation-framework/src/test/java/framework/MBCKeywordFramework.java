package framework;


import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MBCKeywordFramework extends KeywordFramework {

    @Parameters({"dataFile", "dataId"})
    @Test(groups = {"MBC-2560"})
    public void Android_MBC_2560_manage_new_real_time_payment_status_in_receipt_processing_EN(@Optional("MBC-MasterAccountFile.xls") String dataFile, @Optional("37045") String dataId) {
        runTest(dataFile, dataId);
    }

    @Parameters({"dataFile", "dataId"})
    @Test(groups = {"MBC-2560"})
    public void Android_MBC_2560_manage_new_real_time_payment_status_in_receipt_processing_ES(@Optional("MBC-MasterAccountFile.xls") String dataFile, @Optional("37046") String dataId) {
        runTest(dataFile, dataId);
    }

    @Parameters({"dataFile", "dataId"})
    @Test(groups = {"MBC-2560"})
    public void Android_MBC_2560_manage_new_real_time_payment_status_in_receipt_Sent_EN(@Optional("MBC-MasterAccountFile.xls") String dataFile, @Optional("37045") String dataId) {
        runTest(dataFile, dataId);
    }

    @Parameters({"dataFile", "dataId"})
    @Test(groups = {"MBC-2560"})
    public void Android_MBC_2560_manage_new_real_time_payment_status_in_receipt_enviados_EN(@Optional("MBC-MasterAccountFile.xls") String dataFile, @Optional("37046") String dataId) {
        runTest(dataFile, dataId);
    }

    @Parameters({"dataFile", "dataId"})
    @Test(groups = {"MBC-2623"})
    public void Android_MBC_2623_cca_vb_premia_images_my_accounts_android_EN(@Optional("MBC-MasterAccountFile.xls") String dataFile, @Optional("37048") String dataId) {
        runTest(dataFile, dataId);
    }

    @Parameters({"dataFile", "dataId"})
    @Test(groups = {"MBC-2623"})
    public void Android_MBC_2623_cca_vb_premia_images_my_accounts_android_ES(@Optional("MBC-MasterAccountFile.xls") String dataFile, @Optional("37049") String dataId) {
        runTest(dataFile, dataId);
    }
    @Parameters({"dataFile", "dataId"})
    @Test(groups = {"MBC-2636"})
    public void Android_MBC_2636_cca_vb_premia_images_statement_android_EN(@Optional("MBC-MasterAccountFile.xls") String dataFile, @Optional("37048") String dataId) {
        runTest(dataFile, dataId);
    }

    @Parameters({"dataFile", "dataId"})
    @Test(groups = {"MBC-2636"})
    public void Android_MBC_2636_cca_vb_premia_images_statement_android_ES(@Optional("MBC-MasterAccountFile.xls") String dataFile, @Optional("37050") String dataId) {
        runTest(dataFile, dataId);
    }
}


