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
	private static String rank; // Rank of user
	private static String currentUsername = "Player"; // username of user used for snake leaderboard
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
				currentUsername = username;
				if (log.authenticate(username, password)) {
					loginButton.setText("Success");
					// Use a timer for the delay, had to look this up
					new javax.swing.Timer(1000, new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							logWin.setState(Frame.ICONIFIED);
							loginButton.setText("Login");
							rank = log.getRank();
							passTextField.setText("");
							userTextField.setText("");
							logWin.dispose(); // Close login window
							menu(); // Open menu
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
					menuWin.dispose(); // Close menu window
					snake(); // Open snake window
				}
			});
			stockButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					menuWin.dispose(); // Close menu window
					stock(); // Open stock window
				}
			});
			employButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					menuWin.dispose(); // Close menu window
					employ(); // Open employee window
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
		// if window is already made, unminimize it
		// Create stocks object for references
		Stocks inventory = new Stocks();
		// Create stock window and properties
		JFrame stockWin = new JFrame("Stocks");
		stockWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		stockWin.setSize(400, 200);
		stockWin.setLayout(new GridLayout(4,1));
		// Create list of stocks for iteration creation of panels and such
		ArrayList<String> stockNames = new ArrayList<String>((Collection<? extends String>) inventory.viewBrands());
		ArrayList<Integer> stocks = new ArrayList<Integer>((Collection<? extends Integer>) inventory.viewStocks());
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
	 * Snake window function for snake game
	 * Handles graphics, input, and frame timer
	 */
	public static void snake() {
		// Window setup and constants
		final int CELL_SIZE = 20;
		final int GRID_WIDTH = 20;
		final int GRID_HEIGHT = 15;
		final int WIDTH = GRID_WIDTH * CELL_SIZE;
		final int HEIGHT = GRID_HEIGHT * CELL_SIZE;

		// Create the main game window
		JFrame snakeWin = new JFrame("Snake");
		snakeWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		snakeWin.setSize(WIDTH, HEIGHT);
		snakeWin.setResizable(false);

		// Create Snake object
		Snake snakeGame = new Snake(GRID_WIDTH, GRID_HEIGHT);

		// Panel for leaderboard and buttons
		JPanel leadPanel = new JPanel();
		leadPanel.setLayout(new BorderLayout());

		// Leaderboard area
		JTextArea leaderboardArea = new JTextArea();
		leaderboardArea.setEditable(false);
		leaderboardArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		leaderboardArea.setOpaque(false);
		leaderboardArea.setFocusable(false);
		leaderboardArea.setBorder(null);
		leaderboardArea.setHighlighter(null);
		leaderboardArea.setMargin(new Insets(10, 10, 10, 10));
		leaderboardArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		leaderboardArea.setAlignmentY(Component.CENTER_ALIGNMENT);
		leadPanel.add(leaderboardArea, BorderLayout.CENTER);

		// Load and display leaderboard at start
		java.util.List<HighscoreEntry> scores = readHighScores();
		updateLeaderboard(leaderboardArea, scores);

		// Start/Restart and Back buttons
		JButton startButton = new JButton("Start Game");
		startButton.setFocusable(false);
		JButton snakeBackButton = new JButton("Back to Menu");
		snakeBackButton.setFocusable(false);

		// Game panel for drawing the snake game
		JPanel gamePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				// Draw background
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE);

				// Draw food
				Point food = snakeGame.getFood();
				if (food != null) {
					g.setColor(Color.RED);
					g.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				}

				// Draw snake body
				java.util.List<Point> body = snakeGame.getBody();
				for (int i = 0; i < body.size(); i++) {
					Point p = body.get(i);
					if (i == 0) {
						g.setColor(Color.GREEN); // Head
					} else {
						g.setColor(Color.BLUE); // Body
					}
					g.fillRect(p.x * CELL_SIZE, p.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				}

				// Draw score
				g.setColor(Color.WHITE);
				g.drawString("Score: " + snakeGame.getScore(), 10, HEIGHT + 20);
			}
		};
		gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// Container panel for layout
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(gamePanel, BorderLayout.CENTER); // Game in center
		container.add(leadPanel, BorderLayout.EAST);   // Leaderboard on right
		leadPanel.add(startButton, BorderLayout.NORTH); // Start/Restart button at top of leaderboard
		leadPanel.add(snakeBackButton, BorderLayout.SOUTH); // Back button at bottom
		snakeWin.setContentPane(container);
		snakeWin.pack();
		snakeWin.setLocationRelativeTo(null);
		snakeWin.setVisible(true);

		// Timer for game loop, updates game and repaints
		final javax.swing.Timer[] timer = new javax.swing.Timer[1];
		timer[0] = new javax.swing.Timer(180, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!snakeGame.isAlive()) {
					// If alive, update game and repaint
					timer[0].stop();
					startButton.setText("Restart Game");
					startButton.setVisible(true);
				
					// Read, update, write highscores
					java.util.List<HighscoreEntry> scores = readHighScores();
					String username = currentUsername; 
					if (username == null) username = "Player";
					scores.add(new HighscoreEntry(username, snakeGame.getScore()));
					scores.sort((a, b) -> Integer.compare(b.score, a.score));
					if (scores.size() > 10) scores = scores.subList(0, 10);
					writeHighScores(scores);
					updateLeaderboard(leaderboardArea, scores);
				}
			}
		});

		// Key listener for direction control
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		gamePanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!snakeGame.isAlive()) return; // Ignore input if dead
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:    snakeGame.setDirection("UP"); break;
					case KeyEvent.VK_DOWN:  snakeGame.setDirection("DOWN"); break;
					case KeyEvent.VK_LEFT:  snakeGame.setDirection("LEFT"); break;
					case KeyEvent.VK_RIGHT: snakeGame.setDirection("RIGHT"); break;
				}
			}
		});

		// Start/Restart button listener
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset game, hide button, focus panel, start timer
				snakeGame.reset();
				startButton.setVisible(false);
				gamePanel.requestFocusInWindow();
				timer[0].start();
			}
		});
		
		// Back button listener
		snakeBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Minimize current window, unminimize menu
				snakeGame.reset();
				snakeWin.dispose(); // Close snake window
        		menu(); // Open menu window
			}
		});

		// Show button at start
		startButton.setVisible(true);
	}

	private static final String SNAKE_HIGHSCORE_FILE = "snake_highscores.txt"; // Snake leaderboard

	static class HighscoreEntry {
		String username;
		int score;
		HighscoreEntry(String username, int score) {
			this.username = username;
			this.score = score;
		}
	}

	/**
	 * Reads the top 10 scores from the highscore file.
	 */
	private static java.util.List<HighscoreEntry> readHighScores() {
		java.util.List<HighscoreEntry> scores = new ArrayList<>();
		try (Scanner sc = new Scanner(new File(SNAKE_HIGHSCORE_FILE))) {
			while (sc.hasNext()) {
				String username = sc.next();
				if (sc.hasNextInt()) {
					int score = sc.nextInt();
					scores.add(new HighscoreEntry(username, score));
				}
			}
		} catch (Exception e) {
			// File might not exist yet, that's fine
		}
		scores.sort((a, b) -> Integer.compare(b.score, a.score));
		if (scores.size() > 10) scores = scores.subList(0, 10);
		return scores;
	}

	/**
	 * Writes the top 10 scores to the highscore file.
	 */
	private static void writeHighScores(java.util.List<HighscoreEntry> scores) {
		try (PrintWriter pw = new PrintWriter(new File(SNAKE_HIGHSCORE_FILE))) {
			for (int i = 0; i < Math.min(10, scores.size()); i++) {
				pw.println(scores.get(i).username + " " + scores.get(i).score);
			}
		} catch (Exception e) {
			System.out.println("Could not write highscores: " + e);
		}
	}

	/**
	 * Updates the leaderboard display in the given JTextArea.
	 */
	private static void updateLeaderboard(JTextArea leaderboardArea, java.util.List<HighscoreEntry> scores) {
		StringBuilder sb = new StringBuilder("Leaderboard:\n");
		for (int i = 0; i < scores.size(); i++) {
			sb.append(String.format("%2d. %-12s %d\n", i + 1, scores.get(i).username, scores.get(i).score));
		}
		leaderboardArea.setText(sb.toString());
	}

	/**
	 * Stock window function for buying and selling stock, 
	 * viewing current prices, and current funds.
	 **/
	public static void employ(){
		// Create management object for references
		Management employees = new Management();
		// Create employment window and properties
		JFrame employWin = new JFrame("Employees");
		employWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		employWin.setSize(600, 300);
		employWin.setLayout(new GridLayout(3,2));
		/*// Create employee lists for iteration creation of panels and such
		ArrayList<String> workerNames = new ArrayList<String>(employees.viewNames());
		ArrayList<Integer> stocks = new ArrayList<Integer>(employees.viewWage());
		// Iterate through names making panels and componentes for each name
		for (int i = 0; i < workerNames.size(); i++) {
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
		*/
		
		// Display window
		employWin.setLocationRelativeTo(null);
		employWin.setVisible(true);
	}

	/**
	 * This function shows a splash screen with the logo and sound taken from the HD2 RPG project
	 * This was a pain, but it works so hurrah
	 * Way, way too much code I had to look up, best considered copied entirely from various sources
	 * 
	 */
	public static void showSplashScreen() {
	    // Basic splash screen settings
	    int SCREEN_WIDTH = 400;
	    int SCREEN_HEIGHT = 250;
	    int DURATION = 4700; // milliseconds
	    String IMAGE_PATH = "FreedomTM.png";
	    String SOUND_PATH = "Logo.wav";
	
	    // Create splash window
	    JWindow splash = new JWindow();
	    splash.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	    splash.setLocationRelativeTo(null);
	
	    // Load the logo image
	    ImageIcon icon = new ImageIcon(IMAGE_PATH);
	    Image logo = icon.getImage();
	
	    // Play the startup sound
	    try {
	        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(SOUND_PATH));
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioIn);
	        clip.start();
	    } catch (Exception e) {
	        System.out.println("Sound error: " + e);
	    }
	
	    // Panel that draws the image
	    JPanel panel = new JPanel() {
	        long start = System.currentTimeMillis();
	
	        @Override
	        // Okay so this part is a bit confusing but if I understand it correctly, 
			// override tells java we are "replacing" JPanel's colors and tranparency method
			// so because normally that would result in errors, we override it to say
			// "hey don't worry about this" to java. 
			// The protected and super tells everything else that it's normal
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	
	            // How long the animation has been running
	            long elapsed = System.currentTimeMillis() - start;
	            double progress = Math.min(elapsed / (double) DURATION, 1.0);
	
	            // Zoom animation
	            double zoom = 0.9 + (progress * 0.12);
	            int w = (int)(SCREEN_WIDTH * zoom);
	            int h = (int)(SCREEN_HEIGHT * zoom);
	
	            // Brightness animation 
	            double brightness = 0.5 + (progress * 0.4);
	
	            // Scale image
	            BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	            Graphics2D g2 = scaled.createGraphics();
	            g2.drawImage(logo, 0, 0, w, h, null);
	
	            // Apply brightness
	            int alpha = (int)(255.0 * (1.0 - brightness));
	            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
	            g2.setColor(Color.BLACK);
	            g2.fillRect(0, 0, w, h);
	            g2.dispose();
	
	            // Draw image centered on image panel
	            int x = (SCREEN_WIDTH - w) / 2;
	            int y = (SCREEN_HEIGHT - h) / 2;
	            g.drawImage(scaled, x, y, null);
	        }
	    };

		// Add panel and make window visible
	    splash.add(panel);
	    splash.setVisible(true);
	
	    // Timer to update animation at about 60 fps
	    javax.swing.Timer splashDown = new javax.swing.Timer(1000 / 60, null);
	    splashDown.addActionListener(new ActionListener() {
	        long start = System.currentTimeMillis();
	
	        public void actionPerformed(ActionEvent e) {
	            panel.repaint(); // tick/update image
	
	            // End animation after duration above
	            if (System.currentTimeMillis() - start > DURATION) {
	                splashDown.stop();
	                splash.setVisible(false);
	                splash.dispose();
	            }
	        }
	    });
	
	    splashDown.start();
	
	    // Pause program until animation finishes, acts like a wait between each frame
	    try {
	        Thread.sleep(DURATION + 100);
	    } catch (InterruptedException ex) {}
	}

    public static void main(String[] args) {
		// For a minor extra, loading screen
		// Had to look up how to do this
		// Image and from the Helldivers 2 rpg project
		showSplashScreen(); // this was a pain

		login(); // The beginning of it all
    }
}
