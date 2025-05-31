package tests.model;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private static final By PAGE_HEADER = By.className("entry-header");
    private static final By MY_ACCOUNT_BUTTON = By.cssSelector("#menu-item-201");
    private static final By WINDSURFING_CATEGORY_IMAGE = By.xpath("//a[@aria-label='Przejdź do kategorii produktu Windsurfing']");
    private static final By CLIMBING_CATEGORY_PAGE = By.xpath("//a[@aria-label='Przejdź do kategorii produktu Wspinaczka']");
    private static final By CART_BUTTON = By.id("menu-item-200");
    private static final By TEXTBOX_FINDER = By.cssSelector("[class='search-field']");
    private static final By BLUE_BANNER = By.className("woocommerce-store-notice__dismiss-link");
    private static final By SHOP_BUTTON = By.cssSelector("#menu-item-198 > a");

    private WebDriver driver;

    public HomePage(WebDriver webDriver) {
        driver = webDriver;
        var mainPageHeader = driver.findElement(PAGE_HEADER).getText();
        if (!mainPageHeader.contains("Wybierz podróż dla siebie!")) {
            throw new IllegalStateException("This is not Main Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
        tryToCloseBlueBanner();
    }

    private void tryToCloseBlueBanner() {
        try {
            driver.findElement(BLUE_BANNER).click();
        } catch (StaleElementReferenceException | ElementNotInteractableException bannerError) {
            // ignored because blue banner was already clicked
        }
    }

    public LoginPage goToMyAccount() {
        driver.findElement(MY_ACCOUNT_BUTTON).click();
        return new LoginPage(driver);
    }

    public CategoryPage goToWindsurfingCategory() {
        driver.findElement(WINDSURFING_CATEGORY_IMAGE).click();
        return new CategoryPage(driver, CategoryPage.WINDSURFING);
    }

    public CategoryPage goToClimbingCategory() {
        driver.findElement(CLIMBING_CATEGORY_PAGE).click();
        return new CategoryPage(driver, CategoryPage.CLIMBING);
    }

    public CartPage goToCart() {
        driver.findElement(CART_BUTTON).click();
        return new CartPage(driver);
    }

    public SearchResultPage findProduct(String productToSearch) {
        driver.findElement(TEXTBOX_FINDER).sendKeys(productToSearch);
        driver.findElement(TEXTBOX_FINDER).sendKeys(Keys.ENTER);

        return new SearchResultPage(driver);
    }

    public ShopPage goToShop() {
        driver.findElement(SHOP_BUTTON).click();
        return new ShopPage(driver);
    }
}
