package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;

    public CartPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(By.className("entry-header")).getText().contains("Koszyk"));
    }

    public void assertThatIslandsAreInTheCart() {
        var confirmationFirst = driver.findElement(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]"));
        Assert.assertTrue(confirmationFirst.getText().startsWith("Wyspy"));
    }

    public void asserThatClimbingIsInTheCart() {
        var confirmationSecond = driver.findElement(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wspinaczka-via-ferraty/'])[2]"));
        Assert.assertTrue(confirmationSecond.getText().startsWith("Wspinaczka"));
    }

    public void deleteIslandItemFromTheCart() {
        driver.findElement(By.cssSelector("[aria-label='Usuń Wyspy Zielonego Przylądka - Sal z koszyka']")).click();
    }

    public void assertThatIslandsAreNotInTheCart() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.className("woocommerce-message")));
        var confirmation = driver.findElement(By.className("woocommerce-message"));
        Assert.assertTrue(confirmation.getText().contains("Wyspy"));
        Assert.assertTrue(confirmation.getText().startsWith("Usunięto"));
    }
}
