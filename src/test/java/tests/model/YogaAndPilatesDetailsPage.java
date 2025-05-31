package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class YogaAndPilatesDetailsPage {
    private WebDriver driver;
    private static final By YOGA_AND_PILATES_IN_SPAIN_DESCRIPTION = By.cssSelector("[class='woocommerce-Tabs-panel woocommerce-Tabs-panel--description panel entry-content wc-tab']");
    private static final By YOGA_AND_PILATES_IN_SPAIN_HEADER = By.cssSelector("#product-60 > div.summary.entry-summary > h1");
    private static final By YOGA_AND_PILATES_IMAGE = By.className("product_title");

    public YogaAndPilatesDetailsPage(WebDriver webDriver) {
        driver = webDriver;
        var yogaAndPilatesImage = driver.findElement(YOGA_AND_PILATES_IMAGE).getText();
        if (!yogaAndPilatesImage.contains("Yoga i pilates")) {
            throw new IllegalStateException("This is not Yoga and Pilates Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public String getYogaAndPilatesInSpainDescription() {
        var yogaAndPilatesDesc = driver.findElement(YOGA_AND_PILATES_IN_SPAIN_DESCRIPTION);
        return yogaAndPilatesDesc.getText();
    }

    public String getYogaAndPilatesInSpainHeader() {
        var yogaAndPilatesHead = driver.findElement(YOGA_AND_PILATES_IN_SPAIN_HEADER);
        return yogaAndPilatesHead.getText();
    }
}
