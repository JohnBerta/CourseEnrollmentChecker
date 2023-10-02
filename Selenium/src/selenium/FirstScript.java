package selenium;

import javax.swing.JFrame;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstScript extends JFrame {
	
	private JTextField urlField;
    private JTextField waitTimeField;

    public FirstScript() {
        setTitle("Selenium Course Availability Checker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel urlLabel = new JLabel("Course URL:");
        urlField = new JTextField();
        JLabel waitTimeLabel = new JLabel("Wait Time (seconds):");
        waitTimeField = new JTextField();

        JButton checkButton = new JButton("Check Availability");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseURL = urlField.getText();
                int waitTimeInSeconds = Integer.parseInt(waitTimeField.getText());

                boolean isAvailable = checkCourseAvailability(courseURL, waitTimeInSeconds);
                displayResult(isAvailable);
            }
        });

        panel.add(urlLabel);
        panel.add(urlField);
        panel.add(waitTimeLabel);
        panel.add(waitTimeField);
        panel.add(checkButton);

        add(panel);
    }
    
    public static boolean checkCourseAvailability(String courseURL, int waitTimeInSeconds) {
        // Setting system properties of ChromeDriver
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

            // Wait for the specified time (in seconds)
            Thread.sleep(waitTimeInSeconds * 1000);

            // For demonstration purposes, let's assume we're looking for an "Enroll" button
            WebElement enrollButton = driver.findElement(By.className("rc-StartDateButton"));

            // Check if the enrollment button is visible
            if (enrollButton.isDisplayed()) {
                System.out.println("Course available: " + courseURL);
                return true;
            } else {
                System.out.println("Course not available: " + courseURL);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            driver.quit();
        }
        return false;
    }
    
    public static void displayResult(boolean isAvailable) {
        String message = isAvailable ? "Course is available!" : "Course is not available.";
        JOptionPane.showMessageDialog(null, message, "Availability Status", isAvailable ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FirstScript().setVisible(true);
            }
        });
    }
}