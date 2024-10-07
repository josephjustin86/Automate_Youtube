package demo.wrappers;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    public static Object Wrappers;
    ChromeDriver driver;

    public Wrappers(ChromeDriver driver) {
        this.driver = driver;
    }
    
    public void navigateToSite(String webLink) throws InterruptedException {
        System.out.println("Navigating to " + webLink);
        driver.get(webLink);
    }

    public Boolean verifyWebLink(String webName) throws InterruptedException {
        Boolean status = driver.getCurrentUrl().contains(webName);
        return status;
    }

    public void navigateButton(By locator) throws InterruptedException {
        WebElement button = driver.findElement(locator);
        button.click();
    }

    public void displayMessage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'About YouTube')]")));
        WebElement aboutElement = driver.findElement(By.xpath("//h1[contains(text(), 'About YouTube')]"));
        System.out.println(aboutElement.getText());
        List<WebElement> messageElements = driver.findElements(By.xpath("//p[@class='lb-font-display-3 lb-font-color-text-primary']"));
        for (WebElement messageElement : messageElements){
            System.out.println(messageElement.getText());
        }
    }

    public void clickTillDissapear(By locator, Integer maxIteration) throws InterruptedException {
        Integer numIter = 0;
        while(numIter < maxIteration){
            try{
                WebElement button = driver.findElement(locator);
                button.click();
                Thread.sleep(2000);
            }
            catch (Exception e){
                System.out.println("Stopping - " + e.getMessage());
                break;
            }
        }
    }

    public void checkMatureContent(By locator) throws InterruptedException {
        WebElement matureString = driver.findElement(locator);
        String matureContent = matureString.getText();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotEquals(matureContent, "A", "Movie is not for Mature content");
        softAssert.assertAll();
    }   

    public void movieCategory(By locator) throws InterruptedException {
        WebElement movieString = driver.findElement(locator);
        String movieContent = movieString.getText();
        SoftAssert softAssert = new SoftAssert();
        Boolean status = movieContent.contains("Comedy") || movieContent.contains("Animation") | movieContent.contains("Drama");
        softAssert.assertTrue(status, "Movie catogory exist");
        softAssert.assertAll();
    }  

    public void playlistName(By locator) throws InterruptedException {
        WebElement playlistName = driver.findElement(locator);
        System.out.println("Playlist: "+playlistName.getText());
    }  
    
    public void trackNumber(By locator) throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        WebElement trackElement = driver.findElement(locator);
        String trackString = trackElement.getText();
        String updatedTrackString = trackString.replaceAll("[^0-9]", "");
        Integer trackInteger = Integer.parseInt(updatedTrackString);
        System.out.println("Track count: " + trackInteger);
        softAssert.assertTrue(trackInteger <= 50, "Number of tracks listed is more than 50");
        softAssert.assertAll();
    }  

    public void navigateNews(By locator) throws InterruptedException {
        WebElement newsElement = driver.findElement(locator);
        Integer count = 0;
        List<WebElement> newsElements = newsElement.findElements(By.xpath(".//ytd-rich-item-renderer"));
        for (WebElement element : newsElements){
            String titleElement = element.findElement(By.xpath(".//span[@class='style-scope ytd-post-renderer']")).getText();
            String bodyElement = element.findElement(By.xpath(".//div[@id='post-text']//yt-formatted-string[@id='home-content-text']//span[@class='style-scope yt-formatted-string'][1]")).getText();
            String likesElement = element.findElement(By.xpath(".//span[@id='vote-count-middle']")).getText();
            String updatedLikesElement = likesElement.replaceAll("[^0-9]", "");
            Integer likesNumber = Integer.parseInt(updatedLikesElement);
            count += likesNumber;
            System.out.println("Title: "+ titleElement);
            System.out.println("Body: "+ bodyElement);
            System.out.println("Likes: "+ likesElement);
            System.out.println("\n");
        }
        System.out.println("Total likes: " + count);
    }

    public void searchTopic(String searchElement) throws InterruptedException {
        WebElement searchText = driver.findElement(By.xpath("//input[@id='search']"));
        searchText.sendKeys(searchElement);
        Thread.sleep(3000);
        WebElement searchButton = driver.findElement(By.xpath("//button[@id='search-icon-legacy']"));
        searchButton.click();
        Thread.sleep(5000);
    } 

    public void viewCount(By locator) throws InterruptedException {
        Integer totalViewCount = 0;
        List<WebElement> viewCountElements = driver.findElements(locator);
        for (WebElement viewCountElement : viewCountElements){
            String viewCountNumber = viewCountElement.getText();
            if (viewCountNumber.contains("M")){
                String preViewCountNumber = viewCountNumber.replaceAll("[^0-9]", "");
                Integer newPreViewCountNumber = Integer.parseInt(preViewCountNumber);
                newPreViewCountNumber = newPreViewCountNumber * 1000000;
                totalViewCount += newPreViewCountNumber;
            }
            else if (viewCountNumber.contains("K")){
                String preViewCountNumber = viewCountNumber.replaceAll("[^0-9]", "");
                Integer newPreViewCountNumber = Integer.parseInt(preViewCountNumber);
                newPreViewCountNumber = newPreViewCountNumber * 1000;
                totalViewCount += newPreViewCountNumber;
            }
            else {
                String preViewCountNumber = viewCountNumber.replaceAll("[^0-9]", "");
                Integer newPreViewCountNumber = Integer.parseInt(preViewCountNumber);
                totalViewCount += newPreViewCountNumber;
            }

            if (totalViewCount > 100000000)
                break;
        }

        System.out.println("View count has reached above 1Cr");
    } 
}
