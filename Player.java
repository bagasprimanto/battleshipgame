import java.util.Scanner;

public class Player {
	private Board board;
	private String name;
	private int score;
	private final Scanner scan = new Scanner(System.in);

	public Player(String name, Board board) {
		this.name = name;
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean takeTurn() {
		
		//Player inputs a guess
		int[] guess = this.getPlayerGuess();
		int guessRow = guess[0];
		int guessColumn = guess[1];
		
		//Check if guess is valid
		if (this.isValidGuess(guessRow,guessColumn)) {
			//If guess is valid then, check if player hit a ship
			if(this.isHit(guessRow,guessColumn)) {
				//If player hits a ship, then handle the hit ship
				this.handleHit(guessRow,guessColumn);
			} else {
				//If player DID NOT hit a ship, then handle a miss
				this.handleMiss(guessRow,guessColumn);
			}
		} else {
			//If player guess is invalid (player tries hitting a square which has been shot before), then handle repeated guess
			this.handleRepeatedGuess(guessRow,guessColumn);
		}
		
		//Print the game status after each player turn
		this.printStatus();
		
		//Return boolean whether the game is over or not. True = game is over, false = game is still ongoing
		return this.isGameOver();
	}
	
	
	//Returns the the player's guess (the square's coordinates)
	private int[] getPlayerGuess() {
		
		System.out.println(this.name + ", please input your guess:");
		String input = scan.nextLine();
		String guess[] = input.split(" ");
		int guessRow = Integer.valueOf(guess[0]);
		int guessColumn = Integer.valueOf(guess[1]);
		
		return new int[] {guessRow, guessColumn};
	}
	
	
	//Checks if the square with that particular row and column has been hit by a shot before
		//Guess is valid (true) if the square has not been hit by a short (isHasFiredShot() = false)
		//Guess is invalid (false) if the square has been hit by a shot (isHasFiredShot() = true)
	private boolean isValidGuess(int row, int column) {
		return !this.board.getBoard()[row][column].isHasFiredShot();
	}
	
	
	//Checks if the square with that particular row and column has been hit by a shot before
		//Guess is valid (true) if the square has not been hit by a short (isHasFiredShot() = false)
		//Guess is invalid (false) if the square has been hit by a shot (isHasFiredShot() = true)
	private boolean isHit(int row, int column) {
		return this.board.getBoard()[row][column].isHasShip();
	}
	
	
	//Does the actions to when a shit has been hit
	private void handleHit(int row, int column) {
		//Sets the square to has been hit
		this.board.getBoard()[row][column].setHasFiredShot(true);
		
		//Decreases the ship's health
		this.board.getBoard()[row][column].getBattleship().decreaseHealth();
		
		//Check if the ship that is hit is sunk
		if (this.board.getBoard()[row][column].getBattleship().isSunk()) {
			//If sunk, then... 
			//	add a point to the player
			this.score++;
			//	decrease the number of ships on the game board by 1
			this.board.setShips(this.board.getShips()-1);
			
			//Report that a ship has sunk and the player gets 1 more point
			if (this.score == 1) {
				System.out.println("You hit a ship at (" + row + "," + column + ") and it sank! " + this.name + " now has " + this.score + " point");
			} else {
				
				System.out.println("You hit a ship at (" + row + "," + column + ") and it sank! " + this.name + " now has " + this.score + " points");
			}
		  	
		} else {
			//If a ship is not hit, then just report that the ship is hit but it has not sunk
			System.out.println("You hit a ship at (" + row + "," + column + ") but it did not sink.");
		}
	}
	
	
	//Handle when a player hits a square that does not have a ship
	private void handleMiss(int row, int column) {
		this.board.getBoard()[row][column].setHasFiredShot(true);
		System.out.println("Missed at (" + row + "," + column + "). No ship at this square.");
	}
	
	
	//Handle when a player hits a square that has been hit before (invalid guess)
	private void handleRepeatedGuess(int row, int column) {
		System.out.println("You already fired at (" + row + "," + column + "). You lose a turn!");
	}
	
	
	//Prints the game's status: the number of ships left and prints the status of the board
	private void printStatus() {
		if (this.getBoard().getShips() == 1) {
			System.out.println("There is still " + this.board.getShips() + " ship left");
		} else {
			System.out.println("There are still " + this.board.getShips() + " ships left");
		}
		
		System.out.println(this.board);
	}

	
	//Checks if the game iis over or not
	//Game is over only if there are 0 ships left
	private boolean isGameOver() {
		return this.board.getShips() == 0;
	}
}
