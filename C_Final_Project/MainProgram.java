/**
 * Main program for final project by Sebastian and Charlie
 **/

import javax.swing.*; // For swing graphics
import java.awt.*; // For swing graphics
import java.awt.event.*; // For event listeners
import java.util.*; // For array lists

//For splash screen
import java.awt.image.*;
import javax.sound.sampled.*;
import java.io.*;

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
					stock();
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
	/**
	 * Stock window function for buying and selling stock, 
	 * viewing current prices, and current funds.
	 **/
	public static void stock(){
	    // Create stocks object for references
	    Stocks inventory = new Stocks();
		// Create stock window and properties
		JFrame stockWin = new JFrame("Stocks");
        stockWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stockWin.setSize(400, 200);
        stockWin.setLayout(new GridLayout(4,1));
        // Create list of stocks for iteration creation of panels and such
        ArrayList<String> stockNames = new ArrayList<String>(inventory.viewBrands());
        ArrayList<Integer> stocks = new ArrayList<Integer>(inventory.viewStocks());
        ArrayList<String> boxes = new ArrayList<String>();
        boxes.add("ETB");
        boxes.add("Booster bundle");
        boxes.add("Booster box");
        boxes.add("UPC");
        // Iterate through stockNames making panel and componentes for each stock
        for (int i = 0; i < stockNames.size(); i++) {
            // Create panel and components
            String name = stockNames.get(i);
            String box = boxes.get(i);
            JPanel stockPanel = new JPanel(new GridLayout(1, 5));
            JLabel nameLabel = new JLabel(stockNames.get(i), SwingConstants.CENTER);
            JLabel priceLabel = new JLabel(Integer.toString(inventory.viewPrice(boxes.get(i))), SwingConstants.CENTER);
            JLabel numLabel = new JLabel(Integer.toString(stocks.get(i)), SwingConstants.CENTER);
            JButton buyButton = new JButton("Buy");
            JButton sellButton = new JButton("Sell");
            // Add action listeners for buy/sell buttons
            buyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					inventory.buyStock(name,1);
				}
            });
            sellButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					inventory.sellStock(name,box,1);
				}
            });
            // Add stuff to panel
            stockPanel.add(nameLabel);
            stockPanel.add(priceLabel);
            stockPanel.add(numLabel);
            stockPanel.add(buyButton);
            stockPanel.add(sellButton);
            // Add panel to window
            stockWin.add(stockPanel, BorderLayout.CENTER);
        }
        
        // Display window
        stockWin.setLocationRelativeTo(null);
        stockWin.setVisible(true);
	}

	/**
	 * This function shows a splash screen with the logo and sound taken from the HD2 RPG project
	 * This was a pain, but it works so hurrah
	 * Way, way too much code I had to look up, best considered copied entirely from various sources
	 * 
	 */
	public static void showSplashScreen() {
		int SCREEN_WIDTH = 400;
		int SCREEN_HEIGHT = 250;
		int DURATION = 4700; // ms
		String IMAGE_PATH = "FreedomTM.png";
		String SOUND_PATH = "Logo.wav";

		JWindow splash = new JWindow();
		splash.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		splash.setLocationRelativeTo(null);

		// Load image
		ImageIcon icon = new ImageIcon(IMAGE_PATH);
		Image logo = icon.getImage();

		// Play sound
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(SOUND_PATH));
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (Exception e) {
			System.out.println("Sound error: " + e);
		}

		// Custom panel for animation
		JPanel panel = new JPanel() {
			long start = System.currentTimeMillis();
			@Override // I have no idea what this does but if I delete it the code is unhappy
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				long elapsed = System.currentTimeMillis() - start;
				float progress = Math.min(elapsed / (float)DURATION, 1.0f);

				// Zoom effect
				double zoom = 0.9 + (progress * 0.12);
				int w = (int)(SCREEN_WIDTH * zoom);
				int h = (int)(SCREEN_HEIGHT * zoom);

				// Brighten effect
				float brightness = 0.5f + (progress * 0.4f);

				// Draw scaled image
				BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = scaled.createGraphics();
				g2.drawImage(logo, 0, 0, w, h, null);

				// Brighten (overlay white with alpha)
				int alpha = (int)(255 * (1 - brightness));
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, w, h);
				g2.dispose();

				// Center image
				int x = (SCREEN_WIDTH - w) / 2;
				int y = (SCREEN_HEIGHT - h) / 2;
				g.drawImage(scaled, x, y, null);
			}
		};
		splash.add(panel);
		splash.setVisible(true);

		// Animation timer
		javax.swing.Timer splashDown = new javax.swing.Timer(1000 / 60, null); // 60 FPS
		splashDown.addActionListener(new ActionListener() {
			long start = System.currentTimeMillis();
			public void actionPerformed(ActionEvent e) {
				panel.repaint();
				if (System.currentTimeMillis() - start > DURATION) {
					splashDown.stop();
					splash.setVisible(false);
					splash.dispose();
				}
			}
		});
		splashDown.start();

		// Wait for animation to finish
		try {
			Thread.sleep(DURATION + 100);
		} catch (InterruptedException ex) {}
	}

    public static void main(String[] args) {
		// For a minor extra, a splash screen!
		// Had to look up how to do this
		// Image and from the Helldivers 2 rpg project
		showSplashScreen(); // this was a pain

		login(); // The beginning of it all
    }
}
