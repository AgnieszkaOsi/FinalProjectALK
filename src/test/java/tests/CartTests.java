package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Locale;

public class CartTests {
    private WebDriver driver;
    private Faker faker;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://fakestore.testelka.pl");
        driver.manage().window().maximize();
        faker = new Faker(Locale.CANADA);
        registerUser();
    }

    private void registerUser() {
        driver.findElement(By.cssSelector("#menu-item-201")).click();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(12, 14);
        driver.findElement(By.xpath("//a[@href='#']")).click();
        driver.findElement(By.id("reg_email")).sendKeys(email);
        driver.findElement(By.id("reg_password")).sendKeys(password);
        driver.findElement(By.name("register")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#post-8 > div > div > div > p:nth-child(2)")));
        driver.findElement(By.xpath("//a[@href='https://fakestore.testelka.pl']")).click();
    }

    @AfterClass
    public void cleanUp() {
        //driver.quit();
    }

    @Test(testName = "Addind first item to the cart.")
    public void addingFirstItemToTheCart() {
        // given
        // when
        driver.findElement(By.xpath("//a[@aria-label='Przejdź do kategorii produktu Windsurfing']")).click();
        driver.findElement(By.xpath("//a[@href='?add-to-cart=389']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Zobacz koszyk']")));
        driver.findElement(By.xpath("//a[@title='Zobacz koszyk']")).click();

        //then
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]")));
        var confirmationFirst = driver.findElement(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]"));
        Assert.assertTrue(confirmationFirst.getText().startsWith("Wyspy"));
    }


    @Test(testName = "Addind second item to the cart.")
    public void addingSecondItemToTheCart() {
        //given //when
        driver.findElement(By.xpath("//a[@href='https://fakestore.testelka.pl']")).click();
        driver.findElement(By.xpath("//a[@aria-label='Przejdź do kategorii produktu Wspinaczka']")).click();
        driver.findElement(By.xpath("//a[@href='?add-to-cart=40']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Zobacz koszyk']")));
        driver.findElement(By.xpath("//a[@title='Zobacz koszyk']")).click();

        //then
        var confirmationSecond = driver.findElement(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wspinaczka-via-ferraty/'])[2]"));
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wspinaczka-via-ferraty/'])[2]")));
        Assert.assertTrue(confirmationSecond.getText().startsWith("Wspinaczka"));

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]")));
        var confirmationFirst = driver.findElement(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]"));
        Assert.assertTrue(confirmationFirst.getText().startsWith("Wyspy"));
    }


    @Test(testName = "Delete product from the cart")
    public void deleteProductFromTheCart() {
        driver.findElement(By.id("menu-item-197")).click();
        driver.findElement(By.xpath("//a[@aria-label='Przejdź do kategorii produktu Windsurfing']")).click();
        driver.findElement(By.xpath("//a[@href='?add-to-cart=389']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Zobacz koszyk']")));
        driver.findElement(By.xpath("//a[@title='Zobacz koszyk']")).click();

        //then
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]")));
        var confirmationFirst = driver.findElement(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]"));
        Assert.assertTrue(confirmationFirst.getText().startsWith("Wyspy"));
    }
}
