package com.hasbrobeta.battleship;

public class Board {

	private int numShipsPlaced;
	private int hitCounter;
	private int numCurShips;
	private Ships ships;
	private Square[] squares;
	
	public Board() {
		this.numShipsPlaced = 0;
		this.hitCounter = 0;
		this.numCurShips = 5;
		this.ships = new Ships();
		this.squares = new Square[100];
		for (int i=0; i<100; i++)
			this.squares[i] = new Square();
	}

	public int getNumShipsPlaced() {
		return numShipsPlaced;
	}

	public void addNumShipsPlaced() {
		this.numShipsPlaced++;
	}
	
	public int getHitCounter() {
		return hitCounter;
	}

	public void addHitCounter() {
		this.hitCounter++;
	}
	
	public int getNumCurShips() {
		return numCurShips;
	}

	public void subNumCurShips() {
		this.numCurShips--;
	}

	public Ships getShips() {
		return ships;
	}

	public void setShips(Ships ships) {
		this.ships = ships;
	}

	public Square[] getSquares() {
		return squares;
	}

	public void setSquares(Square[] squares) {
		this.squares = squares;
	}
	
	public class Ships {

		private int patrolBoat;
		private int destroyer;
		private int submarine;
		private int battleship;
		private int aircraftCarrier;
		
		public Ships() {
			patrolBoat = 2;
			destroyer = 3;
			submarine = 3;
			battleship = 4;
			aircraftCarrier = 5;
		}

		public int getPatrolBoat() {
			return patrolBoat;
		}

		public void subPatrolBoat() {
			this.patrolBoat--;
		}

		public int getDestroyer() {
			return destroyer;
		}

		public void subDestroyer() {
			this.destroyer--;
		}

		public int getSubmarine() {
			return submarine;
		}

		public void subSubmarine() {
			this.submarine--;
		}

		public int getBattleship() {
			return battleship;
		}

		public void subBattleship() {
			this.battleship--;
		}

		public int getAircraftCarrier() {
			return aircraftCarrier;
		}

		public void subAircraftCarrier() {
			this.aircraftCarrier--;
		}
		
	}
	
}
