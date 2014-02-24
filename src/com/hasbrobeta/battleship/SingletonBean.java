package com.hasbrobeta.battleship;

public class SingletonBean {

	private Board[] players;

	public SingletonBean() {
		players = new Board[2];
		for (int i=0; i<2; i++)
			players[i] = new Board();
	}

	public Board[] getPlayers() {
		return players;
	}

	public void setPlayers(Board[] players) {
		this.players = players;
	}
	
}
