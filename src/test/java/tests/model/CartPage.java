package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class CartPage {
    private static final By GREEN_ISLANDS_PRODUCT_NAME = By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]");
    private static final By FERRATY_PRODUCT_NAME = By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wspinaczka-via-ferraty/'])[2]");
    private static final By DELETED_ISLANDS_BUTTON = By.cssSelector("[aria-label='Usuń Wyspy Zielonego Przylądka - Sal z koszyka']");
    private static final By ISLANDS_REMOVED_CONFIRMATION = By.className("woocommerce-message");

    private WebDriver driver;

    public CartPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(By.className("entry-header")).getText().contains("Koszyk"));
    }

    public void assertThatIslandsAreInTheCart() {
        var confirmationFirst = driver.findElement(GREEN_ISLANDS_PRODUCT_NAME);
        Assert.assertTrue(confirmationFirst.getText().startsWith("Wyspy"));
    }

    public void asserThatClimbingIsInTheCart() {
        var confirmationSecond = driver.findElement(FERRATY_PRODUCT_NAME);
        Assert.assertTrue(confirmationSecond.getText().startsWith("Wspinaczka"));
    }

    public void deleteIslandItemFromTheCart() {
        driver.findElement(DELETED_ISLANDS_BUTTON).click();
    }

    public void assertThatIslandsAreNotInTheCart() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(ISLANDS_REMOVED_CONFIRMATION));
        var confirmation = driver.findElement(ISLANDS_REMOVED_CONFIRMATION);
        Assert.assertTrue(confirmation.getText().contains("Wyspy"));
        Assert.assertTrue(confirmation.getText().startsWith("Usunięto"));
    }
}
