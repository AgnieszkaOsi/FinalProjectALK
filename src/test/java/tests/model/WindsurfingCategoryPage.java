package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class WindsurfingCategoryPage {
    private WebDriver driver;
    private static final By WINDSURFING_HEADER = By.className("woocommerce-products-header");
    private static final By ADD_GREEN_ISLANDS_TO_THE_CART_BUTTON = By.xpath("//a[@href='?add-to-cart=389']");
    private static final By GO_TO_CART_BUTTON = By.xpath("//a[@title='Zobacz koszyk']");
    private static final By SORTING_ROLLER_BUTTON = By.cssSelector("#main > div:nth-child(2) > form > select");
    private static final By SORTING_BY_LOWER_PRICE = By.cssSelector("#main > div:nth-child(2) > form > select > option:nth-child(5)");
    private static final By WINDSURFING_IN_KARPATHOS_PRODUCT = By.cssSelector("#main > ul > li.product.type-product.post-50.status-publish.first.instock.product_cat-windsurfing.has-post-thumbnail.virtual.taxable.purchasable.product-type-simple > a.woocommerce-LoopProduct-link.woocommerce-loop-product__link > h2");

    public WindsurfingCategoryPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(WINDSURFING_HEADER).getText().contains("Windsurfing"));
    }

    public void addToCart() {
        driver.findElement(ADD_GREEN_ISLANDS_TO_THE_CART_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(GO_TO_CART_BUTTON));
    }

    public CartPage goToCart() {
        driver.findElement(GO_TO_CART_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]")));
        return new CartPage(driver);
    }

    public void selectSortingByPrice() {
        driver.findElement(SORTING_ROLLER_BUTTON).click();
        driver.findElement(SORTING_BY_LOWER_PRICE).click();
    }

    public void assertThatWindsurfingInKarpathosIsFirst() {
        var confirmation = driver.findElement(WINDSURFING_IN_KARPATHOS_PRODUCT);
        Assert.assertTrue(confirmation.getText().startsWith("Windsurfing w Karpathos"));
    }
}
