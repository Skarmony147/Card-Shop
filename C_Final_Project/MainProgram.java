/**
 * Main program for final project by Sebastian and Charlie
 **/

import javax.swing.*; // For swing graphics
import java.awt.*; // For swing graphics
import java.awt.event.*; // For event listeners

public class MainProgram {
	/**
	 * Login window function where user enters username and password,
	 * which uses the LoginWindow class for the different operations.
	 **/
	private static String rank;
	public static void login(){
		// Create login window and properties
		JFrame logWin = new JFrame("Login");
        logWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logWin.setSize(250, 140);
        logWin.setLayout(new BorderLayout());
		// Create panel with flow layout
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 7));
		// Create labels, text fields, and button for username and password
        JLabel userLabel = new JLabel("Username");
        JTextField userTextField = new JTextField(10);
        JLabel passLabel = new JLabel("Password");
        JTextField passTextField = new JTextField(10);
        JButton loginButton = new JButton("Login");
		// Add components to the panel
        panel.add(userLabel);
        panel.add(userTextField);
        panel.add(passLabel);
        panel.add(passTextField);
        panel.add(loginButton);
		// Add panel to login window, center window, display window
        logWin.add(panel, BorderLayout.CENTER);
        logWin.setLocationRelativeTo(null);
        logWin.setVisible(true);
		// Create LoginWindow object to perform operations for login window
        LoginWindow log = new LoginWindow();
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                String password = passTextField.getText();
                if (log.authenticate(username, password)) {
					loginButton.setText("Success");
					// Use a timer for the delay, had to look this up
					new javax.swing.Timer(1000, new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							logWin.setState(Frame.ICONIFIED);
							loginButton.setText("Login");
							rank = log.getRank();
							menu();
							((javax.swing.Timer)evt.getSource()).stop();
						}
					}).start();
				} else {
					loginButton.setText("Invalid");
					new javax.swing.Timer(1000, new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							loginButton.setText("Login");
							((javax.swing.Timer)evt.getSource()).stop();
						}
					}).start();
				}
            }
        });
	}
	/**
	 * Menu window function for moving to the different screens
	 * of snake, stocks, and employees depending on the rank of the user.
	 **/
	public static void menu(){
		// Create menu window and properties
		JFrame menuWin = new JFrame("Menu");
        menuWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuWin.setSize(180, 200);
        menuWin.setLayout(new BorderLayout());
		// Create panel with flow layout
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3,1));
		// Create buttons for snake stocks and employee management
		// depending on rank, and add them to panel
		if(rank.equals("Manager")){
			JButton snakeButton = new JButton("Snake");
			JButton stockButton = new JButton("Stocks");
			JButton employButton = new JButton("Employees");
			menuPanel.add(snakeButton);
			menuPanel.add(stockButton);
			menuPanel.add(employButton);
			snakeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					menuWin.setState(Frame.ICONIFIED);
					//snake();
				}
            });
            stockButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					menuWin.setState(Frame.ICONIFIED);
					//stock();
				}
            });
            employButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					menuWin.setState(Frame.ICONIFIED);
					//employ();
				}
            });
		} else if (rank.equals("Employee")){
			JButton snakeButton = new JButton("Snake");
			JButton stockButton = new JButton("Stocks");
			menuPanel.add(snakeButton);
			menuPanel.add(stockButton);
		} else if (rank.equals("Rookie")){
			JButton stockButton = new JButton("Stocks");
			menuPanel.add(stockButton);
		}
		// Add panel to login window, center window, display window
        menuWin.add(menuPanel, BorderLayout.CENTER);
        menuWin.setLocationRelativeTo(null);
        menuWin.setVisible(true);
	}
    public static void main(String[] args) {
		// For a minor extra, a splash screen!
		// Had to look up how to do this
		// Image made by Charlie
		JWindow splash = new JWindow();
		splash.setLayout(new BorderLayout());
		splash.setSize(400, 250);
		splash.setLocationRelativeTo(null);
		splash.getContentPane().add(
			new JLabel("", new ImageIcon("FreedomTM.png"), SwingConstants.CENTER));
		splash.setVisible(true);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		splash.setVisible(false);
		splash.dispose();
		
		login(); // The beginning of it all
    }
}
