import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.function.BiConsumer;

public class VisualEditorTest {
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

        //Navigate to Sandbox
        driver.findElement(By.id("vector-user-links-dropdown-checkbox")).click();
        driver.findElement(By.id("pt-sandbox")).click();
        Thread.sleep(2000);
        //Open Editor
        driver.findElement(By.id("ca-edit")).click();
        Thread.sleep(2000);

        // Try to navigate to the source editor if we are not already on the page. If we cannot find the dropdown, we are already on the source editor page.
        if (!driver.findElements(By.xpath("//*[@id=\"wikiEditor-section-secondary\"]/div/div[2]/div/div[1]/div[1]/div")).isEmpty()) {
            driver.findElement(By.xpath("//*[@id=\"wikiEditor-section-secondary\"]/div/div[2]/div/div[1]/div[1]/div")).click(); //dropdown
            driver.findElement(By.xpath("//*[@id=\"wikiEditor-section-secondary\"]/div/div[2]/div/div[2]/div/span[1]/a")).click(); //Source Editor
        }

        Thread.sleep(2500);
    }

    @AfterClass
    public void closeDriver() {
        driver.close();
    }

    @Test(priority = 40)
    public void InsertTitle() throws InterruptedException {
        //Inserts Title and adds a link
        //---------------------------------------------------
        //Open Visual Editor
        var textBox = driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[2]/div[4]/div[1]/div[1]")); //textbox
        textBox.sendKeys(Keys.CONTROL, "a"); // select and delete all the text in the box
        textBox.sendKeys(Keys.BACK_SPACE);

        //Dropdown and textbox webelements
        var dropdownFormat = driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[1]/div/div[1]/div[2]"));//text format
        dropdownFormat.click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[1]/div/div[5]/div[1]/span[11]/a")).click(); //Page Title
        Thread.sleep(1000);

        //Type Page Title
        var textBox2 = driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[2]/div[4]/div[1]/div[1]/h1")); //textbox
        textBox2.sendKeys("Nick & William's Testing Project");
        textBox2.sendKeys(Keys.ENTER);
        // textBox.sendKeys(Keys.CONTROL, "I");
        Thread.sleep(250);

        //Open text style dropdown then select Italic then insert redirect link
        var dropdownStyle = driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[1]/div/div[1]/div[3]/span/span[3]")); //text style
        dropdownStyle.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[1]/div/div[5]/div[2]/span[2]/a/span[3]")).click();//Italic
        Thread.sleep(1000);

        textBox2.sendKeys("For alternate definitions, see ");
        Thread.sleep(250);

        textBox2.sendKeys(Keys.CONTROL, "k");
        Thread.sleep(1250);
        // send keys wherever the cursor is currently at
        driver.findElement(By.xpath("//*[@id=\"ooui-19\"]/div/div/div/div/div/div[1]/div/input")).sendKeys("Perfection", Keys.ENTER);
        Thread.sleep(1250);

        textBox2.sendKeys(Keys.ARROW_RIGHT);
        textBox2.sendKeys(Keys.ENTER);
        Thread.sleep(2500);
    }//End InsertTitle

    @Test(priority = 41)
    public void InsertParagraph() throws InterruptedException, IOException {
        //Inserts a paragraph and formats some text as bold
        //---------------------------------------------------

        BiConsumer<WebElement, String> toggleBold = (textbox, text) -> {
            var dropdownStyle = driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[1]/div/div[1]/div[3]/span/span[3]")); //text style
            var boldText = driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[1]/div/div[5]/div[2]/span[1]/a/span[3]"));

            dropdownStyle.click();
            boldText.click();

            textbox.sendKeys(text);

            dropdownStyle.click();
            boldText.click();
        };

        var textBox = driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[2]/div[4]/div[1]/div[1]/h1")); //textbox
        textBox.sendKeys("Commonly regarded as one of the best demonstrations of automation comprehension, it was a joint effort by ");
        toggleBold.accept(textBox, "Nick Chlumsky");
        textBox.sendKeys(" and ");
        toggleBold.accept(textBox, "William Dragstrem. ");
        textBox.sendKeys("The project was created to satisfy the requirements for their final project in Professor Deepa Devasenapathy's Software testing Class at Florida Gulf Coast University. ");
        textBox.sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        // Publish
        driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div/div[1]/div/div[2]/div[5]/div/span/a")).click();
        driver.findElement(By.xpath("//*[@id=\"mw-teleport-target\"]/div/div/div/div[1]/div[2]/div[1]/div/div[1]/span/a")).click();
        Thread.sleep(2000);

        var screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(screenshot, new File("VisualScreenshot.png"));
    }// End insertParagraph

    //Insert Image test?
    //Publish Changes
}