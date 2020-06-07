package at.jku.swtesting;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import at.jku.swtesting.pageObjects.ResultsPage;
import at.jku.swtesting.pageObjects.SearchPage;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LisssParameterizedTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void tearDown() {
        driver.close();
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/LisssParameterizedTestValues.csv", numLinesToSkip = 1)
    public void testLisssSearch(String searchKeywords, Integer result) {

        driver.get("http://lisss.jku.at/");

        //  title of the results page
        SearchPage searchPage = PageFactory.initElements(driver, SearchPage.class);
        searchPage.searchFor(searchKeywords);

        // number found titles
        ResultsPage resultsPage = PageFactory.initElements(driver, ResultsPage.class);
        Integer numberOfTitles = resultsPage.getNumberOfResults();
        assertEquals(result, numberOfTitles);
    }

}
