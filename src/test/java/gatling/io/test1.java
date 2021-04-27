package gatling.io;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;


public class test1 {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void addItemTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("http://computer-database.gatling.io/computers");
        WebElement addBtn = driver.findElement(By.xpath("//a[@href='/computers/new']"));
        addBtn.click();

        String computer = "KEK";

        WebElement nameBox;
        nameBox = wait.until(presenceOfElementLocated(By.xpath("//*[@id='name']")));
        nameBox.sendKeys(computer);
        WebElement introducedBox;
        introducedBox = wait.until(presenceOfElementLocated(By.xpath("//input[@id='introduced']")));
        introducedBox.sendKeys("2011-04-26");
        WebElement discontinuedBox;
        discontinuedBox = wait.until(presenceOfElementLocated(By.xpath("//input[@id='discontinued']")));
        discontinuedBox.sendKeys("2020-04-26");

        WebElement companyDrp;
        companyDrp = wait.until(presenceOfElementLocated(By.xpath("//*[@id='company']")));
        Select company = new Select(companyDrp);
        company.selectByValue("13");

        WebElement createBtn;
        createBtn = wait.until(elementToBeClickable(By.xpath("//input[@type='submit']")));
        createBtn.click();


        WebElement searchFld;
        searchFld = wait.until(presenceOfElementLocated(By.xpath("//input[@id='searchbox']")));
        searchFld.sendKeys(computer);

        WebElement filterBtn;
        filterBtn= wait.until(elementToBeClickable(By.xpath("//input[@id='searchsubmit']")));
        filterBtn.click();

        WebElement title;
        title = wait.until(presenceOfElementLocated(By.linkText(computer)));

        Assert.assertTrue(title.isDisplayed(), "Created computer name not displayed");

    }
}