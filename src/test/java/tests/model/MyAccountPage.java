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
}
