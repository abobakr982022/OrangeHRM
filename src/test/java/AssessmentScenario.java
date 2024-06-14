import TestData.Configurations;
import TestData.GeneralMethods;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;

import static org.testng.Assert.assertEquals;

public class AssessmentScenario extends BaseTest {

    Login login ;
    AdminTab adminTab;


    @BeforeMethod
    public void initialize() throws AWTException {
        login =new Login(driver);
        adminTab = new AdminTab(driver);
    }


    @Test (priority = 0)
    public void LoginWithCred() throws InterruptedException {
       login.LoginWithCred(Configurations.userName,Configurations.password);
        GeneralMethods.WaitTOSee(2000);
    }

    @Test (priority = 1)
    public void NavToAdminPage() throws InterruptedException {
        login.LoginWithCred(Configurations.userName,Configurations.password);
        adminTab.NavToAdminPage();
        GeneralMethods.WaitTOSee(2000);
    }

    @Test (priority = 2)
    public void GetTheNumberOfRecords() throws InterruptedException {
        login.LoginWithCred(Configurations.userName,Configurations.password);
        adminTab.NavToAdminPage();
        GeneralMethods.WaitTOSee(1000);
        System.out.println(adminTab.GetTheNumberOfRecords());
        System.out.println(adminTab.GetTheNumberOfUsers());
        assertEquals(adminTab.IsTheNumberOfRecordsEqualToTheNumberOfUsers(),true,"users lists != number of records");
    }
    @Test (priority = 3)
    public void GetTheNumberOUsers() throws InterruptedException {
        login.LoginWithCred(Configurations.userName,Configurations.password);
        adminTab.NavToAdminPage();
        GeneralMethods.WaitTOSee(1000);
        System.out.println(adminTab.GetTheNumberOfUsers());
    }
    @Test (priority = 4)
    public void CheckIfTheNumberOfRecordsEqualToTheNumberOfUsers() throws InterruptedException {
        login.LoginWithCred(Configurations.userName,Configurations.password);
        adminTab.NavToAdminPage();
        GeneralMethods.WaitTOSee(1000);
        assertEquals(adminTab.IsTheNumberOfRecordsEqualToTheNumberOfUsers(),true,"users lists != number of records");
    }

    @Test (priority = 5)
    public void AddUser() throws InterruptedException {
        login.LoginWithCred(Configurations.userName,Configurations.password);
        adminTab.NavToAdminPage();
        GeneralMethods.WaitTOSee(1000);
        adminTab.FillTheRequiredData(Configurations.newUserName,Configurations.newPassword);
        adminTab.SaveTheData();
        GeneralMethods.WaitTOSee(2000);
        System.out.println(adminTab.GetTheNewAddedUser(Configurations.newUserName));
        assertEquals(adminTab.GetTheNewAddedUser(Configurations.newUserName),Configurations.newUserName,"error");
        GeneralMethods.WaitTOSee(2000);
    }
    @Test (priority = 6)
    public void CheckThatThenumberofRecordsIncreasedBy1() throws InterruptedException {
        login.LoginWithCred(Configurations.userName,Configurations.password);
        adminTab.NavToAdminPage();
        GeneralMethods.WaitTOSee(1000);
         int theNumberOfUserBeforeAdding = adminTab.CheckIncreasingOrDecreasingNumberOfUsersByOne();
        System.out.println("the number of user before adding : "+theNumberOfUserBeforeAdding);
        adminTab.FillTheRequiredData(Configurations.newUserName,Configurations.newPassword);
        adminTab.SaveTheData();
        GeneralMethods.WaitTOSee(2000);
        System.out.println(adminTab.GetTheNewAddedUser(Configurations.newUserName));
        GeneralMethods.WaitTOSee(2000);
        System.out.println("the number of user after adding : "+adminTab.CheckIncreasingOrDecreasingNumberOfUsersByOne());
        assertEquals(adminTab.CheckIncreasingOrDecreasingNumberOfUsersByOne(),theNumberOfUserBeforeAdding+1,"failed to add new user");
    }

    @Test (priority = 7)
    public void SearchOnUser() throws InterruptedException {
        login.LoginWithCred(Configurations.userName,Configurations.password);
        adminTab.NavToAdminPage();
        adminTab.FillTheRequiredData(Configurations.newUserName,Configurations.newPassword);
        adminTab.SaveTheData();
        GeneralMethods.WaitTOSee(4000);
        adminTab.SearchOnNewUser(Configurations.newUserName);
        GeneralMethods.WaitTOSee(2000);
        assertEquals(adminTab.GetTheNewAddedUser(Configurations.newUserName),Configurations.newUserName,"result is wrong");
    }

    @Test (priority = 8)
    public void DeleteNewUser() throws InterruptedException {
        login.LoginWithCred(Configurations.userName,Configurations.password);
        adminTab.NavToAdminPage();
        adminTab.FillTheRequiredData(Configurations.newUserName,Configurations.newPassword);
        adminTab.SaveTheData();
        GeneralMethods.WaitTOSee(4000);
        adminTab.DeleteTheNewAddedUser(Configurations.newUserName);
        GeneralMethods.WaitTOSee(2000);
    }
    @Test (priority = 9)
    public void CheckThatThenumberofRecordsDecreasedBy1() throws InterruptedException {
        login.LoginWithCred(Configurations.userName,Configurations.password);
        adminTab.NavToAdminPage();
        GeneralMethods.WaitTOSee(1000);
        int theNumberOfUserBeforeAdding = adminTab.CheckIncreasingOrDecreasingNumberOfUsersByOne();
        System.out.println("the number of user before adding : "+theNumberOfUserBeforeAdding);
        adminTab.FillTheRequiredData(Configurations.newUserName,Configurations.newPassword);
        adminTab.SaveTheData();
        GeneralMethods.WaitTOSee(4000);
        int theNumberOfUserAfterAdding = adminTab.CheckIncreasingOrDecreasingNumberOfUsersByOne();
        System.out.println("the number of user after adding : "+theNumberOfUserAfterAdding);
        adminTab.DeleteTheNewAddedUser(Configurations.newUserName);
        System.out.println("the number of user after deletion : "+adminTab.CheckIncreasingOrDecreasingNumberOfUsersByOne());
        assertEquals(adminTab.CheckIncreasingOrDecreasingNumberOfUsersByOne(),theNumberOfUserAfterAdding-1,"failed to add new user");
    }


}