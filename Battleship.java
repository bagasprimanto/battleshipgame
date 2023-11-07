
public class Battleship {
	private boolean isSunk;
	private int health;
	private final int size;
	
	public Battleship(int size) {
		this.isSunk = false;
		this.size = size;
		this.health = this.size;
	}
	
	
	
	public boolean isSunk() {
		return isSunk;
	}


	//Sets the ship to sunk if the ship has sunk
	public void setSunk(boolean isSunk) {
		this.isSunk = isSunk;
	}



	public int getHealth() {
		return health;
	}



	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getSize() {
		return this.size;
	}


	//Decrease ship's health, used when ship is hit
	public void decreaseHealth() {
		
		this.health--;
		
		//When ship's health reaches 0, then set the ship to sunk
		if (this.health == 0) {
			this.isSunk = true;
		}	
	}
	
	
}
