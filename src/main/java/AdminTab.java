import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class AdminTab {
    private static WebDriver driver;

    public AdminTab(WebDriver driver) {this.driver = driver;}


    // ---------------Locators -----------------------------------------
    private By adminTab= By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name']");

    private By theNumberOfRecords = By.xpath("//span[@class='oxd-text oxd-text--span']");

    private By theNumberOfUsers = By.xpath("//div[@class='oxd-table-body']");

    private By addButton = By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--secondary']");

    private By arrowOfUserRoleField = By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']");
    private By selectOptionFromUserRoleField =By.xpath("//div[@role='listbox'][position()=1]");

    private By employeeName = By.xpath("//input[@placeholder='Type for hints...']");

    private By userName = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");

    private By arrowOfStatusField = By.xpath("(//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'])[2]");

    //this Xpath isn't the best practice , it needs enhancement
    private By selectOptionFromStatusField = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[3]/div/div[2]/div/div[2]/div[2]");

    private By password = By.xpath("(//input[@type='password'])[1]");

    private By confirmPassword = By.xpath("(//input[@type='password'])[2]");

    private By saveButton = By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']");

    private By newAddedUser = By.xpath("(//div[@class='oxd-table-card'])[1]");

    private By searchUserField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");

    private By searchButton = By.xpath("//div[@class='oxd-form-actions']//button[2]");

    private By deleteConfirmation = By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']");



    //-------------Methods-------------------------
    public void NavToAdminPage(){
        driver.findElement(adminTab).click();
    }

    public String GetTheNumberOfRecords(){
        return driver.findElement(theNumberOfRecords).getText();
    }

    public String GetTheNumberOfUsers(){
        WebElement parent = driver.findElement(theNumberOfUsers);
        List<WebElement> numberOfUsers = parent.findElements(By.xpath("./*"));
        String getTheNumberOfUsers =String.valueOf(numberOfUsers.size());
        return getTheNumberOfUsers;
    }

    public boolean IsTheNumberOfRecordsEqualToTheNumberOfUsers(){
        if(GetTheNumberOfRecords().contains(GetTheNumberOfUsers())){
            return true;
        }
        else return false;
    }

    //This method to get the child tags for the parent (DDL of UserRole)
    public List<String> getChildTagsOfUserRole() throws InterruptedException {

        List<String> childTags = new ArrayList<>();

        // Find all child elements of the parent element
        List<WebElement> childElements = driver.findElement(By.xpath("//div[@role='listbox']")).findElements(By.xpath("*"));

        // Iterate over the list of child elements and extract their tag names
        for (WebElement child : childElements) {
            // Get the tag name of each child element
            String tagName = child.getTagName();
            childTags.add(tagName);
        }

        return childTags;
    }

    public void SelectOptionFromUserRoleField() throws InterruptedException {
        driver.findElement(addButton).click();
        driver.findElement(arrowOfUserRoleField).click();
        System.out.println("The Child Tags Name: " + getChildTagsOfUserRole());
        Thread.sleep(1500);
        driver.findElement(selectOptionFromUserRoleField).click();
    }
    public void SelectOptionFromStatusField() throws InterruptedException {
        driver.findElement(arrowOfStatusField).click();
        System.out.println(driver.findElement(selectOptionFromStatusField).getTagName());
        Thread.sleep(1500);
        driver.findElement(selectOptionFromStatusField).click();
    }

    public void EmployeeNameAndUserNameAndPasswordAndConfirmPassword(String user,String pass) throws InterruptedException {
        driver.findElement(employeeName).sendKeys("e");
        Thread.sleep(3000);
        driver.findElement(employeeName).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
        driver.findElement(userName).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(confirmPassword).sendKeys(pass);
    }



    public void FillTheRequiredData (String user,String pass) throws InterruptedException {
        SelectOptionFromUserRoleField();
        SelectOptionFromStatusField();
        EmployeeNameAndUserNameAndPasswordAndConfirmPassword(user,pass);

    }
    public void SaveTheData (){
        driver.findElement(saveButton).click();
    }

    public String GetTheNewAddedUser(String addedNewUser){
        FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(" //div[@class='oxd-table-card']//div[contains(text(),'" + addedNewUser +"')]")));
        return driver.findElement(By.xpath(" //div[@class='oxd-table-card']//div[contains(text(),'" + addedNewUser +"')]")).getText();

    }
    public int CheckIncreasingOrDecreasingNumberOfUsersByOne(){
        WebElement parent = driver.findElement(theNumberOfUsers);
        List<WebElement> numberOfUsers = parent.findElements(By.xpath("./*"));
        int getTheNumberOfUsers =numberOfUsers.size();
        return getTheNumberOfUsers;
    }

    public void SearchOnNewUser(String user) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10 seconds
        // Wait until the element is visible and clickable
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(searchUserField).sendKeys(user);
        driver.findElement(searchButton).click();
    }
    public void DeleteTheNewAddedUser(String addedNewUser) throws InterruptedException {
        FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='oxd-table-card']//div[contains(text(),'"+addedNewUser+"')]/../../div[last()]/div/button[1]")));
        driver.findElement(By.xpath("//div[@class='oxd-table-card']//div[contains(text(),'"+addedNewUser+"')]/../../div[last()]/div/button[1]")).click();
        driver.findElement(deleteConfirmation).click();
        Thread.sleep(2000);

    }
}
