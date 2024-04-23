import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class ProfileTest {
    protected WebDriver driver;

    @BeforeClass
    public void LaunchSearch() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://en.wikipedia.org/w/index.php?returnto=Main+Page&title=Special:UserLogin&centralAuthAutologinTried=1&centralAuthError=Not+centrally+logged+in");
        Thread.sleep(1000);

        //Login
        driver.findElement(By.id("wpName1")).sendKeys("<WikipediaUsernameHere>");
        driver.findElement(By.id("wpPassword1")).sendKeys("<WikipediaPasswordHere>");
        Thread.sleep(4000); // time for solving captcha if I need to
        driver.findElement(By.id("wpLoginAttempt")).click();
        Thread.sleep(2500);

        driver.findElement(By.id("vector-user-links-dropdown-checkbox")).click();//user dropdown
        Thread.sleep(2500);
        driver.findElement(By.xpath("//*[@id=\"pt-preferences\"]/a/span[2]")).click(); //preferences
        Thread.sleep(2500);
        driver.findElement(By.xpath("//*[@id=\"ooui-php-514\"]")).click(); // "Recent Changes" Tab
        Thread.sleep(2500);
    }

    @AfterClass
    public void closeDriver() {
        driver.close();
    }

    @Test(priority = 10)
    public void changeToThirty() throws InterruptedException {
        // Changes a value in settings and saves it
        //---------------------------------------------------
        var recentChangesTextBox = driver.findElement(By.id("ooui-php-174")); // "Days to show in recent changes"
        recentChangesTextBox.clear();
        recentChangesTextBox.sendKeys("30");
        recentChangesTextBox.sendKeys(Keys.ENTER);
        Thread.sleep(2500);
    }

    @Test(priority = 11)
    public void changeToSeven() throws InterruptedException {
        // Change the value back to 7 on refresh
        //---------------------------------------------------
        var recentChangesTextBox = driver.findElement(By.id("ooui-php-174")); // "Days to show in recent changes"
        recentChangesTextBox.clear();
        recentChangesTextBox.sendKeys("7");
        recentChangesTextBox.sendKeys(Keys.ENTER);
        Thread.sleep(2500);
    }
}