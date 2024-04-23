import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class SourceEditorTest {
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

    @Test(priority = 30)
    public void OpenSourceEditor() throws InterruptedException {
        //Navigate to Sandbox
        driver.findElement(By.id("vector-user-links-dropdown-checkbox")).click();
        driver.findElement(By.id("pt-sandbox")).click();
        Thread.sleep(2000);
        //Open Editor
        driver.findElement(By.id("ca-edit")).click();
        Thread.sleep(2500);

        //Open Source Editor
        // Try to navigate to the source editor if we are not already on the page. If we cannot find the dropdown, we are already on the source editor page.
        if (!driver.findElements(By.xpath("//*[@id=\"bodyContent\"]/div/div[1]/div/div[2]/div[4]/span")).isEmpty()) {
            driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[1]/div/div[2]/div[4]/span")).click(); //dropdown
            driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[1]/div/div[5]/div[8]/span[2]/a/span[3]")).click(); //Source Editor
        }

        Thread.sleep(2000);
    }

    @Test(priority = 31)
    public void InsertText() throws InterruptedException, IOException {
        var textBox = driver.findElement(By.xpath("//*[@id=\"wpTextbox1\"]"));
        textBox.sendKeys("=This is a Title= \nAnd this is a wall of text to test the Wikipedia source editor. '''TRA LA LAAAAAAAAAAAAAAAAAAAA LA LA LA.'''");
        Thread.sleep(1000);

        //Click Preview
        driver.findElement(By.xpath("//*[@id=\"wikiEditor-section-secondary\"]/div/div[1]/div/span/a/span[2]"));

        var screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(screenshot, new File("SourceScreenshot.png"));
    } //End InsertText

    @Test(priority = 32)
    public void InsertSymbols() throws InterruptedException {
        var textBox = driver.findElement(By.id("wpTextbox1"));
        textBox.sendKeys(Keys.CONTROL, "a"); // select and delete all the text in the box
        textBox.sendKeys(Keys.BACK_SPACE);

        for (int i = 1; i <= 17; i++) {
            var xpath = "//*[@id=\"Insert\"]/a[" + i + "]"; // Construct the XPath for the special character buttons

            driver.findElement(By.xpath(xpath)).click(); // clicks each special character at the bottom of the editor
            Thread.sleep(250);
        }

        var boxContents = textBox.getAttribute("value");
        Assert.assertEquals(boxContents, "–—°′″≈≠≤≥±−×÷←→·§");
    } //End insertSymbols
}