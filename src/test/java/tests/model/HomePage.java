package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(By.className("entry-header")).getText().contains("Wybierz podróż dla siebie!"));
    }

    public LoginPage goToMyAccount() {
        driver.findElement(By.cssSelector("#menu-item-201")).click();
        return new LoginPage(driver);
    }
}
