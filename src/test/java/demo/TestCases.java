package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Assert;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */
        

        @Test
        public void testCase01() throws InterruptedException {

                System.out.println("Start Test case: testCase01");
                Wrappers navigate = new Wrappers(driver);
                navigate.navigateToSite("https://www.youtube.com/");
                Thread.sleep(3000);
                Boolean webLinkLoc = navigate.verifyWebLink("youtube");
                Assert.isTrue(webLinkLoc, "Not able to load youtube");
                navigate.navigateButton(By.xpath("//a[text()='About']"));
                Boolean aboutPage = navigate.verifyWebLink("about");
                Assert.isTrue(aboutPage, "Not able to load about page");
                navigate.displayMessage();
                System.out.println("End Test case: testCase01");
        }

        @Test
        public void testCase02() throws InterruptedException {

                System.out.println("Start Test case: testCase02");

                Wrappers navigate = new Wrappers(driver);
                navigate.navigateToSite("https://www.youtube.com/");
                Thread.sleep(3000);
                Boolean webLinkLoc = navigate.verifyWebLink("youtube");
                Assert.isTrue(webLinkLoc, "Not able to load youtube");
                navigate.navigateButton(By.xpath("//yt-formatted-string[contains(text(), 'Movies') or contains(text(), 'Films')]"));
                Thread.sleep(3000);
                navigate.clickTillDissapear(By.xpath("//button[@class='yt-spec-button-shape-next yt-spec-button-shape-next--text yt-spec-button-shape-next--mono yt-spec-button-shape-next--size-m yt-spec-button-shape-next--icon-only-default yt-spec-button-shape-next--enable-backdrop-filter-experiment' and @aria-label='Next']"), 3);
                navigate.checkMatureContent(By.xpath("//span[@id='title' and contains(text(), 'Top selling')]//..//..//..//..//..//..//..//ytd-grid-movie-renderer[last()]//p[last()]/../../div[2]/p"));
                navigate.movieCategory(By.xpath("//span[@id='title' and contains(text(), 'Top selling')]//..//..//..//..//..//..//..//ytd-grid-movie-renderer[last()]//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer']"));
                System.out.println("End Test case: testCase02");
        }

        @Test
        public void testCase03() throws InterruptedException {

                System.out.println("Start Test case: testCase03");
                Wrappers navigate = new Wrappers(driver);
                navigate.navigateToSite("https://www.youtube.com/");
                Thread.sleep(3000);
                Boolean webLinkLoc = navigate.verifyWebLink("youtube");
                Assert.isTrue(webLinkLoc, "Not able to load youtube");
                navigate.navigateButton(By.xpath("//yt-formatted-string[contains(text(), 'Music')]"));
                Thread.sleep(3000);
                navigate.clickTillDissapear(By.xpath("//button[@class='yt-spec-button-shape-next yt-spec-button-shape-next--text yt-spec-button-shape-next--mono yt-spec-button-shape-next--size-m yt-spec-button-shape-next--icon-only-default yt-spec-button-shape-next--enable-backdrop-filter-experiment' and @aria-label='Next']"), 2);
                Thread.sleep(1000);
                navigate.playlistName(By.xpath("(//span[@class='style-scope ytd-shelf-renderer' and contains(text(), \"India's Biggest Hits\")]//..//..//..//..//..//..//..)[1]//ytd-compact-station-renderer[last()]//h3"));
                Thread.sleep(1000);
                navigate.trackNumber(By.xpath("(//span[@class='style-scope ytd-shelf-renderer' and contains(text(), \"India's Biggest Hits\")]//..//..//..//..//..//..//..)[1]//ytd-compact-station-renderer[last()]//p[@id='video-count-text']"));
                System.out.println("End Test case: testCase03");
        }

        @Test
        public void testCase04() throws InterruptedException {

                System.out.println("Start Test case: testCase04");
                Wrappers navigate = new Wrappers(driver);
                navigate.navigateToSite("https://www.youtube.com/");
                Thread.sleep(3000);
                Boolean webLinkLoc = navigate.verifyWebLink("youtube");
                Assert.isTrue(webLinkLoc, "Not able to load youtube");
                navigate.navigateButton(By.xpath("//yt-formatted-string[contains(text(), 'News')]"));
                Thread.sleep(6000);
                navigate.navigateNews(By.xpath("//span[@id='title' and contains(text(), 'Latest news posts')]//..//..//..//..//..//..//..//..//.."));
                System.out.println("End Test case: testCase04");
        }

        @Test(dataProvider="searchDataProvider")
        public void testCase05(String searchElement) throws InterruptedException {

                System.out.println("Start Test case: testCase05");
                Wrappers navigate = new Wrappers(driver);
                navigate.navigateToSite("https://www.youtube.com/");
                Thread.sleep(3000);
                Boolean webLinkLoc = navigate.verifyWebLink("youtube");
                Assert.isTrue(webLinkLoc, "Not able to load youtube");
                navigate.searchTopic(searchElement);
                navigate.viewCount(By.xpath("//ytd-video-renderer[@class='style-scope ytd-item-section-renderer']//span[@class='inline-metadata-item style-scope ytd-video-meta-block'][1]"));
                System.out.println("End Test case: testCase05");
        }

        @DataProvider(name = "searchDataProvider")
        public Object[][] searchData(){
        return new Object[][] 
        {
                { "Movies" },

                { "Music" },

                { "Games" }
        };
        }

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}