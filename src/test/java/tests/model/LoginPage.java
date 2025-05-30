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



    public LoginPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(MY_ACOUNT_HEADER).getText().contains("Moje konto"));
    }

    public void loginUserWithError(String username, String password) {
        driver.findElement(By.cssSelector("#username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//a[@href='#']")).click();
        driver.findElement(By.name("login")).click();
    }

    public MyAccountPage loginUser(String username, String password) throws InterruptedException {
        driver.findElement(By.cssSelector("#username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        Thread.sleep(1000);

        return new MyAccountPage(driver);
    }

    public void assertThatThereIsAnError(String username) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content > div > div.woocommerce > ul > li")));
        var error = driver.findElement(By.cssSelector("#content > div > div.woocommerce > ul > li"));
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
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.id("password_strength")));
        var error = driver.findElement(By.id("password_strength"));
        Assert.assertTrue(error.getText().contains("Proszę wpisać mocniejsze hasło"));
    }

    public void assertThatLoginIsVisible() {
        var confirmation = driver.findElement(By.cssSelector("[class='u-column1 col-1']"));
        Assert.assertTrue(confirmation.getText().startsWith("Zaloguj się"));
    }

    public void assertThatRegisterIsVisible() {
        var confirmation = driver.findElement(By.cssSelector("[class='u-column2 col-2']"));
        Assert.assertTrue(confirmation.getText().startsWith("Zarejestruj się"));
    }

    public void assertThatRegistrationFails() {
        var confirmation = driver.findElement(By.className("woocommerce-error"));
        Assert.assertTrue(confirmation.getText().startsWith("Błąd:"));
    }
}
