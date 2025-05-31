package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CategoryPage {
    private WebDriver driver;
    private String category;
    public static final String CLIMBING = "Wspinaczka";
    public static final String WINDSURFING = "Windsurfing";


    private static final By HEADER = By.className("woocommerce-products-header");
    private static final By ADD_GREEN_ISLANDS_TO_THE_CART_BUTTON = By.xpath("//a[@href='?add-to-cart=389']");
    private static final By GO_TO_CART_BUTTON = By.xpath("//a[@title='Zobacz koszyk']");
    private static final By SORTING_ROLLER_BUTTON = By.cssSelector("#main > div:nth-child(2) > form > select");
    private static final By SORTING_BY_LOWER_PRICE = By.cssSelector("#main > div:nth-child(2) > form > select > option:nth-child(5)");
    private static final By WINDSURFING_IN_KARPATHOS_PRODUCT = By.cssSelector("#main > ul > li.product.type-product.post-50.status-publish.first.instock.product_cat-windsurfing.has-post-thumbnail.virtual.taxable.purchasable.product-type-simple > a.woocommerce-LoopProduct-link.woocommerce-loop-product__link > h2");
    private static final By ADD_FERRATY_TO_THE_CART_BUTTON = By.xpath("//a[@href='?add-to-cart=40']");
    private static final By CART_HEADER = By.className("entry-title");

    public CategoryPage(WebDriver webDriver, String categoryName) {
        driver = webDriver;
        category = categoryName;
        var header = driver.findElement(HEADER).getText();
        if (!header.contains(category)) {
            throw new IllegalStateException("This is not " + category + " Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public void addFerratyToCart() {
        if (!category.equals(CLIMBING)) {
            throw new IllegalStateException("Ferraty can not to be added in category " + category);
        }
        driver.findElement(ADD_FERRATY_TO_THE_CART_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(GO_TO_CART_BUTTON));
    }

    public void addGreenIslandsToCart() {
        if (!category.equals(WINDSURFING)) {
            throw new IllegalStateException("Green Islands can not to be added in category " + category);
        }
        driver.findElement(ADD_GREEN_ISLANDS_TO_THE_CART_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(GO_TO_CART_BUTTON));
    }

    public CartPage goToCart() {
        driver.findElement(GO_TO_CART_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.textToBePresentInElementLocated(CART_HEADER, "Koszyk"));
        return new CartPage(driver);
    }

    public void selectSortingByPrice() {
        driver.findElement(SORTING_ROLLER_BUTTON).click();
        driver.findElement(SORTING_BY_LOWER_PRICE).click();
    }

    public String getWindsurfingInKarpathosProduct() {
        if (!category.equals(WINDSURFING)) {
            throw new IllegalStateException("Windsurfing can not to be added in category " + category);
        }
        var windsurfing = driver.findElement(WINDSURFING_IN_KARPATHOS_PRODUCT);
        return windsurfing.getText();
    }
}
