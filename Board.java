import java.util.Random;

public class Board {
	private Square[][] board;
	private int smallShips;
	private int mediumShips;
	private int largeShips;
	private int smallShipsGenerated = 0;
	private int mediumShipsGenerated = 0;
	private int largeShipsGenerated = 0;
	private int ships;

	public Board(int rows, int columns) {
		this.board = new Square[rows][columns];
		this.smallShips = SmallBattleship.TOTAL_ALLOWED;
		this.mediumShips = MediumBattleship.TOTAL_ALLOWED;
		this.largeShips = LargeBattleship.TOTAL_ALLOWED;
		this.ships = SmallBattleship.TOTAL_ALLOWED + MediumBattleship.TOTAL_ALLOWED + LargeBattleship.TOTAL_ALLOWED;
		this.populateBoard();
		this.generateBattleships();
	}

	public Square[][] getBoard() {
		return board;
	}

	public void setBoard(Square[][] board) {
		this.board = board;
	}

	public int getSmallShips() {
		return smallShips;
	}

	public void setSmallShips(int smallShips) {
		this.smallShips = smallShips;
	}

	public int getMediumShips() {
		return mediumShips;
	}

	public void setMediumShips(int mediumShips) {
		this.mediumShips = mediumShips;
	}

	public int getLargeShips() {
		return largeShips;
	}

	public void setLargeShips(int largeShips) {
		this.largeShips = largeShips;
	}

	public int getShips() {
		return ships;
	}

	public void setShips(int ships) {
		this.ships = ships;
	}

	//Populate the 2-dimensional array board with squares
	public void populateBoard() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[0].length; j++) {
				this.board[i][j] = new Square(i, j);
			}
		}
	}

	//Place the battleships inside the battleship game board
	public void generateBattleships() {

/*
 * 		Place the battleships on the battleship game board:
 * 		1. Check if the number of ships for each type has not reached the limit yet
 * 		2. Randomly choose a square
 * 		3. Determine the type of ship to be placed
 * 		4. Determine the orientation of the ship
 * 		5. Check if the ship with the chosen row, chosen column, with that particular type, and orientation can be placed in the square
 * 		6. If the ship can be placed, then place the ship on that square
 * 		
 * 		Repeat steps 1-6 until every type of ship reaches the number of required ships
 */
		
		//As long as not all of the ships are placed, keep placing the ships
		while (!isAllShipsPlaced()) {
			
			// Get a random row and column on the board
			int chosenRow = this.getRandomRow();
			int chosenColumn = this.getRandomColumn();
			
			//Get the type of ship
			int shipType = this.getRandomShipType();

			// Determine ship orientation
			// True = vertical, false = horizontal
			boolean vertical = this.coinFlip();
			
			//If we can place the chosen ship type on the chosen row,column, then place the ship of the chosen ship type
			if(this.canPlaceShip(chosenRow, chosenColumn, shipType, vertical)) {
				this.placeShip(chosenRow, chosenColumn, shipType, vertical);
			}
		}
	}
	
	//To place a ship in the area to be occupied by the ship of that particular type
	private void placeShip(int row, int column, int shipType, boolean vertical) {
		
		Battleship ship = createShipByType(shipType);
		
		//For each square to be occupied,set the battleship there AND set the square to has a ship
		for (int i = 0; i < ship.getSize(); i++) {
			if (vertical) {
				this.board[row + i][column].setBattleship(ship);
				this.board[row + i][column].setHasShip(true);
//				System.out.println(shipType);
//				System.out.println("Vertical");
//				System.out.println(row + i);
//				System.out.println(column);
//				System.out.println("");
			} else {
				this.board[row][column + i].setBattleship(ship);
				this.board[row][column + i].setHasShip(true);
//				System.out.println(shipType);
//				System.out.println("Horizontal");
//				System.out.println(row);
//				System.out.println(column + i);
//				System.out.println("");
			}
		}
		
		//Once finished with adding the ship to the squares, increment the number of ships placed for that ship type
		this.incrementShipCount(shipType);
	}
	
	//To check for all types of ship whether each type of ship has reached the required number of ships placed
	private boolean isAllShipsPlaced() {
		return smallShipsGenerated >= this.smallShips 
				&& mediumShipsGenerated >= this.mediumShips 
				&& largeShipsGenerated >= this.largeShips;
	}
	
	//Get random row on board
	private int getRandomRow() {
		Random r = new Random();
		return r.nextInt(this.board.length);
	}
	
	//Get random column on board
	private int getRandomColumn() {
		Random r = new Random();
		return r.nextInt(this.board[0].length);
	}
	
	//Get random ship type to be placed on board
	private int getRandomShipType() {
		Random r = new Random();
		return r.nextInt(3) + 1; //Ship type 1 = small ship, ship type 2 = medium ship , ship type 3 = large ship
	}
	
	//Get random orientation of the ship. True = vertical, false = horizontal
	private boolean coinFlip() {
		Random r = new Random();
		return r.nextBoolean();
	}
	
	//To increment the number of ships placed for each type of ship
	private void incrementShipCount(int shipType) {
		if(shipType == 1) {
			this.smallShipsGenerated++;
		}
		
		if(shipType ==  2) {
			this.mediumShipsGenerated++;
		}
		
		if(shipType == 3) {
			this.largeShipsGenerated++;
		}
	}

	//Check if the ship type can be placed in the specified row and column with the chosen orientation
	private boolean canPlaceShip(int row, int column, int shipType, boolean vertical) {
		
		//If the number of ships of a particular ship type has not reached the limit yet..
		if (!hasReachedShipLimit(shipType)) {
			
			//Create the ship with the corresponding type
			Battleship ship = createShipByType(shipType);
			
			//Check if the ship of the particular ship type fits inside the battleship board
			int size = ship.getSize();	
			if (isWithinBounds(row, column, size, vertical)) {
				//If ship is within the game board, then check if area to be occupied has no ships in it
				return isAreaUnoccupied(row, column, size, vertical);
			}
		}
		return false;
	}
	
	//Helper method for canPlaceShip, checks if the number of ship for the chosen type has not reached the limit
	private boolean hasReachedShipLimit(int shipType) {
		if (shipType == 1) {
			return smallShipsGenerated >= this.smallShips;
		}
		
		if (shipType == 2) {
			return mediumShipsGenerated >= this.mediumShips;
		}
		
		return largeShipsGenerated >= this.largeShips;
	}
	
	//Create a ship by type
	private Battleship createShipByType(int shipType) {
		if (shipType == 1) {
			return new SmallBattleship();
		}
		
		if (shipType == 2) {
			return new MediumBattleship();
		}
		
		return new LargeBattleship();
	}
	
	//Check if the ship of a certain type fits in the board for a given chosen row & column
	private boolean isWithinBounds(int row, int column, int size, boolean vertical) {
		if (vertical) {
			return row + size < board.length; // returns true if the ship is oriented vertically, and the ship does not exceed the row index of the board
		} else {
			return column + size < board[0].length; // returns true if the ship is oriented horizontally, and the ship does not exceed the column index of the board
		}
	}
	
	//Check if the area to be occupied by a ship of a certain type is occupied by another ship or not
	private boolean isAreaUnoccupied(int row, int column, int size, boolean vertical) {
		if (vertical) {
			for (int i = 0; i < size; i++) {
				if (this.board[row + i][column].isHasShip()) { //If a square to be occupied has a ship, then we can't place a ship in the chosen square (return false)
					return false;
				}
			}
		} else {
			for (int j = 0; j < size; j++) {
				if (this.board[row][column + j].isHasShip()) {
					return false;
				}
			}
		}
		
		return true; //If all squares to be occupied by the ship is NOT occupied, then we can place a ship in the chosen square (return true)
	}
	
	//Prints the battleship game board
	public String toString() {
		String string = "";

		for (int i = this.board.length - 1; i >= 0; i--) {
			for (int j = 0; j < this.board[0].length; j++) {
				string += this.board[i][j];
			}
			string += "\n";
		}

		return string;
	}

	// For personal use, just printing the board with the positions of the ship
	public void printBoard() {
		for (int i = this.board.length - 1; i >= 0; i--) {
			for (int j = 0; j < this.board[0].length; j++) {
				if (this.board[i][j].isHasShip()) {
					if(this.board[i][j].getBattleship().getSize() == 1) {
						System.out.print("1 ");
					}
					
					if(this.board[i][j].getBattleship().getSize() == 2) {
						System.out.print("2 ");
					}
					
					if(this.board[i][j].getBattleship().getSize() == 3) {
						System.out.print("3 ");
					}
					
				} else {
					System.out.print("_ ");
				}
			}
			System.out.println("");
		}
	}



}
