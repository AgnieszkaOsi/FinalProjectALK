package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ClimbingCategoryPage {
    private WebDriver driver;
    private static final By ADD_FERRATY_TO_THE_CART_BUTTON = By.xpath("//a[@href='?add-to-cart=40']");
    private static final By CLIMBING_HEADER = By.className("woocommerce-products-header");
    private static final By SEE_CART_BUTTON = By.xpath("//a[@title='Zobacz koszyk']");





    public ClimbingCategoryPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(CLIMBING_HEADER).getText().contains("Wspinaczka"));
    }

    public void addToCart() {
        driver.findElement(ADD_FERRATY_TO_THE_CART_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(SEE_CART_BUTTON));
    }

    public CartPage goToCart() {
        driver.findElement(By.xpath("//a[@title='Zobacz koszyk']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wspinaczka-via-ferraty/'])[2]")));
        return new CartPage(driver);
    }
}
