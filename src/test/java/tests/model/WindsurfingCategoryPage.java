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

    public void selectSortingByPrice() {
        driver.findElement(By.cssSelector("#main > div:nth-child(2) > form > select")).click();
        driver.findElement(By.cssSelector("#main > div:nth-child(2) > form > select > option:nth-child(5)")).click();
    }

    public void assertThatWindsurfingInKarpathosIsFirst() {
        var confirmation = driver.findElement(By.cssSelector("#main > ul > li.product.type-product.post-50.status-publish.first.instock.product_cat-windsurfing.has-post-thumbnail.virtual.taxable.purchasable.product-type-simple > a.woocommerce-LoopProduct-link.woocommerce-loop-product__link > h2"));
        Assert.assertTrue(confirmation.getText().startsWith("Windsurfing w Karpathos"));
    }
}
