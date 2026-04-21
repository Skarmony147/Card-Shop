import javax.swing.*;

public class SimpleWindow extends JFrame {
	private JPanel panel; // To reference a panel
	private JLabel messageLabel; // To reference a label
	private JTextField kiloTextField; // To reference a text field
	private JButton calcButton; // To reference a button
	private final int WIDTH = 310; // Window width
	private final int HEIGHT = 100;// Window height
	//Constructor
	public SimpleWindow(){
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
		// Display the window.
		setVisible(true);
	}
	private void buildPanel(){
		// Create a label to display instructions.
		messageLabel = new JLabel("Username");
		// Create a text field 12 characters wide.
		loginTextField = new JTextField(12);
		// Create a button with the caption "Login".
		loginButton = new JButton("Login");
		// Create a JPanel object and let the panel
		// field reference it.
		panel = new JPanel();
		// Add the label, text field, and button
		// components to the panel.
		panel.add(messageLabel);
		panel.add(loginTextField);
		panel.add(calcButton);
	}
	public static void main(String[] args){
		SimpleWindow sw = new SimpleWindow();//creates window with code above
	}
}
