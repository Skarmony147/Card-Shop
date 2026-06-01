import javax.swing.*; // For swing graphics
import java.awt.*; // For swing graphics
import java.awt.event.*; // For event listeners
import java.util.*; // For array lists

//For splash screen and misc
import java.awt.image.*;
import javax.sound.sampled.*;
import java.io.*;


/**
 * Main program for final project by Sebastian and Charlie
 **/
public class MainProgram{
	private static Random rando = new Random(); // For "certain things".
	private static String rank; // Rank of user
	private static String currentUsername = "Player"; // username of user used for snake leaderboard
	private static Management employees = new Management(); // Create management object for employ window
	private static Stocks inventory = new Stocks(); // Create stocks object for stock window
	private static Report report = new Report(); // Report object to hold actions of the day
	private static Clip snakeMusicClip = null; // Clip for snake music
	private static final String SNAKE_HIGHSCORE_FILE = "snake_highscores.txt"; // Snake leaderboard
	
	/**
	 * Plays the button sound effect
	 */
	public static void playButtonSound() {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("ButtonSound.wav"));
			Clip buttonClip = AudioSystem.getClip();
			buttonClip.open(audioIn);
			buttonClip.start();
		} catch (Exception e) {
			System.out.println("Could not play button sound: " + e);
		}
	}

	/**
	 * Login window function where user enters username and password,
	 * which uses the LoginWindow class for the different operations.
	 **/
	public static void login(){
		// Create login window and properties
		JFrame logWin = new JFrame("Login");
		logWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logWin.setSize(250, 140);
		logWin.setLayout(new BorderLayout());
		logWin.setResizable(false);
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
				playButtonSound();
				String username = userTextField.getText();
				String password = passTextField.getText();
				currentUsername = username;
				if (log.authenticate(username, password)) {
					loginButton.setText("Success");
					// Use a timer for the delay
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
	public static void menu() {
		// Create menu window and properties
		JFrame menuWin = new JFrame("Menu");
		menuWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuWin.setSize(200, 220);
		menuWin.setLayout(new BorderLayout());
		menuWin.setResizable(false);
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(5,1));
		
		// Funds label
		JLabel menuFunds = new JLabel(String.format("Current funds: $%.2f", report.remaining()), SwingConstants.CENTER);
		menuPanel.add(menuFunds); 

		// Declare buttons outside the if/else blocks   it's a suprise tool that we'll use later
		JButton snakeButton = null;
		JButton stockButton = null;
		JButton employButton = null;

		// Create buttons for snake, stocks, and employee management depending on rank
		if(rank.equals("Manager")){
			snakeButton = new JButton("Snake");
			stockButton = new JButton("Stocks");
			employButton = new JButton("Employees");
			menuPanel.add(snakeButton);
			menuPanel.add(stockButton);
			menuPanel.add(employButton);
		} else if (rank.equals("Employee")){
			snakeButton = new JButton("Snake");
			stockButton = new JButton("Stocks");
			JLabel employees = new JLabel("Employees", SwingConstants.CENTER);
			menuPanel.add(snakeButton);
			menuPanel.add(stockButton);
			menuPanel.add(employees);
		} else if (rank.equals("Rookie")){
			stockButton = new JButton("Stocks");
			JLabel snek = new JLabel("Snake", SwingConstants.CENTER);
			JLabel employees = new JLabel("Employees", SwingConstants.CENTER);
			menuPanel.add(stockButton);
			menuPanel.add(snek);
			menuPanel.add(employees);
		}

		// Add listeners only if the button exists
		if (snakeButton != null) {
			snakeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					playButtonSound();
					menuWin.dispose();
					snake();
				}
			});
		}
		if (stockButton != null) {
			stockButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					playButtonSound();
					menuWin.dispose();
					stock();
				}
			});
		}
		if (employButton != null) {
			employButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					playButtonSound();
					menuWin.dispose();
					employ();
				}
			});
		}
		
		// Receipt button
		JButton endOfDay = new JButton("Receipt");
		menuPanel.add(endOfDay);
		
		// Receipt listener
		endOfDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playButtonSound();
				menuWin.dispose();
				receipt();
			}
		});

		// Add panel to window, center, and show
		menuWin.add(menuPanel, BorderLayout.CENTER);
		menuWin.setLocationRelativeTo(null);
		menuWin.setVisible(true);
	}
	
	/**
	 * Stock window function for buying and selling stock, 
	 * viewing current prices, and current funds.
	 **/
	public static void stock(){
		// Create stock window and properties
		JFrame stockWin = new JFrame("Stocks");
		stockWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		stockWin.setSize(500, 200);
		stockWin.setLayout(new GridLayout(6,1));
		stockWin.setResizable(false);
		// Create list of stocks for iteration creation of panels and such
		// try/catch didn't work so
		@SuppressWarnings("unchecked")
		ArrayList<String> stockNames = new ArrayList<String>((Collection<? extends String>) inventory.viewBrands());
		@SuppressWarnings("unchecked")
		ArrayList<Integer> stocks = new ArrayList<Integer>((Collection<? extends Integer>) inventory.viewStocks());
		ArrayList<String> boxes = new ArrayList<String>();
		String[] boxType = {"ETB", "Boost bundle", "Boost box", "UPC"};
		for(int i=0; i<boxType.length; i++){
			boxes.add(boxType[i]);
		}
		// Labels for each thing
		JPanel labelPanel = new JPanel(new GridLayout(1, 6));
		JLabel stockLabel = new JLabel("Stocks", SwingConstants.CENTER);
		JLabel boxLabel = new JLabel("Box Type", SwingConstants.CENTER);
		JLabel pricesLabel = new JLabel("Price", SwingConstants.CENTER);
		JLabel amountLabel = new JLabel("Amount", SwingConstants.CENTER);
		JLabel buySellLabel = new JLabel("Buy/Sell", SwingConstants.CENTER);
		JLabel placeholder = new JLabel();
		// Add labels to panel
		labelPanel.add(stockLabel);
		labelPanel.add(boxLabel);
		labelPanel.add(pricesLabel);
		labelPanel.add(amountLabel);
		labelPanel.add(buySellLabel);
		labelPanel.add(placeholder);
		stockWin.add(labelPanel, BorderLayout.CENTER);
		// Bottom row output thing, funds, menu button
		JPanel lowStocks = new JPanel(new GridLayout(1, 2));
		JLabel stockFunds = new JLabel(String.format("Funds: $%.2f", report.remaining()), SwingConstants.CENTER);
		JButton stockBackButton = new JButton("Menu");
		// Iterate through stockNames making panel and components for each stock
		for (int i = 0; i < stockNames.size(); i++) {
			final int stockIndex = i; // Get the index for use in the buy/sell listeners
			// Create panel and components
			String name = stockNames.get(i);
			JPanel stockPanel = new JPanel(new GridLayout(1, 6));
			JLabel nameLabel = new JLabel(stockNames.get(i), SwingConstants.CENTER);
			JComboBox<String> boxDrop = new JComboBox<>(boxType);
			JLabel priceLabel = new JLabel(String.format("$%.2f", (double)inventory.viewPrice("ETB")), SwingConstants.CENTER);
			JLabel numLabel = new JLabel(Integer.toString(stocks.get(i)), SwingConstants.CENTER);
			JButton buyButton = new JButton("Buy");
			JButton sellButton = new JButton("Sell");
			// Update price when box type changes
			boxDrop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String selectedBox = (String)boxDrop.getSelectedItem();
					priceLabel.setText(String.format("$%.2f", (double)inventory.viewPrice(selectedBox)));
				}
			});
			// Add action listeners for buy/sell buttons
			buyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					playButtonSound();
					inventory.buyStock(name,1);
					numLabel.setText(String.valueOf(inventory.viewStocks().get(stockIndex)));
					report.addAction("Bought " + nameLabel.getText() + " stock.", -Double.parseDouble(priceLabel.getText().replace("$", "")));
					stockFunds.setText(String.format("Funds: $%.2f", report.remaining()));
				}
			});
			sellButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					playButtonSound();
					inventory.sellStock(name,(String)boxDrop.getSelectedItem(),1);
					numLabel.setText(String.valueOf(inventory.viewStocks().get(stockIndex)));
					report.addAction("Sold " + nameLabel.getText() + " stock.", Double.parseDouble(priceLabel.getText().replace("$", "")));
					stockFunds.setText(String.format("Funds: $%.2f", report.remaining()));
				}
			});
			// Add stuff to panel
			stockPanel.add(nameLabel);
			stockPanel.add(boxDrop);
			stockPanel.add(priceLabel);
			stockPanel.add(numLabel);
			stockPanel.add(buyButton);
			stockPanel.add(sellButton);
			
			// Add panel to window
			stockWin.add(stockPanel, BorderLayout.CENTER);
		}
		// Add bottom row to window after everything else
		lowStocks.add(stockFunds);
		lowStocks.add(stockBackButton);
		stockWin.add(lowStocks);
		
		// Back button listener
		stockBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playButtonSound();
				// Close stock window, open menu
				stockWin.dispose(); // Close stock window
        		menu(); // Open menu window
			}
		});
		
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

		// Stuff for snake framerate
		final int INITIAL_DELAY = 200; // starting speed
		final int MIN_DELAY = 30;      // fastest speed allowed
		final int DELAY_DECREMENT = 4; // how much to speed up per fruit

		final int[] timerDelay = { INITIAL_DELAY }; // use array to allow modification in inner class

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
				if (snakeGame.isAlive()) {
					// If alive, update game and repaint
					int oldScore = snakeGame.getScore();
					snakeGame.update();
					gamePanel.repaint();

					// If score increased, speed up the timer
					// We often partkae in a modest amount of tomfoolery
					if (snakeGame.getScore() > oldScore) {
						timerDelay[0] = Math.max(MIN_DELAY, timerDelay[0] - DELAY_DECREMENT);
						timer[0].setDelay(timerDelay[0]);
					}
				} else {
					// Stop game and show restart button
					timer[0].stop();
					stopSnakeMusic();
					startButton.setText("Restart Game");
					startButton.setVisible(true);
					// Read, update, write highscores
					java.util.List<HighscoreEntry> scores = readHighScores();
					String username = currentUsername; 
					if (username == null) 
						username = "Player";
					scores.add(new HighscoreEntry(username, snakeGame.getScore()));
					scores.sort((a, b) -> Integer.compare(b.score, a.score));
					if (scores.size() > 10) 
						scores = scores.subList(0, 10);
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
					case KeyEvent.VK_UP:    
						snakeGame.setDirection("UP"); break;
					case KeyEvent.VK_DOWN:  
						snakeGame.setDirection("DOWN"); break;
					case KeyEvent.VK_LEFT:  
						snakeGame.setDirection("LEFT"); break;
					case KeyEvent.VK_RIGHT: 
						snakeGame.setDirection("RIGHT"); break;
				}
			}
		});

		// Start/Restart button listener
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playButtonSound();
				// Reset game, hide button, focus panel, start timer
				snakeGame.reset();
				startButton.setVisible(false);
				gamePanel.requestFocusInWindow();
				timerDelay[0] = 200; // Reset speed
				timer[0].setDelay(timerDelay[0]);
				timer[0].start();
				report.addAction("Played snake.", -0.50);
				playLoopingSound("SnakeLoop.wav");
			}
		});
		
		// Back button listener
		snakeBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playButtonSound();
				// Close snake window and reset game, open menu
				stopSnakeMusic();
				snakeGame.reset();
				snakeWin.dispose(); // Close snake window
        		menu(); // Open menu window
			}
		});

		// Show button at start
		startButton.setVisible(true);
	}

	// Used to store a player's username and their score
    // for snake game leaderboard
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

	/*
	* Plays looping snake music
	*/
	private static void playLoopingSound(String filename) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filename));
			snakeMusicClip = AudioSystem.getClip();
			snakeMusicClip.open(audioIn);
			snakeMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("Could not play sound: " + e);
		}
	}

	/*
	* Stops the snake music if it's playing
	*/
	private static void stopSnakeMusic() {
		if (snakeMusicClip != null && snakeMusicClip.isRunning()) {
			snakeMusicClip.stop();
			snakeMusicClip.close();
			snakeMusicClip = null;
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
	 * Employment window for firing, hiring, and promoting employees.
	 **/
	public static void employ(){
		// Create employment window and properties
		JFrame employWin = new JFrame("Employees");
		employWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		employWin.setSize(750, 160);
		employWin.setLayout(new GridLayout(3,2));
		employWin.setResizable(false);
		// Menu and output/reason label
		JLabel reasonLabel = new JLabel(("[Output]"), SwingConstants.CENTER);
		JButton employBackButton = new JButton("Menu");
		// Create employee lists for iteration creation of panels and such
		ArrayList<String> workerNames = new ArrayList<String>();
		ArrayList<Double> wages = new ArrayList<Double>();
		workerNames = employees.getEmployees();
		wages = employees.getWages();
		while(workerNames.size()<4){
			if(workerNames.size()<4){
				workerNames.add("");
				wages.add((double)0);
			}
		}
		// Iterate through names making panels and components for each name
		for (int i = 0; i < workerNames.size(); i++) {
			String name = workerNames.get(i);
			Double wage = wages.get(i);
			JPanel employeePanel = new JPanel(new GridLayout(1, 5));
			JLabel nameLabel = new JLabel(workerNames.get(i), SwingConstants.CENTER);
			JLabel wageLabel = new JLabel(String.format("$%.2f", wage), SwingConstants.CENTER);
			JButton fireButton = new JButton("Fire");
			JButton promoteButton = new JButton("Promote");
			JButton hireButton = new JButton("Hire New");
			// Add action listeners for fire/promote buttons
			fireButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					playButtonSound();
					String[] reasons = {
						"No comment.", 
						"They had a criminal history.", 
						"I was bribed.",
						"I was paying them too much.",
						"It was either tell them or sell them.",
						"They yawned on the job. Can't have that.",
						"Customers liked them more than me.",
						"They accidentally showed up to a shift that wasn't for them.",
						"Idiot thought they could outrun me.",
						"They were climbing the rank ladder a bit too fast.",
						"Just didn't like 'em.",
						"They thought they could use the full break.",
						"Someone switched the shifts.",
						"They spent their christmas with their family and not us.",
						"They won against me in chess.",
						"They thought it'd be funny to insult me.",
						"Arceus vs Babe Ruth vs Exodia vs Skullclamp didn't end well.",
						"They looked tired on the job."};
					final String reason = reasons[rando.nextInt(0, reasons.length)];
					employees.Fire(nameLabel.getText(), reason);
					reasonLabel.setText(reason);
					// When firing an employee, delete all the stuff for them and put hire button
					employeePanel.remove(nameLabel);
					employeePanel.remove(wageLabel);
					employeePanel.remove(fireButton);
					employeePanel.remove(promoteButton);
					employeePanel.setLayout(new BorderLayout());
					employeePanel.add(hireButton);
					employeePanel.repaint();
				}
			});
			// Hire button listener
			hireButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					playButtonSound();
					String[] names = { // Ripped the names from google
						"Liam", "Noah", "Oliver", "Theodore", "Henry",
						"James", "Elijah", "Mateo", "Lucas", "William",
						"Olivia", "Charlotte", "Emma", "Amelia", "Sophia",
						"Mia", "Isabella", "Evelyn", "Sofia", "Eliana",
					};
					String newName = names[rando.nextInt(0, names.length)];
					employees.Hire(newName, 14.50);
					// Re add stuff from firing
					employeePanel.remove(hireButton);
					employeePanel.setLayout(new GridLayout(1, 5));
					employeePanel.add(nameLabel);
					employeePanel.add(wageLabel);
					employeePanel.add(fireButton);
					employeePanel.add(promoteButton);
					nameLabel.setText(newName);
					wageLabel.setText(String.format("$%.2f", wage));
					employeePanel.repaint();
				}
			});
			// Promote button listener, increase pay by $0.50
			promoteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					playButtonSound();
					double newWage = Double.parseDouble(wageLabel.getText().replace("$", "")) + 0.50;
					employees.ChangePay(nameLabel.getText(), newWage);
					wageLabel.setText(String.format("$%.2f", newWage));
				}
			});
			// Add stuff to panel
			employeePanel.add(nameLabel);
			employeePanel.add(wageLabel);
			employeePanel.add(fireButton);
			employeePanel.add(promoteButton);
			// If no employee in the slot, delete all the stuff for them and put hire button
			if(workerNames.get(i)==""){
				employeePanel.remove(nameLabel);
				employeePanel.remove(wageLabel);
				employeePanel.remove(fireButton);
				employeePanel.remove(promoteButton);
				employeePanel.setLayout(new BorderLayout());
				employeePanel.add(hireButton);
				employeePanel.repaint();
			}
			// Add panel to window
			employWin.add(employeePanel, BorderLayout.CENTER);
		}
		// Add reason and menu stuff after the rest
		employWin.add(reasonLabel);
		employWin.add(employBackButton);
		// Back button listener
		employBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playButtonSound();
				// Close management window, open menu
				employWin.dispose(); // Close managment window
        		menu(); // Open menu window
			}
		});
		
		// Display window
		employWin.setLocationRelativeTo(null);
		employWin.setVisible(true);
	}
	
	/**
	 * Receipt of all the actions done and the result funds for the day;
	 * End of app
	 **/
	public static void receipt(){
		// Create receipt window and properties
		JFrame endWin = new JFrame("Login");
		endWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		endWin.setSize(300, 400);
		endWin.setLayout(new BorderLayout());
		endWin.setResizable(false);
		// Create panel with flow layout
		JPanel finalPanel = new JPanel();
		finalPanel.setLayout(new BorderLayout());
		// Create report text and end button
		JTextArea textPane = new JTextArea(report.report()); // Intensity intensifies
		textPane.setEditable(false);
		//Put the editor pane in a scroll pane.
		JScrollPane textScrollPane = new JScrollPane(textPane);
		textScrollPane.setVerticalScrollBarPolicy(
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textScrollPane.setPreferredSize(new Dimension(250, 145));
		textScrollPane.setMinimumSize(new Dimension(10, 10));
		// End button
		JButton endButton = new JButton("End day");
		endButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					playButtonSound();
					endWin.dispose();
				}
			});
		// Add components to the panel
		finalPanel.add(textScrollPane, BorderLayout.CENTER);
		finalPanel.add(endButton, BorderLayout.SOUTH);
		// Add panel window, center window, display window
		endWin.add(finalPanel, BorderLayout.CENTER);
		endWin.setLocationRelativeTo(null);
		endWin.setVisible(true);
	}

	/**
	 * This function shows a splash screen with the logo and sound taken from the HD2 RPG project
	 * This was a pain, but it works so hurrah
	 * Had to look up this code, best considered copied entirely from various sources
	 */
	public static void showSplashScreen() {
	    // Basic splash screen settings
	    int SCREEN_WIDTH = 400;
	    int SCREEN_HEIGHT = 250;
	    int DURATION = 4700; 
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
	/**
	 * This method starts the whole program up and creates your brand new card shop!
	 * 
	 * @param args This is the same as the other one, it doesn't really do anything, its just there for the method creation
	 * 
	 */
    public static void main(String[] args) {
		// For a minor extra, loading screen
		// Had to look up how to do this
		// Image and from the Helldivers 2 rpg project
		showSplashScreen(); // this was a pain

		login(); // The beginning of it all
    }
}
