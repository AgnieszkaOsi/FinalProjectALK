package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DetailsPage {
    private WebDriver driver;
    private String category;
    public static final String YOGA_AND_PILATES = "Yoga i pilates";

    private static final By YOGA_AND_PILATES_IN_SPAIN_DESCRIPTION = By.cssSelector("[class='woocommerce-Tabs-panel woocommerce-Tabs-panel--description panel entry-content wc-tab']");
    private static final By YOGA_AND_PILATES_IN_SPAIN_HEADER = By.cssSelector("#product-60 > div.summary.entry-summary > h1");
    private static final By PRODUCT_TITLE = By.className("product_title");

    public DetailsPage(WebDriver webDriver, String categoryName) {
        driver = webDriver;
        category = categoryName;
        var productTitle = driver.findElement(PRODUCT_TITLE).getText();
        if (!productTitle.contains(category)) {
            throw new IllegalStateException("This is not " + category + " Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public String getYogaAndPilatesInSpainDescription() {
        if (!category.equals(YOGA_AND_PILATES)) {
            throw new IllegalStateException("Yoga and pilates are not in the " + category);
        }
        var yogaAndPilatesDesc = driver.findElement(YOGA_AND_PILATES_IN_SPAIN_DESCRIPTION);
        return yogaAndPilatesDesc.getText();
    }

    public String getYogaAndPilatesInSpainHeader() {
        if (!category.equals(YOGA_AND_PILATES)) {
            throw new IllegalStateException("Yoga and pilates are not in the " + category);
        }
        var yogaAndPilatesHead = driver.findElement(YOGA_AND_PILATES_IN_SPAIN_HEADER);
        return yogaAndPilatesHead.getText();
    }
}
