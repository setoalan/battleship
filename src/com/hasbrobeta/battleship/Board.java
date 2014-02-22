package com.hasbrobeta.battleship;

public class Board {

	private int numShipsPlaced = 0;
	private Square[] squares;
	
	public Board() {
		this.numShipsPlaced = 0;
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

	public Square[] getSquares() {
		return squares;
	}

	public void setSquares(Square[] squares) {
		this.squares = squares;
	}
	
}
