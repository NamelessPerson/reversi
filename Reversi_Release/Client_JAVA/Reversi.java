import java.io.*;
import javax.swing.*;

public class Reversi {
	
	/* 0 = unplaced
	 * 1 = white
	 * 2 = black
	 */
	int[] Board; 
	Reversi(){
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		Board = new int[64];
		
		for(int i = 0; i < 64; i ++)
			Board[i] = 0;
		/*Initially white,black, black white*/
		Board[rcToOneD(3,3)] = 1; //Assign whites
		Board[rcToOneD(4,4)] = 1;
		Board[rcToOneD(3,4)] = 2; //Assign blacks
		Board[rcToOneD(4,3)] = 2;
	}
	
	void run(){
		while(true){
			
		}
	}
	
	/*This function takes in a row and a column
	 * and returns the integer index into the 1D array
	 * that refers to.
	 */
	
	public int rcToOneD(int r, int c){
		return 8*r + c;
	}
	
	/* This function takes in an integer between 0 an 63 (inclusive)
	 * and returns an array of two integers that correspond to the row and column numbers
	 * 
	 */
	public int[] oneDtoRC(int i){
		int[] returnMe = new int[2];
		returnMe[0] = i / 8;
		returnMe[1] = i % 8;
		return returnMe;
	}
	
	/*Not sure where we want this function, 
	 * writing it here, we can move it later.
	 */
	/* Takes in a move as a location and color and checks whether it is legal on the current board.
	 * 
	 */
	public boolean isLegalMove(int color, int[] move){
		boolean returnMe = false;
		
		/*Check if occupied*/
		if(Board[rcToOneD(move[0],move[1])] != 0)
			return false;
		
		/*0 = north, 1 = NE, 2 = E, 3 = SE, 4 = S, 5 = SW, 6 = W, 7 = NW*/
		for (int i = 0; i < 8; i ++){
			int[] nextPos = getNextPos(move, i);
	
			/*Skip this direction if we're about to go off the board*/
			if(isOffBoard(nextPos))
				continue;
			
			/*Skip this direction if we don't detect an opposite color piece*/
			int nextColor = Board[rcToOneD(nextPos[0],nextPos[1])];
			if(nextColor == 0 || nextColor == color)
				continue;
			
			returnMe = returnMe | isLegalMoveHelper(color, move, i);
			if (returnMe = true)
					return returnMe;
		}
		
		return returnMe;
	}
	
	/* This function recursively walks in a particular direction to see if there is a legal move in that
	 * direction. It searches for a sandwich with the opposite color.
	 */
	public boolean isLegalMoveHelper(int color, int[] pos, int dir){
		int[] nextPos = new int[2];
		int nextColor;
		
		nextPos = getNextPos(pos,dir);
		
		/*Check off the board*/
		if(isOffBoard(nextPos)){	
			return false; //If we ever get to the edge of the board, this direction was a bust.
		}

		nextColor = Board[rcToOneD(nextPos[0],nextPos[1])];
		
		if(nextColor == color)
			return true;
		else if(nextColor == 0)
			return false;
		else
			return(isLegalMoveHelper(color,nextPos,dir));
	}
	
	/*Flood fill implement move method*/
	/*Check isLegalMove BEFORE calling this function*/
	public void makeMove(int[] move, int color){
		Board[rcToOneD(move[0],move[1])] = color;
		
		/*Find every direction we should walk in*/
		/*0 = north, 1 = NE, 2 = E, 3 = SE, 4 = S, 5 = SW, 6 = W, 7 = NW*/
		for (int i = 0; i < 8; i ++){
			int[] nextPos = getNextPos(move, i);
	
			/*Skip this direction if we're about to go off the board*/
			if(isOffBoard(nextPos))
				continue;
			
			/*Skip this direction if we don't detect an opposite color piece*/
			int nextColor = Board[rcToOneD(nextPos[0],nextPos[1])];
			if(nextColor == 0 || nextColor == color)
				continue;
			
			/*If we should flood fill, do it. (Turns out isLegalMoveHelper is already checking this.)*/
			if(isLegalMoveHelper(color, nextPos, i)){
				makeMoveHelper(color, nextPos, i);
			
			}
		}
	}
	
	
	/*This function does the actual flood-filling*/
	public void makeMoveHelper(int color, int[] pos, int dir){
		int[] nextPos = new int[2];
		nextPos = getNextPos(pos,dir);
		
		Board[rcToOneD(pos[0],pos[1])] = color;
		if(Board[rcToOneD(nextPos[0],nextPos[1])] != color)
			makeMoveHelper(color, nextPos, dir);
	}

	public int[] getNextPos(int[] pos, int dir){
		int[] nextPos = new int[2];
		/*Handle norths*/
		if(dir == 0 || dir == 7 || dir == 1){
			nextPos[0] = pos[0]-1;
		}
		/*Handle easts*/
		if(dir == 1 || dir == 2 || dir == 3){
			nextPos[1] = pos[1]+1;
		}
		
		/* Handle souths*/
		if(dir == 3 || dir == 4 || dir == 5){
			nextPos[0] = pos[0]+1;
		}
		
		/*Handle wests*/
		if(dir == 5 || dir == 6 || dir == 7){
			nextPos[1] = pos[1]-1;
		}
		
		return nextPos;
	}

	public boolean isOffBoard(int[] pos)
	{
		return (pos[0] < 0 || pos[0] > 7 || pos[1] < 0 || pos[1] > 7);
	}
	
	public static void main(String[] args) {
		Reversi r = new Reversi();
	}

}
