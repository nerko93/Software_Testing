package at.jku.swtesting;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LisssTest {

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

    @Test
    public void testLisssSearch() {
        driver.get("http://lisss.jku.at/");
        WebElement searchField = driver.findElement(By.id("searchBar"));
        searchField.clear();
        searchField.sendKeys("software testing");
        searchField.submit();

        // title of the results page
        assertEquals("JKU | LISSS - software testing", driver.getTitle());

        // number found titles
        WebElement resultsCount = driver.findElement(By.className("results-count"));
        Integer numberOfTitles = Integer.parseInt(resultsCount
                .getText()
                .split(" ")[0]
                .replace(".", "")
        );
        assertEquals(2738, numberOfTitles);

        // number found titles on the first page
        List<WebElement> firstPageCount =
                driver.findElements(By.xpath("//prm-brief-result-container[@class='list-item']"));
        int numberOfTitlesOnFirstPage = firstPageCount.size();
        assertEquals(10, numberOfTitlesOnFirstPage);
    }

}
