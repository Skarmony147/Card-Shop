import javax.swing.*; // Needed for swing graphics
import java.awt.*; // Needed for FlowLayout class

/**
 * The is the login window meant to check a username and password with
 * a file database when attempting to log in.
 **/

public class LoginWindow extends JFrame {
	private JPanel panel; // To reference a panel
	private JLabel userLabel; // To reference username label
	private JTextField userTextField; // To reference username text field
	private JLabel passLabel; // To reference password label
	private JTextField passTextField; // To reference password text field
	private JButton loginButton; // To reference login button
	private final int WIDTH = 250; // Window width
	private final int HEIGHT = 140;// Window height
	
	//Constructor
	public LoginWindow(){
		// Set the window title.
		setTitle("Login Window");
		// Set the size of the window.
		setSize(WIDTH, HEIGHT);
		// Specify what happens when the close button is clicked.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Build the panel and add it to the frame.
		buildPanel();
		// Add the panel to the frame's content pane.
		add(panel);
		// Center the window on screen
		setLocationRelativeTo(null);
		// Display the window.
		setVisible(true);
	}
	
	private void buildPanel(){
		// Create a label to display instructions.
		userLabel = new JLabel("Username");
		// Create a text field 12 characters wide.
		userTextField = new JTextField(10);
		// Create a label to display instructions.
		passLabel = new JLabel("Password");
		// Create a text field 12 characters wide.
		passTextField = new JTextField(10);
		// Create a button with the caption "Login".
		loginButton = new JButton("Login");
		// Create a JPanel object and let the panel
		// field reference it.
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 7));
		// Add the label, text field, and button
		// components to the panel.
		panel.add(userLabel);
		panel.add(userTextField);
		panel.add(passLabel);
		panel.add(passTextField);
		panel.add(loginButton);
	}
	public static void main(String[] args){
		LoginWindow log = new LoginWindow();// Creates window with code above
	}
}
