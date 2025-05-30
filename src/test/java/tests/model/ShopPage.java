package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ShopPage {
    private WebDriver driver;
    private static final By WINDSURFING_IMAGE = By.xpath("//a[@aria-label='Przejdź do kategorii produktu Windsurfing']");

    public ShopPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(By.className("woocommerce-products-header")).getText().startsWith("Sklep"));
    }

    public WindsurfingCategoryPage goToWindsurfingPage() {
        driver.findElement(WINDSURFING_IMAGE).click();
        return new WindsurfingCategoryPage(driver);
    }
}
