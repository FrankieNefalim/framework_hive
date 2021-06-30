package framework;

import org.testng.annotations.Test;

public class MBFMMobileKeywordFramework extends KeywordFramework {

    @Test(groups = {"MBFM-1"})
    public void MBFM_1_Android_CambiosPropertyFileDFM_EN() {
        runTest("MBFM-MasterAccountFile.xls", "48814");
    }

    @Test(groups = {"MBFM-1"})
    public void MBFM_1_Android_CambiosPropertyFileDFM_ES() {
        runTest("MBFM-MasterAccountFile.xls", "48815");
    }

}