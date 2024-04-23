import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class LoginTest {
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

    @Test(priority = 90)
    public void IncorrectPassword() throws InterruptedException {
        //Tests incorrect Password
        //---------------------------------------------------
        driver.findElement(By.id("wpName1")).sendKeys("<WikipediaUsernameHere>"); //Username
        driver.findElement(By.id("wpPassword1")).sendKeys("<WikipediaPasswordHere>"); //Password
        driver.findElement(By.id("wpLoginAttempt")).click(); //Click Log In
        Thread.sleep(1000); // Wait for login
    }

    @Test(priority = 91)
    public void IncorrectUsername() throws InterruptedException {
        driver.get("https://en.wikipedia.org/w/index.php?returnto=Main+Page&title=Special:UserLogin&centralAuthAutologinTried=1&centralAuthError=Not+centrally+logged+in");
        Thread.sleep(1000);

        //Tests incorrect username
        driver.findElement(By.id("wpName1")).sendKeys("WrongUser"); //Username
        driver.findElement(By.id("wpPassword1")).sendKeys("<WikipediaPasswordHere>"); //Password
        driver.findElement(By.id("wpLoginAttempt")).click(); //Click Log In
        Thread.sleep(1000); // Wait for login
    }

    @Test(priority = 92)
    public void CorrectLogin() throws InterruptedException {
        //Ensures Correct Login functions appropriately
        driver.get("https://en.wikipedia.org/w/index.php?returnto=Main+Page&title=Special:UserLogin&centralAuthAutologinTried=1&centralAuthError=Not+centrally+logged+in");
        Thread.sleep(1000);

        // Login
        driver.findElement(By.id("wpName1")).sendKeys("<WikipediaUsernameHere>"); //Username
        driver.findElement(By.id("wpPassword1")).sendKeys("<WikipediaPasswordHere>"); //Password
        driver.findElement(By.id("wpLoginAttempt")).click(); //Click Log In
        Thread.sleep(2500); // Wait for login
    }
}