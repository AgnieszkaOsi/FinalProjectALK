package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(By.className("entry-header")).getText().contains("Wybierz podróż dla siebie!"));
    }

    public LoginPage goToMyAccount() {
        driver.findElement(By.cssSelector("#menu-item-201")).click();
        return new LoginPage(driver);
    }

    public WindsurfingCategoryPage goToWindsurfingCategory() {
        driver.findElement(By.xpath("//a[@aria-label='Przejdź do kategorii produktu Windsurfing']")).click();
        return new WindsurfingCategoryPage(driver);
    }

    public ClimbingCategoryPage goToClimbingCategory() {
        driver.findElement(By.xpath("//a[@aria-label='Przejdź do kategorii produktu Wspinaczka']")).click();
        return new ClimbingCategoryPage(driver);
    }

    public CartPage goToCart() {
        driver.findElement(By.id("menu-item-200")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]")));

        return new CartPage(driver);
    }

    public SearchResultPage findProduct(String productToSearch) {
        driver.findElement(By.cssSelector("[class='search-field']")).sendKeys(productToSearch);
        driver.findElement(By.cssSelector("[class='search-field']")).sendKeys(Keys.ENTER);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href='#']")));
        driver.findElement(By.cssSelector("[href='#']")).click();

        return new SearchResultPage(driver);
    }

    public ShopPage goToShop() {
        driver.findElement(By.cssSelector("#menu-item-198 > a")).click();
        return new ShopPage(driver);
    }
}
