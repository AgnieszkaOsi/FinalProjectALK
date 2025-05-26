package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.model.*;


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
        driver.quit();
    }

    @Test(testName = "Searching product and checking description")
    public void searchingProductAndCheckingDescription() {
        //given
        String productToSearch = "pilates";
        HomePage homePage = new HomePage(driver);

        //when
        SearchResultPage searchResultPage = homePage.findProduct(productToSearch);
        YogaAndPilatesDetailsPage yogaAndPilatesDetailsPage = searchResultPage.goToYogaAndPilatesDetails();

        //then
        yogaAndPilatesDetailsPage.assertThatDescriptionContainsProduct(productToSearch);
    }

    @Test(testName = "Searching cheapest product in category 'Windsurfing'")
    public void searchingCheapestProduct() {
        //given
        HomePage homePage = new HomePage(driver);

        //when
        ShopPage shopPage = homePage.goToShop();
        WindsurfingCategoryPage windsurfingCategoryPage = shopPage.goToWindsurfingPage();
        windsurfingCategoryPage.selectSortingByPrice();

        //then
        windsurfingCategoryPage.assertThatWindsurfingInKarpathosIsFirst();
    }
}
