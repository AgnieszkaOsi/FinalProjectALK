package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ClimbingCategoryPage {
    private WebDriver driver;
    private static final By ADD_FERRATY_TO_THE_CART_BUTTON = By.xpath("//a[@href='?add-to-cart=40']");
    private static final By CLIMBING_HEADER = By.className("woocommerce-products-header");
    private static final By SEE_CART_BUTTON = By.xpath("//a[@title='Zobacz koszyk']");
    private static final By CLIMBING_IMAGE = By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wspinaczka-via-ferraty/'])[2]");


    public ClimbingCategoryPage(WebDriver webDriver) {
        driver = webDriver;
        var climbingHeader = driver.findElement(CLIMBING_HEADER).getText();
        if (!climbingHeader.contains("Wspinaczka")) {
            throw new IllegalStateException("This is not Climbing Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public void addToCart() {
        driver.findElement(ADD_FERRATY_TO_THE_CART_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(SEE_CART_BUTTON));
    }

    public CartPage goToCart() {
        driver.findElement(SEE_CART_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(CLIMBING_IMAGE));
        return new CartPage(driver);
    }
}
