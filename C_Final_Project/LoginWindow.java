import javax.swing.*; // Needed for swing graphics
import java.awt.*; // Needed for FlowLayout
import java.awt.event.*; // Needed for login button
import java.io.*; // Needed for reading LoginList file
import java.util.Scanner; // Needed for scanner

/**
 * The is the login window meant to check a username and password with
 * a file database when attempting to log in.
 **/

public class LoginWindow extends JFrame {
	private JPanel panel; // Reference a panel
	private JLabel userLabel; // Reference username label
	private JTextField userTextField; // Reference username text field
	private JLabel passLabel; // Reference password label
	private JTextField passTextField; // Reference password text field
	private JButton loginButton; // Reference login button
	private final int WIDTH = 250; // Window width
	private final int HEIGHT = 140;// Window height
	public String rank =""; // Rank of the current user
	
	/**
	 * Constructor
	 **/
	public LoginWindow(){
		// Set the window title
		setTitle("Login Window");
		// Set the size of the window
		setSize(WIDTH, HEIGHT);
		// Specify what happens when the close button is clicked
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Build the panel and add it to the frame
		buildPanel();
		// Add the panel to the frame's content pane
		add(panel);
		// Center the window on screen
		setLocationRelativeTo(null);
		// Display the window
		setVisible(true);
	}
	/**
	 * buildPanel adds two labels and two text fields for username and
	 * password, then a button which will compare the username and 
	 * password to a file
	 **/
	private void buildPanel(){
		// Create a label to display instructions
		userLabel = new JLabel("Username");
		// Create a text field 12 characters wide
		userTextField = new JTextField(10);
		// Create a label to display instructions
		passLabel = new JLabel("Password");
		// Create a text field 12 characters wide
		passTextField = new JTextField(10);
		// Create a button with the caption "Login"
		loginButton = new JButton("Login");
		// Add an action listener to the button
		loginButton.addActionListener(new LoginButtonListener());
		// Create a JPanel object and let the panel
		// field reference it.
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 7));
		// Add the label, text field, and button
		// components to the panel
		panel.add(userLabel);
		panel.add(userTextField);
		panel.add(passLabel);
		panel.add(passTextField);
		panel.add(loginButton);
	}
	/**
	 * Action Listener for login button
	 **/
	private class LoginButtonListener implements ActionListener{
	/**
	 * The actionPerformed method executes when the user
	 * clicks on the Calculate button.
	 * @param e The event object.
	 **/
		public void actionPerformed(ActionEvent e){
			try{
				// Get LoginList file
				File list = new File("LoginList.txt");
				// Set scanner to file
				Scanner inputFile = new Scanner(list);
				// Sets line variable to first line of file
				String line = inputFile.nextLine();
				// Repeat checking lines until end of file
				while(inputFile.hasNext()){
					// Check if entered username is in list, then check 
					// if password matches as well. If not, make 
					// loginButton text say "Incorrect password" for a second
					if(line.charAt(0) == 'U' && userTextField.getText().equals(line.substring(2,line.length()))){
						System.out.println(line.substring(2,line.length()));
						line = inputFile.nextLine();
						if(line.charAt(0) == 'P' && passTextField.getText().equals(line.substring(2,line.length()))){
							System.out.println(line.substring(2,line.length()));
						}
					}
					// Sets line variable to next line in file
					line = inputFile.nextLine();
				}
				inputFile.close();
			}catch(Exception uh){
				System.out.println("Error occurred in LoginWindow " + uh);
			}
		}
	}
}
