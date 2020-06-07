package at.jku.swtesting.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ResultsPage {

    @FindBy(how = How.CLASS_NAME, using = "results-count")
    private WebElement resultsCount;

    public Integer getNumberOfResults() {
        return Integer.parseInt(resultsCount.getText().split(" ")[0].replace(".", ""));
    }

}


