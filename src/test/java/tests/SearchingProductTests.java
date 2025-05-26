package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class SearchingProductTests {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://fakestore.testelka.pl");
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void cleanUp() {
        //driver.quit();
    }

    @Test(testName = "Searching product and checking description")
    public void searchingProductAndCheckingDescription() {
        //given
        String productToSearch = "pilates";

        //when
        driver.findElement(By.cssSelector("[class='search-field']")).sendKeys(productToSearch);
        driver.findElement(By.cssSelector("[class='search-field']")).sendKeys(Keys.ENTER);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href='#']")));
        driver.findElement(By.cssSelector("[href='#']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h2[@class='woocommerce-loop-product__title'])[1]")));
        driver.findElement(By.xpath("(//h2[@class='woocommerce-loop-product__title'])[1]")).click();
        driver.findElement(By.cssSelector("[class='woocommerce-Tabs-panel woocommerce-Tabs-panel--description panel entry-content wc-tab']")).getText().startsWith("Opis Take a caulk");

        //then
        var confirmationFirst = driver.findElement(By.cssSelector("#tab-description > p:nth-child(2)"));
        var confirmationSecond = driver.findElement(By.cssSelector("#product-60 > div.summary.entry-summary > h1"));
        Assert.assertTrue(confirmationFirst.getText().startsWith("Take a caulk bring a spring"));
        Assert.assertTrue(confirmationSecond.getText().contains(productToSearch));
    }

    @Test(testName = "Searching cheapest prdduct in category 'Windsurfing'")
    public void searchingCheapestProduct() {
        //given
        //when
        driver.findElement(By.cssSelector("#menu-item-198 > a")).click();
        driver.findElement(By.xpath("//a[@aria-label='Przejdź do kategorii produktu Windsurfing']")).click();
        driver.findElement(By.cssSelector("#main > div:nth-child(2) > form > select")).click();
        driver.findElement(By.cssSelector("#main > div:nth-child(2) > form > select > option:nth-child(5)")).click();

        //then
        var confirmation = driver.findElement(By.cssSelector("#main > ul > li.product.type-product.post-50.status-publish.first.instock.product_cat-windsurfing.has-post-thumbnail.virtual.taxable.purchasable.product-type-simple > a.woocommerce-LoopProduct-link.woocommerce-loop-product__link > h2"));
        Assert.assertTrue(confirmation.getText().startsWith("Windsurfing w Karpathos"));
    }
}
