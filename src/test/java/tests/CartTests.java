package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
        loginPage.closeBanner();
        MyAccountPage myAccountPage = loginPage.registerUser(email, password);

        homePage = myAccountPage.goToHomePage();
        return homePage;
    }

    @AfterClass
    public void cleanUp() {
        //driver.quit();
    }

    @Test(testName = "Addind first item to the cart.")
    public void addingFirstItemToTheCart() {
        // given
        HomePage homePage = registerUser();

        // when
        WindsurfingCategoryPage windsurfingCategoryPage = homePage.goToWindsurfingCategory();
        windsurfingCategoryPage.addToCart();

        //then
        CartPage cartPage = windsurfingCategoryPage.goToCart();
        cartPage.assertThatIslandsAreInTheCart();
    }

    @Test(testName = "Addind second item to the cart.")
    public void addingSecondItemToTheCart() {
        //given
        driver.get("https://fakestore.testelka.pl");
        HomePage homePage = new HomePage(driver);

        // when
        ClimbingCategoryPage climbingCategoryPage = homePage.goToClimbingCategory();
        climbingCategoryPage.addToCart();

        //then
        CartPage cartPage = climbingCategoryPage.goToCart();
        cartPage.assertThatIslandsAreInTheCart();
        cartPage.asserThatClimbingIsInTheCart();
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
        cartPage.asserThatClimbingIsInTheCart();
        cartPage.assertThatIslandsAreNotInTheCart();
    }
}
