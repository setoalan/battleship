package com.hasbrobeta.battleship;

public class Square {
	
	private boolean isOccupied;
	private int shipNum;
	private int shipSegmentNum;
	private int shipDirection;
	private boolean isShot;
	
	public Square() {
		isOccupied = false;
		shipNum = -1;
		shipSegmentNum = -1;
		shipDirection = -1;
		isShot = false;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public int getShipNum() {
		return shipNum;
	}

	public void setShipNum(int shipNum) {
		this.shipNum = shipNum;
	}

	public int getShipSegmentNum() {
		return shipSegmentNum;
	}

	public void setShipSegmentNum(int shipSegmentNum) {
		this.shipSegmentNum = shipSegmentNum;
	}

	public int getShipDirection() {
		return shipDirection;
	}

	public void setShipDirection(int shipDirection) {
		this.shipDirection = shipDirection;
	}

	public boolean isShot() {
		return isShot;
	}

	public void setShot(boolean isShot) {
		this.isShot = isShot;
	}

}
