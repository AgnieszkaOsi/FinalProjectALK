package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.model.HomePage;
import tests.model.LoginPage;
import tests.model.MyAccountPage;

import java.util.Locale;

public class LogOutTests {
    private static final Logger log = LoggerFactory.getLogger(LogOutTests.class);
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

    @Test(testName = "Log out user")
    public void logOutUser() throws InterruptedException {
        //given
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(12, 14);

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToMyAccount();
        loginPage.tryToCloseBlueBanner();
        MyAccountPage myAccountPage = loginPage.registerUser(email, password);

        //when
        loginPage = myAccountPage.logOut();

        //then
        Assert.assertTrue(loginPage.getLoginButton().startsWith("Zaloguj się"));
        Assert.assertTrue(loginPage.getRegistrationButton().startsWith("Zarejestruj się"));
    }

    @Test(testName = "User can log in after log out")
    public void userCanLoginAfterLogOut() throws InterruptedException {
        //given
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(12, 14);
        String expectedUsername = email.substring(0, email.indexOf("@"));

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToMyAccount();
        loginPage.tryToCloseBlueBanner();
        MyAccountPage myAccountPage = loginPage.registerUser(email, password);
        loginPage = myAccountPage.logOut();

        //when
        myAccountPage = loginPage.loginUser(email, password);

        //then
        Assert.assertTrue(myAccountPage.getHelloUserText().startsWith("Witaj"));
        Assert.assertTrue(myAccountPage.getHelloUserText().contains(expectedUsername));
    }
}
