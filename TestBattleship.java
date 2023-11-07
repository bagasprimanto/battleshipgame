import java.util.Scanner;

public class TestBattleship {

	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		//Initialise the battleship game board
		Board board = initializeBoard();
		
		board.printBoard();
		
		//Create 2 players
		Player playerOne = initializePlayer("Player 1", board);
		Player playerTwo = initializePlayer("Player 2", board);
		
		//Play the battleship game
		playGame(playerOne, playerTwo);
		
		//Announce the winner of the game
		announceWinner(playerOne, playerTwo);

	}
	
	//Creates the battleship game board
	private static Board initializeBoard() {
		Board board = new Board(10,10);
		return board;
	}
	
	//Initialises the player and sets a player to the battleship game board
	private static Player initializePlayer(String playerPrompt, Board board) {
		System.out.println(playerPrompt + ", please input your name:");
		return new Player(scan.nextLine(), board);
	}
	
	//Play the battleship game
	private static void playGame(Player playerOne, Player playerTwo) {
		boolean gameFinish = false;
		
		while(!gameFinish) {
			gameFinish = playerOne.takeTurn();
			if (!gameFinish) {
				gameFinish = playerTwo.takeTurn();
			}
		}
		
		System.out.println("There are no more ships left, game has finished!");
	}
	
	//Announce the winner. If player one gets more points then player one wins, whereas if player two gets more points then player two wins
	//If both players gets the same number of points, then it's a draw
	private static void announceWinner(Player playerOne, Player playerTwo) {
		if (playerOne.getScore() > playerTwo.getScore()) {
			System.out.println("Congratulations " + playerOne.getName() + " you win with " + playerOne.getScore() + " points!");
			System.out.println("Sorry " + playerTwo.getName() + " you lost with " + playerTwo.getScore() + " points!");
		} else if (playerOne.getScore() < playerTwo.getScore()){
			System.out.println("Congratulations " + playerTwo.getName() + " you win with " + playerTwo.getScore() + " points!");
			System.out.println("Sorry " + playerOne.getName() + " you lost with " + playerOne.getScore() + " points!");
		} else {
			System.out.println("Its a draw with " + playerTwo.getName() + " having " + playerTwo.getScore() + " points and " + playerOne.getName() + " having " + playerOne.getScore() + " points!");
		}
	}

}
