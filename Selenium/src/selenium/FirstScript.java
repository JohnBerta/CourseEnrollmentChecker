package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstScript extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField urlField;
    private JTextField waitTimeField;

    public FirstScript() {
        setTitle("Selenium Course Availability Checker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel urlLabel = new JLabel("Course URL:");
        urlField = new JTextField(20); // Set the preferred width
        JLabel waitTimeLabel = new JLabel("Wait Time (seconds):");
        waitTimeField = new JTextField(5); // Set the preferred width

        JLabel browserLabel = new JLabel("Select Browser:");
        String[] browsers = {"Chrome", "Firefox", "Edge"}; // Add more as needed
        JComboBox browserComboBox = new JComboBox<>(browsers);

        JButton checkButton = new JButton("Check Availability");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseURL = urlField.getText();
                int waitTimeInSeconds = Integer.parseInt(waitTimeField.getText());
                String selectedBrowser = browserComboBox.getSelectedItem().toString();

                WebDriver driver = createWebDriver(selectedBrowser);
                if (driver != null) {
                    boolean isAvailable = checkCourseAvailability(driver, courseURL, waitTimeInSeconds);
                    displayResult(isAvailable);
                    driver.quit();
                } else {
                    JOptionPane.showMessageDialog(null, "Unsupported browser selection", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(urlLabel, constraints);

        constraints.gridx = 1;
        panel.add(urlField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(waitTimeLabel, constraints);

        constraints.gridx = 1;
        panel.add(waitTimeField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(browserLabel, constraints);

        constraints.gridx = 1;
        panel.add(browserComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(checkButton, constraints);

        add(panel);
    }
    
    public static WebDriver createWebDriver(String browser) {
        WebDriver driver;
        if ("Chrome".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.chrome.driver", "C:\\Apps\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            // Set Chrome-specific options if needed
            driver = new ChromeDriver(options);
        } else if ("Firefox".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.gecko.driver", "C:\\Apps\\geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions();
            // Set Firefox-specific options if needed
            driver = new FirefoxDriver(options);
        } else {
            // Handle unsupported browsers or default to a specific browser
            // You can display an error message to the user in the UI
            driver = null;
        }
        return driver;
    }


    public static boolean checkCourseAvailability(WebDriver driver, String courseURL, int waitTimeInSeconds) {
        try {
            // Open the course URL
            driver.get(courseURL);

            // Your automation logic for checking enrollment availability goes here
            // For example, you can look for an enrollment button or check for specific elements on the page

            // Wait for the specified time (in seconds)
            Thread.sleep(waitTimeInSeconds * 1000);

         // Find the element with the specified class name
            WebElement courseIndicator = driver.findElement(By.className("css-1qci13h"));

            // Check if the course indicator element is present
            if (courseIndicator != null) {
                System.out.println("Course availability status: Available");
                return true;
            } else {
                System.out.println("Course availability status: Not Available");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
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