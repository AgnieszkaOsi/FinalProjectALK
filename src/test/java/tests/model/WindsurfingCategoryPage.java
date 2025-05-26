package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class WindsurfingCategoryPage {
    private WebDriver driver;

    public WindsurfingCategoryPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(By.className("woocommerce-products-header")).getText().contains("Windsurfing"));
    }

    public void addToCart() {
        driver.findElement(By.xpath("//a[@href='?add-to-cart=389']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Zobacz koszyk']")));
    }

    public CartPage goToCart() {
        driver.findElement(By.xpath("//a[@title='Zobacz koszyk']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='https://fakestore.testelka.pl/product/wyspy-zielonego-przyladka-sal/'])[2]")));

        return new CartPage(driver);
    }
}
