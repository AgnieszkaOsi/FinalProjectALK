package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShopPage {
    private WebDriver driver;
    private static final By WINDSURFING_IMAGE = By.xpath("//a[@aria-label='Przejdź do kategorii produktu Windsurfing']");
    private static final By SHOP_PAGE = By.className("woocommerce-products-header");

    public ShopPage(WebDriver webDriver) {
        driver = webDriver;
        var shopPage = driver.findElement(SHOP_PAGE).getText();
        if (!shopPage.contains("Sklep")) {
            throw new IllegalStateException("This is not Shop Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public WindsurfingCategoryPage goToWindsurfingPage() {
        driver.findElement(WINDSURFING_IMAGE).click();
        return new WindsurfingCategoryPage(driver);
    }
}
