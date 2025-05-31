package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderDetailsPage {
    private WebDriver driver;
    private static final By ORDER_DETAILS_PAGE = By.className("entry-header");
    private static final By ORDER_CONFIRMATION = By.cssSelector("[class='woocommerce-notice woocommerce-notice--success woocommerce-thankyou-order-received']");

    public OrderDetailsPage(WebDriver webDriver) {
        driver = webDriver;
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.textToBePresentInElementLocated(ORDER_DETAILS_PAGE, "Zamówienie otrzymane"));
    }

    public String orderConfirmation() {
        var orderConfirmation = driver.findElement(ORDER_CONFIRMATION);
        return orderConfirmation.getText();
    }
}