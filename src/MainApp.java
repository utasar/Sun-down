import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.util.TimerTask;

public class MainApp extends JFrame {
    private static final int INACTIVITY_TIME = 10 * 1000; // 10 seconds in milliseconds
    private static final int WARNING_TIME = 8 * 1000;    // 8 seconds (to give user a warning before the screenshot is taken)
    private Timer timer = new Timer();
    private String role;
    private JLabel statusLabel;
    
    public MainApp(String role) {
        this.role = role;
        setTitle("Main Application - " + role);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Status label to show activity status
        statusLabel = new JLabel("Activity: Active");
        add(statusLabel);

        // Welcome message
        JLabel label = new JLabel("Welcome, " + role);
        add(label);

        // Add activity listener
        addActivityListener();
        resetInactivityTimer();

        setVisible(true);
    }

    private void addActivityListener() {
        // Listen for mouse and keyboard activity
        AWTEventListener listener = event -> resetInactivityTimer();
        Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
        
        // Listen for window focus change (e.g., app switching)
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                resetInactivityTimer();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                resetInactivityTimer();
            }
        });
    }

    private void resetInactivityTimer() {
        statusLabel.setText("Activity: Active"); // Reset the activity label

        // Cancel any existing timer and create a new one
        timer.cancel();
        timer = new Timer();
        
        // After inactivity time, take a screenshot
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                captureScreenshot();
            }
        }, INACTIVITY_TIME);

        // Optional: Give a warning 2 seconds before inactivity triggers the screenshot
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                statusLabel.setText("Warning: Inactivity detected for 8 seconds. Screenshot will be taken soon.");
            }
        }, WARNING_TIME);
    }

    private void captureScreenshot() {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);

            // Save screenshot
            String filename = "screenshot_" + System.currentTimeMillis() + ".png";
            File file = new File(filename);
            ImageIO.write(screenFullImage, "png", file);

            System.out.println("Screenshot saved: " + file.getAbsolutePath());

            statusLabel.setText("Activity: Inactive (Screenshot Taken)"); // Update status label

        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // MainApp is now created after login, so no need for invokeLater here
    }
}
