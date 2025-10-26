import javax.swing.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 150, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 50, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 150, 25);
        add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(20, 80, 80, 25);
        add(roleLabel);

        roleBox = new JComboBox<>(new String[]{"admin", "company_admin", "user"});
        roleBox.setBounds(100, 80, 150, 25);
        add(roleBox);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 110, 100, 25);
        add(loginButton);

        loginButton.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (FileManager.authenticateUser(username, password, role)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            // After successful login, open MainApp window
            new MainApp(role);
            this.dispose(); // Close the login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials!");
        }
    }

    public static void main(String[] args) {
        // Create and display the login frame
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
