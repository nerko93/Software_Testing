package at.jku.swtesting.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SearchPage {

    @FindBy(how = How.ID, using = "searchBar")
    private WebElement searchField;

    public void searchFor(String keywords) {
        searchField.clear();
        searchField.sendKeys(keywords);
        searchField.submit();
    }

}
