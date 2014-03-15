//package com.hasbrobeta.battleship;
//
//import java.util.Random;
//
////unsure of importance for following libraries
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//public class ai extends BaseAdapter{
//
//
//    public class Square {
//        boolean isOccupied;
//        boolean direction; 
//        int size;       
//        public Square() {isOccupied = false; direction = NULL, size = 0} //is NULL ok or is this Boolean vs boolean?
//    }
//
//    public class Board {
//        public int numShipsPlaced = 0;
//        public Square[] squares;
//
//        public Board() {
//            this.squares = new Square[100]; 
//            for (int i = 0; i < 100; i++) 
//                this.squares[i] = new Square();
//        }
//    }
//
//    public class AIPlacement {
//    
//        Random rand = new Random(); //necessary everytime?
//        int allPlaced  = 0;
//        int shipSz  = 5;
//
//        do {
//            int start = rand.nextInt(100);
//            
//            if (square[i].isOccupied == false) {
//
//                boolean direction = rand.nextBoolean();
//                boolean canPlace = true;
//
//                if (direction == true) { //horizontal
//                    if (start + shipSz % 10 != start + shipSz) 
//                        canPlace = false;     
//                    if (canPlace){
//                        for (int i = start; i < start + shipSz; i++) {
//                            if (square[i].isOccupied == true) {
//                                canPlace = false;
//                                break;  
//                            }
//                        }
//                    }
//
//                    if (canPlace == true) {
//                        
//                        for(int i = start; i < start + shipSz; i++){
//                            square[i].isOccupied = true;
//                            square[i].direction = true;
//                            square[i].size = shipSz;
//                        }
//
//                        allPlaced++;
//                        shipSz--;
//                    }
//                }
//
//                else { //veritcal
//                    if (start + shipSz * 10 > 100) 
//                        canPlace = false;
//                    if (canPlace == true) {
//                        for (int i = start; i < start + (shipSz * 10); i += 10) {
//                            if (square[i].isOccupied == true){
//                                canPlace = false;
//                                break;
//                            }
//                        }
//                    }
//
//                    if (canPlace == true) {
//
//                        for(int i = start; i < start + (shipSz * 10); i += 10) {
//                            square[i].isOccupied = true;
//                            square[i].direction = false;
//                            square[i].size = shipSz;
//                        }
//
//                        allPlaced++;
//                        shipSz--;                    
//                    }
//                }
//
//            }
//
//            if (allPlaced == 4)
//                shipSz = 3; //place destroyer last
//
//        } while (allPlaced < 5) //TO DO: TEST with graphics, interpreter for the 2 person data structure? 
//        //unsure if current data structure is enough for visuals
//    }
//}
//
//
