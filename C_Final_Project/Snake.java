/**
 * Snake class for snake game logic.
 * Handles movement, food, collision, score, and state.
 * All graphics and input are handled in MainProgram.
 */
import java.awt.Point;      // For snake body and food positions
import java.util.LinkedList; // For snake body
import java.util.Random;       // For returning the body as a List
/**
 * This class is for the snake game that the workers or managers can play during their shifts at the card shop. 
 * It holds everything for it and is a unique twist on the original.
 */
public class Snake {
    private LinkedList<Point> body; // List representing the snake's body, with the head at the front
    private Point food; // Current food position
    private String direction; // Current direction of movement ("UP", "DOWN", "LEFT", "RIGHT"), paris with nextDirection to avoid clipping issue
    private boolean alive; // Whether the snake is alive
    private int score; // Current score
    private int gridWidth, gridHeight; // Grid dimensions
    private Random rand = new Random(); // Random number generator for food placement
    private String nextDirection; // Next direction to move, pairs with direction to avoid direction reversal

    /**
     * Constructs a new Snake game with the given grid size.
     * @param gridWidth the width of the grid
     * @param gridHeight the height of the grid
     */
    public Snake(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        reset();
    }

    /**
     * Resets the snake game to the initial state.
     * Snake is placed in the center, direction is set to RIGHT, score is reset, and food is spawned.
     */
    public void reset() {
        body = new LinkedList<>();
        body.add(new Point(gridWidth / 2, gridHeight / 2)); // Start in center
        direction = "RIGHT";
        nextDirection = "RIGHT";
        alive = true;
        score = 0;
        spawnFood();
    }

    /**
     * Sets the snake's direction, preventing reversal.
     * @param dir the new direction ("UP", "DOWN", "LEFT", "RIGHT")
     */
    public void setDirection(String dir) {
        // Prevent reversing direction
        if ((direction.equals("UP") && dir.equals("DOWN")) ||
            (direction.equals("DOWN") && dir.equals("UP")) ||
            (direction.equals("LEFT") && dir.equals("RIGHT")) ||
            (direction.equals("RIGHT") && dir.equals("LEFT"))) {
            return;
        } else {
			nextDirection = dir; // Windows be like:
		}
    }

    /**
     * Updates the snake's position.
     * Moves the snake, checks for collisions, handles eating food and growing, and updates alive state.
     */
    public void update() {
        if (!alive) return;
        direction = nextDirection; // Apply the next direction
        Point head = new Point(body.getFirst()); // Get current head position
        // Move head in the current direction
        switch (direction) {
            case "UP": head.y--; 
				break;
            case "DOWN": head.y++; 
				break;
            case "LEFT": head.x--; 
				break;
            case "RIGHT": head.x++; 
				break;
        }
        // Check collision with walls or self
        if (head.x < 0 || head.x >= gridWidth || head.y < 0 || head.y >= gridHeight || body.contains(head)) {
            alive = false;
            return;
        }
        // Add new head position
        body.addFirst(head);
        // Check if food is eaten
        if (head.equals(food)) {
            score++;
            spawnFood(); // Place new food
        } else {
            body.removeLast(); // Remove tail if not eating
        }
    }

    /**
     * Spawns food at a random location not occupied by the snake.
     */
    private void spawnFood() {
        do {
            food = new Point(rand.nextInt(gridWidth), rand.nextInt(gridHeight));
        } while (body.contains(food));
    }

    /**
     * Returns the snake's body as a list of Points.
     * @return list of Points representing the snake's body
     */
    public java.util.List<Point> getBody() { 
        return body; 
    }

    /**
     * Returns the current food location.
     * @return Point representing the food's location
     */
    public Point getFood() { 
        return food; 
    }

    /**
     * Returns true if the snake is alive.
     * @return true if alive, false if dead
     */
    public boolean isAlive() { 
        return alive; 
    }

    /**
     * Returns the current score.
     * @return the score
     */
    public int getScore() { 
        return score; 
    }
}
