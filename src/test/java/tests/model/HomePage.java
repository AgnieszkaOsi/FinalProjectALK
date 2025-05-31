package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private static final By MAIN_PAGE_HEADER = By.className("entry-header");
    private static final By MY_ACCOUNT_BUTTON = By.cssSelector("#menu-item-201");
    private static final By WINDSURFING_CATEGORY_IMAGE = By.xpath("//a[@aria-label='Przejdź do kategorii produktu Windsurfing']");
    private static final By CLIMBING_CATEGORY_PAGE = By.xpath("//a[@aria-label='Przejdź do kategorii produktu Wspinaczka']");
    private static final By CART_BUTTON = By.id("menu-item-200");
    private static final By GREEN_ISLANDS_PRODUCT_NAME = By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]");
    private static final By TEXTBOX_FINDER = By.cssSelector("[class='search-field']");
    private static final By BLUE_BANNER = By.cssSelector("[href='#']");
    private static final By SHOP_BUTTON = By.cssSelector("#menu-item-198 > a");

    private WebDriver driver;

    public HomePage(WebDriver webDriver) {
        driver = webDriver;
        var mainPageHeader = driver.findElement(MAIN_PAGE_HEADER).getText();
        if (!mainPageHeader.contains("Wybierz podróż dla siebie!")) {
            throw new IllegalStateException("This is not Main Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public LoginPage goToMyAccount() {
        driver.findElement(MY_ACCOUNT_BUTTON).click();
        return new LoginPage(driver);
    }

    public WindsurfingCategoryPage goToWindsurfingCategory() {
        driver.findElement(WINDSURFING_CATEGORY_IMAGE).click();
        return new WindsurfingCategoryPage(driver);
    }

    public ClimbingCategoryPage goToClimbingCategory() {
        driver.findElement(CLIMBING_CATEGORY_PAGE).click();
        return new ClimbingCategoryPage(driver);
    }

    public CartPage goToCart() {
        driver.findElement(CART_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(GREEN_ISLANDS_PRODUCT_NAME));

        return new CartPage(driver);
    }

    public SearchResultPage findProduct(String productToSearch) {
        driver.findElement(TEXTBOX_FINDER).sendKeys(productToSearch);
        driver.findElement(TEXTBOX_FINDER).sendKeys(Keys.ENTER);
        tryToCloseBlueBanner();

        return new SearchResultPage(driver);
    }

    private void tryToCloseBlueBanner() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(BLUE_BANNER));
            driver.findElement(BLUE_BANNER).click();
        } catch (StaleElementReferenceException bannerError) {
            // ignored because blue banner was already clicked
        }
    }

    public ShopPage goToShop() {
        driver.findElement(SHOP_BUTTON).click();
        return new ShopPage(driver);
    }
}
