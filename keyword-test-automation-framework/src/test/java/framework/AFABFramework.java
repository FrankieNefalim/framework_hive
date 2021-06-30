package framework;

import org.testng.annotations.*;

public class AFABFramework extends KeywordFramework {

    //done
    @Test(groups = {"Employees, regression, smoke"})
    public void HIVEE_Login(){ runTest("HIVE_MasterFile.xls", "10001"); }

    @Test(groups = {"Employees, regression, smoke"})
    public void HIVEE_Logout(){
        runTest("HIVE_MasterFile.xls", "10001");
    }

    @Test(groups = {"Employees, regression, smoke"})
    public void HIVES_Login(){
        runTest("HIVE_MasterFile.xls", "20000");
    }

    @Test(groups = {"Employees, regression, smoke"})
    public void HIVES_Logout(){
        runTest("HIVE_MasterFile.xls", "10001");
    }

    //todo
    @Test(groups = {"Employees, regression, smoke"})
    public void HIVEE_CreateNewOpportunity(){
        runTest("HIVE_MasterFile.xls", "10001");
    }

    @Test(groups = {"Employees, regression, smoke"})
    public void HIVES_SingIn(){
        runTest("HIVE_MasterFile.xls", "19999");
    }

    @Test(groups = {"Employees, regression, smoke"})
    public void HIVES_Apply(){
        runTest("HIVE_MasterFile.xls", "19999");
    }

}
    