import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class WatchlistTest {
    protected WebDriver driver;

    @BeforeClass
    public void Login() throws InterruptedException {
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
    }

    @AfterClass
    public void closeDriver() {
        driver.close();
    }

    @Test(priority = 50)
    public void CustomSearchTest() throws InterruptedException {
        var searchBox = driver.findElement(By.xpath("//*[@id=\"searchInput\"]"));

        searchBox.sendKeys("Java (Programming Language)", Keys.ENTER);
        Thread.sleep(3000);

        driver.findElement(By.xpath("/html/body")).sendKeys(Keys.ALT, Keys.SHIFT, "w");
        Thread.sleep(1000);
    }

    @Test(priority = 51)
    public void SomeWatchlist() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"pt-watchlist-2\"]/a")).click(); // click on watchlist
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div[2]/div/div/div[1]/div/div[1]/div/div[2]/span/a")).click(); // Click on "Edit your list of watched pages"
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"ooui-php-2\"]")).click(); // Click on "Check all" to select all articles
        driver.findElement(By.xpath("//*[@id=\"ooui-php-15\"]/button")).click(); // Click on "Remove titles"
    }
}