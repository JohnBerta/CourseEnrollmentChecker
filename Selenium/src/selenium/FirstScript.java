package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FirstScript {
	
	public static void checkCourseAvailability(String courseURL) {
	    //Setting system properties of ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Apps\\chromedriver.exe");
        
        // Optional: You can configure Chrome options, such as running headless
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Uncomment this line to run headless (without a visible browser window)
        
        WebDriver driver = new ChromeDriver(options);

        try {
            // Open the course URL
            driver.get(courseURL);

            // Your automation logic for checking enrollment availability goes here
            // For example, you can look for an enrollment button or check for specific elements on the page

            // Wait 2 seconds
            Thread.sleep(2000);
            
            // For demonstration purposes, let's assume we're looking for an "Enroll" button
            WebElement enrollButton = driver.findElement(By.className("rc-StartDateButton"));

            // Check if the enrollment button is visible
            if (enrollButton.isDisplayed()) {
                System.out.println("Course available: " + courseURL);
            } else {
                System.out.println("Course not available: " + courseURL);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        // Example usage:
        String courseURL = "https://www.coursera.org/programs/iec-general-z5sjb/learn/codio-software-testing-for-developers?source=search";
        checkCourseAvailability(courseURL);
    }
	
}