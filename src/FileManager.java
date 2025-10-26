import java.io.*;

public class FileManager {
    private static final String FILE_NAME = "ps7\\hack\\src\\data.txt";

    public static boolean authenticateUser(String username, String password, String role) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                line = line.trim();
    
                
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String storedUsername = parts[0].trim(); 
                    String storedPassword = parts[1].trim(); 
                    String storedRole = parts[2].trim();   
    
                    
                    if (storedUsername.equals(username) && storedPassword.equals(password) && storedRole.equals(role)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return false;
    }}
    
