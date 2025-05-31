package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResultPage {
    private WebDriver driver;
    private static final By YOGA_AND_PILATES_IN_SPAIN_IMAGE = By.xpath("(//h2[@class='woocommerce-loop-product__title'])[1]");
    private static final By SEARCH_RESULTS_HEADER = By.className("woocommerce-products-header");

    public SearchResultPage(WebDriver webDriver) {
        driver = webDriver;
        var searchResultsHeader = driver.findElement(SEARCH_RESULTS_HEADER).getText();
        if (!searchResultsHeader.contains("Wyniki wyszukiwania")) {
            throw new IllegalStateException("This is not Search Results Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public YogaAndPilatesDetailsPage goToYogaAndPilatesDetails() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(YOGA_AND_PILATES_IN_SPAIN_IMAGE));
        driver.findElement(YOGA_AND_PILATES_IN_SPAIN_IMAGE).click();

        return new YogaAndPilatesDetailsPage(driver);
    }
}
