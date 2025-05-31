package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private static final By GREEN_ISLANDS_PRODUCT_NAME = By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]");
    private static final By FERRATY_PRODUCT_NAME = By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wspinaczka-via-ferraty/'])[2]");
    private static final By DELETED_ISLANDS_BUTTON = By.cssSelector("[aria-label='Usuń Wyspy Zielonego Przylądka - Sal z koszyka']");
    private static final By ISLANDS_REMOVED_CONFIRMATION = By.className("woocommerce-message");
    private static final By CART_PAGE_HEADER = By.className("entry-header");
    private static final By HOME_PAGE = By.xpath("//a[@href='https://fakestore.testelka.pl']");
    private static final By PAYMENT_PAGE = By.cssSelector("[class='checkout-button button alt wc-forward']");

    private WebDriver driver;

    public CartPage(WebDriver webDriver) {
        driver = webDriver;
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.textToBePresentInElementLocated(CART_PAGE_HEADER, "Koszyk"));
    }

    public String getGreenIslandsProductName() {
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(GREEN_ISLANDS_PRODUCT_NAME));
        var confirmationFirst = driver.findElement(GREEN_ISLANDS_PRODUCT_NAME);
        return confirmationFirst.getText();
    }

    public String getFerratyProductName() {
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(FERRATY_PRODUCT_NAME));
        var confirmationSecond = driver.findElement(FERRATY_PRODUCT_NAME);
        return confirmationSecond.getText();
    }

    public void deleteIslandItemFromTheCart() {
        driver.findElement(DELETED_ISLANDS_BUTTON).click();
    }

    public String getIslandsRemovedConfirmation() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(ISLANDS_REMOVED_CONFIRMATION));
        var confirmation = driver.findElement(ISLANDS_REMOVED_CONFIRMATION);
        return confirmation.getText();
    }

    public HomePage goToHomePage() {
        driver.findElement(HOME_PAGE).click();
        return new HomePage(driver);
    }

    public PaymentPage goToPaymentPage() {
        driver.findElement(PAYMENT_PAGE).click();
        return new PaymentPage(driver);
    }
}
