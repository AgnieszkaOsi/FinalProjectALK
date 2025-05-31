package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import tests.model.HomePage;
import tests.model.LoginPage;
import tests.model.MyAccountPage;

import java.util.Locale;

public class LoginAndRegistrationTests {
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
        driver.quit();
    }

    @Test(testName = "Should Not Allow Unregister User To Login")
    public void shouldNotAllowUnregisterUserToLogin() {
        //given
        String notExistingUsername = faker.name().username();
        String password = faker.random().hex();

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToMyAccount();

        //when
        loginPage.loginUserWithError(notExistingUsername, password);

        //then
        Assert.assertTrue(loginPage.getLoginErrorInformation().contains(notExistingUsername));
        Assert.assertTrue(loginPage.getLoginErrorInformation().startsWith("Błąd"));
    }

    @Test(testName = "New user registration")
    public void newUserRegistration() {
        //given
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(12, 14);
        String expectedUsername = email.substring(0, email.indexOf("@"));

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToMyAccount();
        loginPage.tryToCloseBlueBanner();

        //when
        MyAccountPage myAccountPage = loginPage.registerUser(email, password);

        //then
        Assert.assertTrue(myAccountPage.getHelloUserText().startsWith("Witaj"));
        Assert.assertTrue(myAccountPage.getHelloUserText().contains(expectedUsername));
    }

    @Test(testName = "New user registration fails when password is too short")
    public void newUserRegistrationFailsWhenPasswordIsTooShort() {
        //given
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(4, 5);

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToMyAccount();

        //when
        loginPage.registerUserWithWrongPassword(email, password);

        //then
        Assert.assertTrue(loginPage.getErrorToWeakPassword().contains("Proszę wpisać mocniejsze hasło"));
    }

    @Test(testName = "New user registration fails when user is already registered")
    public void newUserRegistrationFailsWhenUserIsAlreadyRegistered() {
        //given
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(12, 14);

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToMyAccount();
        loginPage.tryToCloseBlueBanner();
        MyAccountPage myAccountPage = loginPage.registerUser(email, password);
        loginPage = myAccountPage.logOut();

        //when
        loginPage.registerDuplicateUser(email, password);

        //then
        Assert.assertTrue(loginPage.getErrorAccountExist().startsWith("Błąd:"));
    }
}