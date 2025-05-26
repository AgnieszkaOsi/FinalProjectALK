package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Locale;

public class LogOutTests {
    private WebDriver driver;
    private Faker faker;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://fakestore.testelka.pl");
        driver.manage().window().maximize();
        faker = new Faker(Locale.CANADA);
    }

    @AfterMethod
    public void cleanUp() {
        //driver.quit();
    }

    @Test(testName = "Log out user")
    public void logOutUser() {
        //given
        driver.findElement(By.cssSelector("#menu-item-201")).click();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(12, 14);
        driver.findElement(By.id("reg_email")).sendKeys(email);
        driver.findElement(By.id("reg_password")).sendKeys(password);
        driver.findElement(By.xpath("//a[@href='#']")).click();
        driver.findElement(By.name("register")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#post-8 > div > div > div > p:nth-child(2)")));

        //when
        driver.findElement(By.cssSelector("#post-8 > div > div > div > p:nth-child(2) > a")).click();

        //then
        driver.findElement(By.cssSelector("[aria-current='page']")).click();
        var confirmationFirst = driver.findElement(By.cssSelector("[class='u-column1 col-1']"));
        Assert.assertTrue(confirmationFirst.getText().startsWith("Zaloguj się"));
        var confirmationSecond = driver.findElement(By.cssSelector("[class='u-column2 col-2']"));
        Assert.assertTrue(confirmationSecond.getText().startsWith("Zarejestruj się"));
    }
}
