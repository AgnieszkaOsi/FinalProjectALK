package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private static final By MY_ACOUNT_HEADER = By.className("entry-title");
    private static final By BLUE_BANNER = By.xpath("//a[@href='#']");
    private static final By REGISTRATION_EMAIL_TEXTBOX = By.id("reg_email");
    private static final By REGISTRATION_PASSWORD_TEXTBOX = By.id("reg_password");
    private static final By REGISTRATION_BUTTON = By.name("register");
    private static final By WELCOME_TEXT = By.cssSelector("#post-8 > div > div > div > p:nth-child(2)");
    private static final By LOGIN_USERNAME_TEXTBOX = By.cssSelector("#username");
    private static final By LOGIN_PASSWORD_TEXTBOX = By.id("password");
    private static final By LOGIN_BUTTON = By.name("login");
    private static final By LOGIN_ERROR_INFORMATION = By.cssSelector("#content > div > div.woocommerce > ul > li");
    private static final By ERROR_TOO_WEAK_PASSWORD = By.id("password_strength");
    private static final By ERROR_ACCOUNT_EXIST = By.className("woocommerce-error");






    public LoginPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(MY_ACOUNT_HEADER).getText().contains("Moje konto"));
    }

    public void loginUserWithError(String username, String password) {
        driver.findElement(LOGIN_USERNAME_TEXTBOX).sendKeys(username);
        driver.findElement(LOGIN_PASSWORD_TEXTBOX).sendKeys(password);
        driver.findElement(BLUE_BANNER).click();
        driver.findElement(LOGIN_BUTTON).click();
    }

    public MyAccountPage loginUser(String username, String password) throws InterruptedException {
        driver.findElement(LOGIN_USERNAME_TEXTBOX).sendKeys(username);
        driver.findElement(LOGIN_PASSWORD_TEXTBOX).sendKeys(password);
        driver.findElement(By.name("login")).click();
        Thread.sleep(1000);

        return new MyAccountPage(driver);
    }

    public void assertThatThereIsAnError(String username) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(LOGIN_ERROR_INFORMATION));
        var error = driver.findElement(LOGIN_ERROR_INFORMATION);
        Assert.assertTrue(error.getText().contains(username));
        Assert.assertTrue(error.getText().startsWith("Błąd"));
    }

    public void closeBanner() {
        driver.findElement(BLUE_BANNER).click();
    }

    public MyAccountPage registerUser(String email, String password) {
        driver.findElement(REGISTRATION_EMAIL_TEXTBOX).sendKeys(email);
        driver.findElement(REGISTRATION_PASSWORD_TEXTBOX).sendKeys(password);
        driver.findElement(REGISTRATION_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(WELCOME_TEXT));

        return new MyAccountPage(driver);
    }

    public void registerDuplicateUser(String email, String password) {
        driver.findElement(REGISTRATION_EMAIL_TEXTBOX).sendKeys(email);
        driver.findElement(REGISTRATION_PASSWORD_TEXTBOX).sendKeys(password);
        driver.findElement(REGISTRATION_BUTTON).click();
    }

    public void registerUserWithWrongPassword(String email, String password) throws InterruptedException {
        driver.findElement(REGISTRATION_EMAIL_TEXTBOX).sendKeys(email);
        driver.findElement(REGISTRATION_PASSWORD_TEXTBOX).sendKeys(password);
        Thread.sleep(500);
        driver.findElement(REGISTRATION_EMAIL_TEXTBOX).click();
    }

    public void assertThatPasswordIsTooWeak() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(ERROR_TOO_WEAK_PASSWORD));
        var error = driver.findElement(ERROR_TOO_WEAK_PASSWORD);
        Assert.assertTrue(error.getText().contains("Proszę wpisać mocniejsze hasło"));
    }

    public void assertThatLoginIsVisible() {
        var confirmation = driver.findElement(LOGIN_BUTTON);
        Assert.assertTrue(confirmation.getText().startsWith("Zaloguj się"));
    }

    public void assertThatRegisterIsVisible() {
        var confirmation = driver.findElement(REGISTRATION_BUTTON);
        Assert.assertTrue(confirmation.getText().startsWith("Zarejestruj się"));
    }

    public void assertThatRegistrationFails() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(ERROR_ACCOUNT_EXIST));
        var confirmation = driver.findElement(ERROR_ACCOUNT_EXIST);
        Assert.assertTrue(confirmation.getText().startsWith("Błąd:"));
    }
}
