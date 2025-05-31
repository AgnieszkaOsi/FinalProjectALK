package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import tests.model.*;

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
    }

    private HomePage registerUser() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(12, 14);

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToMyAccount();
        MyAccountPage myAccountPage = loginPage.registerUser(email, password);

        homePage = myAccountPage.goToHomePage();
        return homePage;
    }

    @AfterClass
    public void cleanUp() {
        driver.quit();
    }

    @Test(testName = "Addind first item to the cart.")
    public void addingFirstItemToTheCart() {
        // given
        HomePage homePage = registerUser();

        // when
        CategoryPage windsurfingCategoryPage = homePage.goToWindsurfingCategory();
        windsurfingCategoryPage.addGreenIslandsToCart();

        //then
        CartPage cartPage = windsurfingCategoryPage.goToCart();
        Assert.assertTrue(cartPage.getGreenIslandsProductName().startsWith("Wyspy"));
    }

    @Test(testName = "Addind second item to the cart.")
    public void addingSecondItemToTheCart() {
        //given
        driver.get("https://fakestore.testelka.pl");
        HomePage homePage = new HomePage(driver);

        // when
        CategoryPage climbingCategoryPage = homePage.goToClimbingCategory();
        climbingCategoryPage.addFerratyToCart();

        //then
        CartPage cartPage = climbingCategoryPage.goToCart();
        Assert.assertTrue(cartPage.getGreenIslandsProductName().startsWith("Wyspy"));
        Assert.assertTrue(cartPage.getFerratyProductName().startsWith("Wspinaczka"));
    }

    @Test(testName = "Delete product from the cart")
    public void deleteProductFromTheCart() {
        //given
        driver.get("https://fakestore.testelka.pl");
        HomePage homePage = new HomePage(driver);

        //when
        CartPage cartPage = homePage.goToCart();
        cartPage.deleteIslandItemFromTheCart();

        //then
        Assert.assertTrue(cartPage.getFerratyProductName().startsWith("Wspinaczka"));
        Assert.assertTrue(cartPage.getIslandsRemovedConfirmation().contains("Wyspy"));
        Assert.assertTrue(cartPage.getIslandsRemovedConfirmation().startsWith("Usunięto"));
    }
}
