package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyAccountPage {
    private static final By MAIN_PAGE_BUTTON = By.xpath("//a[@href='https://fakestore.testelka.pl']");
    private static final By HELLO_USER_TEXT = By.cssSelector("#post-8 > div > div > div > p:nth-child(2)");
    private static final By LOG_OUT_BUTTON = By.cssSelector("#post-8 > div > div > div > p:nth-child(2) > a");

    private WebDriver driver;

    public MyAccountPage(WebDriver webDriver) {
        driver = webDriver;
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.textToBePresentInElementLocated(HELLO_USER_TEXT, "Witaj"));
    }

    public String getHelloUserText() {
        var helloUser = driver.findElement(HELLO_USER_TEXT);
        return helloUser.getText();
    }

    public LoginPage logOut() {
        driver.findElement(LOG_OUT_BUTTON).click();

        return new LoginPage(driver);
    }

    public HomePage goToHomePage() {
        driver.findElement(MAIN_PAGE_BUTTON).click();

        return new HomePage(driver);
    }
}
