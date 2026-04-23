import java.io.*; // Needed for reading LoginList file
import java.util.Scanner; // Needed for scanner

/**
 * Handles authentication logic for the login system.
 */
public class LoginWindow {
    public String rank = ""; // Rank of the current user

    /**
     * Authenticate the username and password against the LoginList.txt file.
     * @param username The username to check
     * @param password The password to check
     * @return true if credentials are valid, false otherwise
     */
    public boolean authenticate(String username, String password) {
        try {
            File list = new File("LoginList.txt");
            Scanner inputFile = new Scanner(list);
            while (inputFile.hasNextLine()) {
                String line = inputFile.nextLine();
                if (line.length() > 2 && line.charAt(0) == 'U' && username.equals(line.substring(2))) {
                    if (inputFile.hasNextLine()) {
                        String passLine = inputFile.nextLine();
                        if (passLine.length() > 2 && passLine.charAt(0) == 'P' && password.equals(passLine.substring(2))) {
                            if (inputFile.hasNextLine()) {
                                String rankLine = inputFile.nextLine();
                                rank = rankLine.length() > 2 ? rankLine.substring(2) : "";
                            }
                            inputFile.close();
                            return true;
                        }
                    }
                }
            }
            inputFile.close();
        } catch (Exception uh) {
            System.out.println("Error occurred in LoginWindow " + uh);
        }
        return false;
    }
}