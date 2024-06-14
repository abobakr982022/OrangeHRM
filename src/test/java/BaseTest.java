import TestData.Configurations;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver driver;


    public BaseTest() {
    }


    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //Navigate to Examiner Portal
        driver.get(Configurations.Portal);
    }

    @AfterClass
    public void teardown()
    {
        driver.quit();
    }

}
