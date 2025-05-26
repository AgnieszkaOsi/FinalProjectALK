package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class MyAccountPage {
    private WebDriver driver;

    public MyAccountPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(By.className("entry-title")).getText().contains("Moje konto"));
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
        driver.findElement(By.xpath("//a[@href='https://fakestore.testelka.pl']")).click();

        return new HomePage(driver);
    }
}
