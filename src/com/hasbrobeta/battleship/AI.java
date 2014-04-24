package com.hasbrobeta.battleship;

import java.util.Random;

public class AI{
	
	public void play() {
		Board[] players = BattleshipFragment.singletonBean.getPlayers();
		for(int i = 0; i < 100; i++) {
			Square otherSquare = players[0].getSquares()[i];
			Square mySquare = players[1].getSquares()[i];
			if (otherSquare.isOccupied() && !mySquare.isShot()) {
				mySquare.setShot(true);
				players[1].addHitCounter();
				return;
			}
	
		}
		//throw error
	}

    public void AIPlacement() {
    	System.out.print("entered");
        Random rand = new Random();
        int allPlaced  = 0;
        int shipSz  = 5;
        
        do {
        	System.out.print("loop");
            int start = rand.nextInt(100);
            boolean square = BattleshipFragment.singletonBean.getPlayers()[1].getSquares()[start].isOccupied();
            
            if (square == false) {

                boolean direction = rand.nextBoolean();
                boolean canPlace = true;

                if (direction == true) { //horizontal
                    if (start + shipSz % 10 != start + shipSz) 
                        canPlace = false;     
                    if (canPlace){
                        for (int i = start; i < start + shipSz; i++) {
                            if (BattleshipFragment.singletonBean.getPlayers()[1].getSquares()[i].isOccupied() == true) {
                                canPlace = false;
                                break;  
                            }
                        }
                    }

                    if (canPlace == true) {
                        System.out.print("YES");
                        
                        for(int i = start; i < start + shipSz; i++){
                        	BattleshipFragment.singletonBean.getPlayers()[1].getSquares()[i].setOccupied(true);
                        }

                        allPlaced++;
                        shipSz--;
                    }
                }

                else { //vertical
                    if (start + shipSz * 10 > 100) 
                        canPlace = false;
                    if (canPlace == true) {
                        for (int i = start; i < start + (shipSz * 10); i += 10) {
                            if (BattleshipFragment.singletonBean.getPlayers()[1].getSquares()[i].isOccupied() == true){
                                canPlace = false;
                                break;
                            }
                        }
                    }

                    if (canPlace == true) {
                    	System.out.print("YES");
                        for(int i = start; i < start + (shipSz * 10); i += 10) {
                        	BattleshipFragment.singletonBean.getPlayers()[1].getSquares()[i].setOccupied(true);
                        }

                        allPlaced++;
                        shipSz--;                    
                    }
                }

            }

            if (allPlaced == 4)
                shipSz = 3; //place destroyer last

        } while (allPlaced < 5) ;
    }
}


