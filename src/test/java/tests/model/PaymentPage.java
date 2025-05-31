package tests.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentPage {
    private WebDriver driver;
    private static final By PAYMENT_PAGE = By.className("entry-header");
    private static final By FIRST_NAME = By.id("billing_first_name");
    private static final By LAST_NAME = By.id("billing_last_name");
    private static final By SELECT_COUNTRY = By.cssSelector("[class='select2-selection__arrow']");
    private static final By COUNTRY_SEARCH_FIELD = By.cssSelector("[class='select2-search__field']");
    private static final By CLICK_ON_CHOOSEN_COUNTRY = By.cssSelector("[class='select2-results__option select2-results__option--highlighted']");
    private static final By SET_STREET_AND_HOME_NUMBER = By.id("billing_address_1");
    private static final By SET_POSTCODE = By.id("billing_postcode");
    private static final By SET_CITY = By.id("billing_city");
    private static final By SET_PHONE_NUMBER = By.id("billing_phone");
    private static final By CARD_NUMBER_IFRAME = By.xpath("(//iframe[@allow='payment *'])[1]");
    private static final By CARD_NUMBER_TEXTBOX = By.name("cardnumber");
    private static final By VALID_DATE_IFRAME = By.xpath("(//iframe[@allow='payment *'])[2]");
    private static final By VALID_DATE_TEXTBOX = By.name("exp-date");
    private static final By CVC_IFRAME = By.xpath("(//iframe[@allow='payment *'])[3]");
    private static final By CVC_TEXTBOX = By.name("cvc");
    private static final By TERMS_BUTTON = By.id("terms");
    private static final By PLACE_ORDER = By.id("place_order");

    public PaymentPage(WebDriver webDriver) {
        driver = webDriver;
        var paymentPage = driver.findElement(PAYMENT_PAGE).getText();
        if (!paymentPage.contains("Zamówienie")) {
            throw new IllegalStateException("This is not Payment Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public void setFirstNameAndLastName(String name, String lastName) {
        driver.findElement(FIRST_NAME).sendKeys(name);
        driver.findElement(LAST_NAME).sendKeys(lastName);
    }

    public void selectCountry(String country) {
        driver.findElement(SELECT_COUNTRY).click();
        driver.findElement(COUNTRY_SEARCH_FIELD).sendKeys(country);
        driver.findElement(CLICK_ON_CHOOSEN_COUNTRY).click();
    }

    public void setAddress(String address, String zipCode, String city, String telephoneNumber) {
        driver.findElement(SET_STREET_AND_HOME_NUMBER).sendKeys(address);
        driver.findElement(SET_POSTCODE).sendKeys(zipCode);
        driver.findElement(SET_CITY).sendKeys(city);
        driver.findElement(SET_PHONE_NUMBER).sendKeys(telephoneNumber);
    }

    public void setCardDetails(String cardNumber, String validDate, String cvc) {
        driver.switchTo().frame(driver.findElement(CARD_NUMBER_IFRAME));
        driver.findElement(CARD_NUMBER_TEXTBOX).sendKeys(cardNumber);
        driver.switchTo().defaultContent();

        driver.switchTo().frame(driver.findElement(VALID_DATE_IFRAME));
        driver.findElement(VALID_DATE_TEXTBOX).sendKeys(validDate);
        driver.switchTo().defaultContent();

        driver.switchTo().frame(driver.findElement(CVC_IFRAME));
        driver.findElement(CVC_TEXTBOX).sendKeys(cvc);
        driver.switchTo().defaultContent();
    }

    public void acceptTerms() {
        driver.findElement(TERMS_BUTTON).click();
    }

    public OrderDetailsPage placeOrder() {
        driver.findElement(PLACE_ORDER).click();
        return new OrderDetailsPage(driver);
    }
}
