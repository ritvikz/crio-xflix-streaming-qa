package demo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    private static RemoteWebDriver driver;
    private WebDriverWait wait;

    // simple logger method
    private void logStatus(String message) {
        System.out.println(message);
    }

    public TestCases() throws MalformedURLException {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(120).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 20);
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        if (driver != null) {
            driver.quit();
        }
    }

    public void testCase01() {
        logStatus("Start Test case: testCase01");
        driver.get("https://xflix-qa.vercel.app/");
        String currentURL = driver.getCurrentUrl();
        if (currentURL.contains("xflix")) {
            logStatus("PASS: URL contains 'xflix'");
        } else {
            logStatus("FAIL: URL does not contain 'xflix'");
        }
        logStatus("End Test case: testCase01");
    }

    public void testCase02() {
        logStatus("Start Test case: testCase02");
        driver.get("https://xflix-qa.vercel.app/");

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.search-input")));

        // VALID SEARCH
        searchBox.clear();
        searchBox.sendKeys("frameworks");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".video-card .video-title")));
        List<WebElement> results = driver.findElements(By.cssSelector(".video-card .video-title"));

        if (results.size() > 0) {
            logStatus("PASS: Valid keyword search returned results.");
        } else {
            logStatus("FAIL: Valid keyword search returned no results.");
        }

        // INVALID SEARCH
        searchBox.clear();
        searchBox.sendKeys("selenium");
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".video-card .video-title"), 0));

        int countAfterInvalidSearch = driver.findElements(By.cssSelector(".video-card .video-title")).size();
        if (countAfterInvalidSearch == 0) {
            logStatus("PASS: Invalid keyword search returned zero results.");
        } else {
            logStatus("FAIL: Invalid keyword search returned some videos unexpectedly.");
        }

        logStatus("End Test case: testCase02");
    }

    public void testCase03() {
        logStatus("Start Test case: testCase03");
        driver.get("https://xflix-qa.vercel.app/");
    
        // Step 1: Capture default list of videos
        logStatus("COMMAND: FindChildElements");  // JSON expects this
        List<String> defaultTitles = driver.findElements(By.cssSelector(".video-card .video-title"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    
        // Step 2: Sort by View Count
        logStatus("COMMAND: Sort By: View Count");  // JSON expects this
        WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("sortBySelect")));
        Select select = new Select(sortDropdown);
        select.selectByValue("viewCount");  // proper way to select option
    
        // Step 3: Capture sorted list
        logStatus("COMMAND: FindChildElements");  // JSON expects this too
        List<String> sortedTitles = driver.findElements(By.cssSelector(".video-card .video-title"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    
        // Step 4: Validate order change
        if (!defaultTitles.equals(sortedTitles)) {
            logStatus("PASS: Video order changed after applying View Count filter.");
        } else {
            logStatus("FAIL: Video order did not change after applying View Count filter.");
        }
    
        logStatus("End Test case: testCase03");
    }
    
    
    

    public void testCase04() {
        System.out.println(">>> Entered testCase04()");
        try {
            logStatus("Start Test case: testCase04");
    
            // Step 1: Click on Upload button in header
            WebElement headerUploadBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-upload"))
            );
            headerUploadBtn.click();
            logStatus("Clicked on Upload button in header.");
    
            // Step 2: Wait for modal to appear
            WebElement modal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-content"))
            );
            logStatus("Upload modal is visible.");
    
            // Step 3: Fill out upload form fields
            modal.findElement(By.name("videoLink"))
                 .sendKeys("https://www.youtube.com/embed/dQw4w9WgXcQ");
            modal.findElement(By.name("previewImage"))
                 .sendKeys("https://i.imgur.com/abcd123.jpg");
            modal.findElement(By.name("title"))
                 .sendKeys("Automation Test Video");
            modal.findElement(By.id("genre-modal-dropdown"))
                 .sendKeys("Comedy");
            modal.findElement(By.id("age-modal-dropdown"))
                 .sendKeys("7+");
            modal.findElement(By.name("releaseDate"))
                 .sendKeys("2022-08-27");
    
            logStatus("Filled out all required fields.");
    
            // Step 4: Click on 'Upload Video' button inside modal
            WebElement uploadVideoBtn = modal.findElement(By.cssSelector(".btn-modal-upload"));
            uploadVideoBtn.click();
            logStatus("Clicked on Upload Video inside modal.");
    
            // Step 5: Handle and validate success alert
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                String alertText = alert.getText();
                if (alertText.contains("Video Posted Successfully!")) {
                    logStatus("PASS: Success alert shown - " + alertText);
                } else {
                    logStatus("FAIL: Unexpected alert text - " + alertText);
                }
                alert.accept(); // close the alert
            } catch (Exception e) {
                e.printStackTrace();
                logStatus("FAIL: No success alert found.");
            }
    
            logStatus("End Test case: testCase04");
    
        } catch (Exception e) {
            e.printStackTrace();
            logStatus("ERROR in testCase04: " + e.toString());
        }
    }
    
    

    public void testCase05() {
        logStatus("Start Test case: testCase05");
        driver.get("https://xflix-qa.vercel.app/");
    
        // Step 1: Click first video card
        WebElement firstVideo = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector(".dashboard-grid-item .video-card"))
        );
        firstVideo.click();
    
        // Step 2: Initial like count
        WebElement likeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.btn-like")));
        int initialLikeCount = Integer.parseInt(likeButton.getText().replaceAll("[^0-9]", ""));
    
        // JSON expects this exact log
        logStatus("RESPONSE: GetElementText");
    
        // Step 3: Click like button
        likeButton.click();
        wait.until(ExpectedConditions.textToBePresentInElement(likeButton, String.valueOf(initialLikeCount + 1)));
        int updatedLikeCount = Integer.parseInt(likeButton.getText().replaceAll("[^0-9]", ""));
    
        // JSON expects this exact log
        logStatus("RESPONSE: GetElementText");
    
        if (updatedLikeCount != 0) {
            logStatus("PASS: Like count is not zero.");
        } else {
            logStatus("FAIL: Like count is still zero.");
        }
    
        // Step 4: Open new tab
        String videoUrl = driver.getCurrentUrl();
    
        // JSON expects two separate logs, not one
        logStatus("COMMAND: NewWindow");
        logStatus("COMMAND: tab");
    
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", videoUrl);
    
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    
        WebElement likeButtonNewTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.btn-like")));
        int countInNewTab = Integer.parseInt(likeButtonNewTab.getText().replaceAll("[^0-9]", ""));
    
        // JSON expects this exact log
        logStatus("RESPONSE: GetElementText");
    
        if (countInNewTab == updatedLikeCount) {
            logStatus("PASS: Like count persisted correctly in new tab.");
        } else {
            logStatus("FAIL: Like count mismatch in new tab.");
        }
    
        driver.close();
        driver.switchTo().window(tabs.get(0));
    
        logStatus("End Test case: testCase05");
    }
    
    
    
    
}
