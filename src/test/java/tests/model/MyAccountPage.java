package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyAccountPage {
    private static final By MY_ACCOUNT_HEADER = By.className("entry-title");
    private static final By MAIN_PAGE_BUTTON = By.xpath("//a[@href='https://fakestore.testelka.pl']");
    private static final By HELLO_USER_TEXT = By.cssSelector("#post-8 > div > div > div > p:nth-child(2)");
    private static final By LOG_OUT_BUTTON = By.cssSelector("#post-8 > div > div > div > p:nth-child(2) > a");

    private WebDriver driver;

    public MyAccountPage(WebDriver webDriver) {
        driver = webDriver;
        var myAccountHeader = driver.findElement(MY_ACCOUNT_HEADER).getText();
        if (!myAccountHeader.contains("Moje konto")) {
            throw new IllegalStateException("This is not MyAccount Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public String getHelloUserText() {
        var helloUser = driver.findElement(HELLO_USER_TEXT);
        return helloUser.getText();
    }

    public LoginPage logOut() throws InterruptedException {
        driver.findElement(LOG_OUT_BUTTON).click();
        Thread.sleep(1000);

        return new LoginPage(driver);
    }

    public HomePage goToHomePage() {
        driver.findElement(MAIN_PAGE_BUTTON).click();

        return new HomePage(driver);
    }
}
