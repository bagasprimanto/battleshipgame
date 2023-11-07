
public class Square {
	private int row;
	private int column;
	private boolean hasShip;
	private boolean hasFiredShot;
	private Battleship battleship;
	
	public Square(int row, int column) {
		this.row = row;
		this.column = column;
		this.hasShip = false;
		this.hasFiredShot = false;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public boolean isHasShip() {
		return hasShip;
	}
	public void setHasShip(boolean hasShip) {
		this.hasShip = hasShip;
	}
	public boolean isHasFiredShot() {
		return hasFiredShot;
	}
	public void setHasFiredShot(boolean hasFiredShot) {
		this.hasFiredShot = hasFiredShot;
	}
	public Battleship getBattleship() {
		return battleship;
	}
	public void setBattleship(Battleship battleship) {
		this.battleship = battleship;
	}
	
	//Prints the square object as a string
	public String toString() {
		String string;
		
		if (this.hasFiredShot && !this.hasShip) {
			string = "o";
		} else if (this.hasFiredShot && this.hasShip) {
			string = "x";
		} else {
			string = "-";
		}
		
		return String.format("%3s", string);
	}
	
}
