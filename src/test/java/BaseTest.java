import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class BaseTest {
    private WebDriver driver;

    @BeforeAll
    static void setupClass(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.navigate().to("http://the-internet.herokuapp.com/");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void addElements() {
        driver.findElement(By.xpath("//a[contains(text(),'Add/Remove Elements')]")).click();
        Assertions.assertEquals("Add/Remove Elements", driver.findElement( By.xpath("//h3[contains(text(), 'Add/Remove Elements')]")).getText());

        int button = countsButtons();

        driver.findElement(By.xpath("//button[contains(text(), 'Add Element')]")).click();

        Assertions.assertEquals(button + 1, countsButtons());
    }

    @Test
    void removeElements() {
        driver.findElement(By.xpath("//a[contains(text(),'Add/Remove Elements')]")).click();
        Assertions.assertEquals("Add/Remove Elements", driver.findElement( By.xpath("//h3[contains(text(), 'Add/Remove Elements')]")).getText());

        driver.findElement(By.xpath("//button[contains(text(), 'Add Element')]")).click();

        int button = countsButtons();

        driver.findElement(By.xpath("//button[contains(text(), 'Delete')]")).click();

        Assertions.assertEquals(button - 1, countsButtons());
    }

    private Integer countsButtons() {
        return driver.findElements(By.cssSelector("button")).size();
    }

    @Test
    void onContextMenu() {
        driver.findElement(By.xpath("//a[contains(text(),'Context Menu')]")).click();
        Assertions.assertEquals("Context Menu", driver.findElement( By.xpath("//h3[contains(text(), 'Context Menu')]")).getText());

        Actions actions = new Actions(driver);
        actions.contextClick(driver.findElement(By.xpath("//div[contains(@style, 'border-style')]"))).perform();
        Assertions.assertEquals("You selected a context menu", driver.switchTo().alert().getText());
    }

    @Test
    void dropdownList() {
        driver.findElement(By.xpath("//a[contains(text(),'Dropdown')]")).click();
        Assertions.assertEquals("Dropdown List", driver.findElement( By.xpath("//h3[contains(text(), 'Dropdown List')]")).getText());

        WebElement selectElem = driver.findElement(By.tagName("select"));
        Select select = new Select(selectElem);
        select.selectByVisibleText("Option 2");
        Assertions.assertEquals("Option 2", select.getFirstSelectedOption().getText());
    }

    @Test
    void checkboxes() {
        driver.findElement(By.xpath("//a[contains(text(),'Checkboxes')]")).click();
        Assertions.assertEquals("Checkboxes", driver.findElement( By.xpath("//h3[contains(text(), 'Checkboxes')]")).getText());

        Assertions.assertFalse(driver.findElement(By.xpath("//input[1]")).isSelected());
        Assertions.assertTrue(driver.findElement(By.xpath("//input[2]")).isSelected());

        driver.findElement(By.xpath("//input[1]")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//input[1]")).isSelected());
    }
}
