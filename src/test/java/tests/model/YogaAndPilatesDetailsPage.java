package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class YogaAndPilatesDetailsPage {
    private WebDriver driver;
    private static final By YOGA_AND_PILATES_IN_SPAIN_DESCRIPTION = By.cssSelector("[class='woocommerce-Tabs-panel woocommerce-Tabs-panel--description panel entry-content wc-tab']");
    private static final By YOGA_AND_PILATES_IN_SPAIN_HEADER = By.cssSelector("#product-60 > div.summary.entry-summary > h1");

    public YogaAndPilatesDetailsPage(WebDriver webDriver) {
        driver = webDriver;
        Assert.assertTrue(driver.findElement(By.className("product_title")).getText().startsWith("Yoga i pilates"));
    }

    public void assertThatDescriptionContainsProduct(String productToSearch) {
        driver.findElement(YOGA_AND_PILATES_IN_SPAIN_DESCRIPTION).getText().startsWith("Opis Take a caulk");
        var confirmationSecond = driver.findElement(YOGA_AND_PILATES_IN_SPAIN_HEADER);
        Assert.assertTrue(confirmationSecond.getText().contains(productToSearch));
    }
}
