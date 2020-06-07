package at.jku.swtesting;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoogleSearchTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

		 /*WebDriverManager.firefoxdriver().setup();
		 driver = new FirefoxDriver();*/

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void tearDown() {
        driver.close();
        driver.quit();
    }

    @Test
    public void testSearch() {

        driver.get("https://www.google.at/");

        WebElement searchField = driver.findElement(By.name("q"));
        searchField.clear();
        searchField.sendKeys("testing");
        searchField.submit();


		/* google main picture gets clicked
		driver.findElement(By.id("hplogo")).click();
		*/
		
		/* tries to google any suggested google search
		WebElement searchButton = driver.findElement(By.cssSelector("center:nth-child(1) > .gNO89b"));
		searchButton.click();
		*/

        assertEquals("testing - Google-Suche", driver.getTitle());
    }


}
