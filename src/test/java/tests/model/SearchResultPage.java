package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class SearchResultPage {
    private WebDriver driver;

    public SearchResultPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(By.className("woocommerce-products-header")).getText().startsWith("Wyniki wyszukiwania"));
    }

    public YogaAndPilatesDetailsPage goToYogaAndPilatesDetails() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h2[@class='woocommerce-loop-product__title'])[1]")));
        driver.findElement(By.xpath("(//h2[@class='woocommerce-loop-product__title'])[1]")).click();

        return new YogaAndPilatesDetailsPage(driver);
    }
}
