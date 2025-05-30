package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class MyAccountPage {
    private static final By MY_ACCOUNT_HEADER = By.className("entry-title");
    private static final By MAIN_PAGE_BUTTON = By.xpath("//a[@href='https://fakestore.testelka.pl']");

    private WebDriver driver;

    public MyAccountPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(MY_ACCOUNT_HEADER).getText().contains("Moje konto"));
    }

    public void assertThatConfirmationIsVisible(String expectedUsername) {
        var confirmation = driver.findElement(By.cssSelector("#post-8 > div > div > div > p:nth-child(2)"));
        Assert.assertTrue(confirmation.getText().startsWith("Witaj"));
        Assert.assertTrue(confirmation.getText().contains(expectedUsername));
    }

    public LoginPage logOut() throws InterruptedException {
        driver.findElement(By.cssSelector("#post-8 > div > div > div > p:nth-child(2) > a")).click();
        Thread.sleep(1000);

        return new LoginPage(driver);
    }

    public HomePage goToHomePage() {
        driver.findElement(MAIN_PAGE_BUTTON).click();

        return new HomePage(driver);
    }
}
