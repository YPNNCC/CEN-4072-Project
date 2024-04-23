import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class HistoryTest {
    protected WebDriver driver;

    @BeforeClass
    public void RunSetup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://en.wikipedia.org/w/index.php?returnto=Main+Page&title=Special:UserLogin&centralAuthAutologinTried=1&centralAuthError=Not+centrally+logged+in");
        Thread.sleep(1000);
        //Login
        driver.findElement(By.id("wpName1")).sendKeys("<WikipediaUsernameHere>");
        driver.findElement(By.id("wpPassword1")).sendKeys("<WikipediaPasswordHere>");
        Thread.sleep(5000); // time for solving captcha if I need to
        driver.findElement(By.id("wpLoginAttempt")).click();
        Thread.sleep(1000);

        //Navigate to Sandbox
        driver.findElement(By.id("vector-user-links-dropdown-checkbox")).click();
        driver.findElement(By.id("pt-sandbox")).click();
        Thread.sleep(2000);
    }

    @AfterClass
    public void closeDriver() {
        driver.close();
    }

    @Test(priority = 1)
    public void viewHistory() throws InterruptedException {
        //Navigate to the history page and view an old edit of the page
        driver.findElement(By.xpath("//*[@id=\"ca-history\"]/a/span")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("19:24, 10 April 2024")).click(); //follow link for specific date
        Thread.sleep(2000);
        driver.findElement(By.linkText("Latest revision")).click();
        Thread.sleep(2000);

    }//End viewHistory

    @Test(priority = 2)
    public void PermaLink() throws InterruptedException {
        //scroll through 5 past alterations of the page on the permanent link page
        driver.findElement(By.linkText("Permanent link")).click();
        Thread.sleep(2000);

        for(int i = 0; i < 5; i++)
        {
            driver.findElement(By.partialLinkText("Previous revision")).click();
            Thread.sleep(2000);
        }
    }// End permaLink
}