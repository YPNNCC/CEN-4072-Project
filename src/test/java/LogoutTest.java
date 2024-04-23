import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class LogoutTest {
    protected WebDriver driver;

    @BeforeClass
    public void RunSetup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://en.wikipedia.org/w/index.php?returnto=Main+Page&title=Special:UserLogin&centralAuthAutologinTried=1&centralAuthError=Not+centrally+logged+in");
        Thread.sleep(1000);
    }

    @AfterClass
    public void closeDriver() {
        driver.close();
    }

    @Test(priority = 80)
    public void CorrectLogin() throws InterruptedException {
        //Tests that the login is functioning appropriately
        //---------------------------------------------------
        driver.findElement(By.id("wpName1")).sendKeys("<WikipediaUsernameHere>");
        driver.findElement(By.id("wpPassword1")).sendKeys("<WikipediaPasswordHere>");
        driver.findElement(By.id("wpLoginAttempt")).click();
        Thread.sleep(2500);
    }

    @Test(priority = 81)
    public void LogoutUser() throws InterruptedException {
        //Ensures Logout is functioning appropriately
        //---------------------------------------------------
        driver.findElement(By.id("vector-user-links-dropdown-checkbox")).click();
        driver.findElement(By.xpath("//a[@title='Log out']")).click();
        Thread.sleep(5000L);
    }
}