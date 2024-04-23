import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class SearchTest {
    protected WebDriver driver;

    @BeforeClass
    public void LaunchSearch() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        Thread.sleep(1000);
    }

    @AfterClass
    public void closeDriver() {
        driver.close();
    }

    @Test(priority = 20)
    public void CustomSearchTest() throws InterruptedException {
        //Tests searching for a specific article
        //---------------------------------------------------
        var searchBox = driver.findElement(By.xpath("//*[@id=\"searchInput\"]"));

        searchBox.sendKeys("Java (Programming Language)");
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
    }

    @Test(priority = 21)
    public void RandomPageTest() throws InterruptedException {
        //Tests the random page functionality
        //---------------------------------------------------
        driver.findElement(By.xpath("//*[@id=\"vector-main-menu-dropdown-checkbox\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"n-randompage\"]/a")).click();
        Thread.sleep(2000);
    }
}