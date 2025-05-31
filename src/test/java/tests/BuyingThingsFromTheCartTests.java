package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.model.*;

import java.util.Locale;

public class BuyingThingsFromTheCartTests {
    private WebDriver driver;
    private Faker faker;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        faker = new Faker(Locale.GERMANY);
        registerUser();
        addItemsToCart();
    }

    private void registerUser() {
        driver.get("https://fakestore.testelka.pl");
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(12, 14);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToMyAccount();
        loginPage.registerUser(email, password);
    }

    private void addItemsToCart() {
        driver.get("https://fakestore.testelka.pl");
        HomePage homePage = new HomePage(driver);
        CategoryPage categoryPage = homePage.goToWindsurfingCategory();
        categoryPage.addGreenIslandsToCart();
        CartPage cartPage =  categoryPage.goToCart();
        HomePage homePage1 = cartPage.goToHomePage();
        CategoryPage categoryPage1 = homePage1.goToClimbingCategory();
        categoryPage1.addFerratyToCart();
        categoryPage1.goToCart();
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
        String country = "Niemcy";
        String cardNumber = "4242424242424242";
        String validDate = "0429";
        String cvc = "376";

        // when
        CartPage cartPage = new CartPage(driver);
        PaymentPage paymentPage = cartPage.goToPaymentPage();
        paymentPage.setFirstNameAndLastName(name, surname);
        paymentPage.selectCountry(country);
        paymentPage.setAddress(address, zipCode, city, telephoneNumber);

        Thread.sleep(2000); //there is an issue with short page reload after setting address which blocks the element
        //with card payments and I don't know what should I wait for.
        paymentPage.setCardDetails(cardNumber, validDate, cvc);
        paymentPage.acceptTerms();
        OrderDetailsPage orderDetailsPage = paymentPage.placeOrder();

        // then
        Assert.assertTrue(orderDetailsPage.orderConfirmation().startsWith("Dziękujemy. Otrzymaliśmy Twoje zamówienie."));
    }
}
