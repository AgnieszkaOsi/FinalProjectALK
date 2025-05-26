package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.model.HomePage;
import tests.model.LoginPage;
import tests.model.MyAccountPage;

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
        driver.quit();
    }

    @Test(testName = "Log out user")
    public void logOutUser() {
        //given
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(12, 14);

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToMyAccount();
        MyAccountPage myAccountPage = loginPage.registerUser(email, password);

        //when
        loginPage = myAccountPage.logOut();

        //then
        loginPage.assertThatLoginIsVisible();
        loginPage.assertThatRegisterIsVisible();
    }
}
