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

public class BuyingThingsFromTheCart {
    private WebDriver driver;
    private Faker faker;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://fakestore.testelka.pl");
        driver.manage().window().maximize();
        faker = new Faker(Locale.GERMANY);
        registerUser();
        addItemsToCart();
    }

    private void registerUser() {
        driver.findElement(By.cssSelector("#menu-item-201")).click();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(12, 14);
        driver.findElement(By.xpath("//a[@href='#']")).click();
        driver.findElement(By.id("reg_email")).sendKeys(email);
        driver.findElement(By.id("reg_password")).sendKeys(password);
        driver.findElement(By.name("register")).click();
    }


    private void addItemsToCart() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#post-8 > div > div > div > p:nth-child(2)")));
        driver.findElement(By.xpath("//a[@href='https://fakestore.testelka.pl']")).click();
        driver.findElement(By.xpath("//a[@aria-label='Przejdź do kategorii produktu Windsurfing']")).click();
        driver.findElement(By.xpath("//a[@href='?add-to-cart=389']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Zobacz koszyk']")));
        driver.findElement(By.xpath("//a[@title='Zobacz koszyk']")).click();
        driver.findElement(By.xpath("//a[@href='https://fakestore.testelka.pl']")).click();
        driver.findElement(By.xpath("//a[@aria-label='Przejdź do kategorii produktu Wspinaczka']")).click();
        driver.findElement(By.xpath("//a[@href='?add-to-cart=40']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Zobacz koszyk']")));
        driver.findElement(By.xpath("//a[@title='Zobacz koszyk']")).click();
    }


    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test(testName = "Buying things from the cart.")
    public void buyingThingsFromTheCart() throws InterruptedException {
        //given
        String name = faker.name().firstName();
        String surname = faker.name().lastName();
        String address = faker.address().streetAddress();
        String zipCode = faker.address().zipCode();
        String city = faker.address().cityName();
        while (zipCode.contains("0")) {
            zipCode = faker.address().zipCode();
        }
        String telephoneNumber = faker.phoneNumber().phoneNumber();


        // when
        driver.findElement(By.cssSelector("[class='checkout-button button alt wc-forward']")).click();
        driver.findElement(By.id("billing_first_name")).sendKeys(name);
        driver.findElement(By.id("billing_last_name")).sendKeys(surname);

        driver.findElement(By.cssSelector("[class='select2-selection__arrow']")).click();
        driver.findElement(By.cssSelector("[class='select2-search__field']")).sendKeys("Niemcy");
        driver.findElement(By.cssSelector("[class='select2-results__option select2-results__option--highlighted']")).click();

        driver.findElement(By.id("billing_address_1")).sendKeys(address);
        driver.findElement(By.id("billing_postcode")).sendKeys(zipCode);
        driver.findElement(By.id("billing_city")).sendKeys(city);
        driver.findElement(By.id("billing_phone")).sendKeys(telephoneNumber);


        Thread.sleep(2000);
        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@allow='payment *'])[1]")));
        driver.findElement(By.name("cardnumber")).sendKeys("4242424242424242");
        driver.switchTo().defaultContent();

        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@allow='payment *'])[2]")));
        driver.findElement(By.name("exp-date")).sendKeys("0429");
        driver.switchTo().defaultContent();

        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@allow='payment *'])[3]")));
        driver.findElement(By.name("cvc")).sendKeys("376");
        driver.switchTo().defaultContent();

        driver.findElement(By.id("terms")).click();
        driver.findElement(By.id("place_order")).click();


        // then
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='woocommerce-notice woocommerce-notice--success woocommerce-thankyou-order-received']")));
        var confirmation = driver.findElement(By.cssSelector("[class='woocommerce-notice woocommerce-notice--success woocommerce-thankyou-order-received']"));
        Assert.assertTrue(confirmation.getText().startsWith("Dziękujemy. Otrzymaliśmy Twoje zamówienie."));
    }
}
