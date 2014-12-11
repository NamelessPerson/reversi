import java.util.ArrayList;


public class AI {

	/* 0 = unplaced
	 * 1 = white
	 * 2 = black
	 */
	int[] Board;
	
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
	
	public ArrayList<int[]> findAllLegalMoves(int color, int[] B){
		System.out.println("Welcome to find all legal moves!");
		ArrayList<int[]> allMovableSpaces = findAllMovableSpaces(B);
		/*Find all elements that need removed because they are a movable space but not
		 * a legal move for this color.
		 */
		ArrayList<int[]> returnMe = new ArrayList<int[]>();
		
		for(int i = 0; i < allMovableSpaces.size(); i++){
			if(isLegalMove(color, allMovableSpaces.get(i),B)){
				returnMe.add(allMovableSpaces.get(i));
			}
		}
		
		return returnMe;
	}
	
	/*Flood fills from position (3,3) to find all spaces where a legal move could even happen*/
	public ArrayList<int[]> findAllMovableSpaces(int[] B){
		int[] initPos = {3,3};
		
		ArrayList<int[]> returnMe = new ArrayList<int[]>();
		/*
		ArrayList<int[]> checkedList = new ArrayList<int[]>();
		returnMe.addAll(findAllMovableSpacesHelper(initPos, B, checkedList));

		return returnMe;*/
		int[] pos = {0,0};
		
		for(int i = 0; i < 8; i ++)
			for( int j = 0; j < 8; j++){
				pos[0] = i;
				pos[1] = j;
				
				/*Check all neighboring spaces*/
				for(int k = 0; k < 8; k++){
					int[] nextPos = new int[2];
					nextPos = getNextPos(pos, k);
					if(!isOffBoard(nextPos)){
						if(B[rcToOneD(nextPos[0],nextPos[1])] != 0){
							returnMe.add(nextPos);
							continue;
						}
					}
				}
			}
		return returnMe;
		
		
	}
	
	/*Helper function for findAllMovableSpaces(). Does the actual flood filling but only in one direction
	 * Always returns a list of one element or a list of zero
	 * */
	public ArrayList<int[]> findAllMovableSpacesHelper(int[] pos, int[] B, ArrayList<int[]> checkedList){
		ArrayList<int[]> returnMe = new ArrayList<int[]>();

		/*Check if this pos has already been checked*/
		for(int i = 0; i < checkedList.size(); i ++)
		{
			if(pos[0] == checkedList.get(i)[0] && pos[1] == checkedList.get(i)[1])
				return returnMe;
		}
		
		checkedList.add(pos);
		
		for(int i = 0; i < 8; i ++){
			int[] nextPos = getNextPos(pos, i);
		 
		
			if(isOffBoard(nextPos))
				continue;
			else if(B[rcToOneD(nextPos[0],nextPos[1])] == 0){
				returnMe.add(nextPos);
				System.out.println("Adding: ");
				printMove(nextPos);
			}
			else
				returnMe.addAll(findAllMovableSpacesHelper(nextPos, B, checkedList));
		}
		return returnMe;
	}
	
	
	/*Not sure where we want this function, 
	 * writing it here, we can move it later.
	 */
	/* Takes in a move as a location and color and checks whether it is legal on the current board.
	 * 
	 */
	public boolean isLegalMove(int color, int[] move, int[] B){
		boolean returnMe = false;
		
		/*Check if occupied*/
		if(B[rcToOneD(move[0],move[1])] != 0)
			return false;
		
		/*0 = north, 1 = NE, 2 = E, 3 = SE, 4 = S, 5 = SW, 6 = W, 7 = NW*/
		for (int i = 0; i < 8; i ++){
			int[] nextPos = getNextPos(move, i);
	
			/*Skip this direction if we're about to go off the board*/
			if(isOffBoard(nextPos))
				continue;
			
			/*Skip this direction if we don't detect an opposite color piece*/
			int nextColor = B[rcToOneD(nextPos[0],nextPos[1])];
			if(nextColor == 0 || nextColor == color)
				continue;
			
			returnMe = returnMe | isLegalMoveHelper(color, move, i, B);
			if (returnMe = true)
					return returnMe;
		}
		
		return returnMe;
	}
	
	/* This function recursively walks in a particular direction to see if there is a legal move in that
	 * direction. It searches for a sandwich with the opposite color.
	 */
	public boolean isLegalMoveHelper(int color, int[] pos, int dir, int [] B){
		int[] nextPos = new int[2];
		int nextColor;
		
		nextPos = getNextPos(pos,dir);
		
		/*Check off the board*/
		if(isOffBoard(nextPos)){	
			return false; //If we ever get to the edge of the board, this direction was a bust.
		}

		nextColor = B[rcToOneD(nextPos[0],nextPos[1])];
		
		if(nextColor == color)
			return true;
		else if(nextColor == 0)
			return false;
		else
			return(isLegalMoveHelper(color,nextPos,dir, B));
	}
	
	/*Flood fill implement move method*/
	/*Check isLegalMove BEFORE calling this function*/
	public void makeMove(int[] move, int color, int[] B){
		B[rcToOneD(move[0],move[1])] = color;
		
		/*Find every direction we should walk in*/
		/*0 = north, 1 = NE, 2 = E, 3 = SE, 4 = S, 5 = SW, 6 = W, 7 = NW*/
		for (int i = 0; i < 8; i ++){
			int[] nextPos = getNextPos(move, i);
	
			/*Skip this direction if we're about to go off the board*/
			if(isOffBoard(nextPos))
				continue;
			
			/*Skip this direction if we don't detect an opposite color piece*/
			int nextColor = B[rcToOneD(nextPos[0],nextPos[1])];
			if(nextColor == 0 || nextColor == color)
				continue;
			
			/*If we should flood fill, do it. (Turns out isLegalMoveHelper is already checking this.)*/
			if(isLegalMoveHelper(color, nextPos, i, B)){
				makeMoveHelper(color, nextPos, i, B);
			
			}
		}
	}
	
	
	/*This function does the actual flood-filling*/
	public void makeMoveHelper(int color, int[] pos, int dir, int[] B){
		int[] nextPos = new int[2];
		nextPos = getNextPos(pos,dir);
		
		B[rcToOneD(pos[0],pos[1])] = color;
		if(B[rcToOneD(nextPos[0],nextPos[1])] != color)
			makeMoveHelper(color, nextPos, dir, B);
	}

	public int[] getNextPos(int[] pos, int dir){
		int[] nextPos = new int[2];
		nextPos[0] = pos[0];
		nextPos[1] = pos[1];
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

	/*Does exactly what it says on the tin*/
	public boolean isOffBoard(int[] pos){
		return (pos[0] < 0 || pos[0] > 7 || pos[1] < 0 || pos[1] > 7);
	}
	
	/*Does exactly what it says on the tin*/
	public void printBoard(int[] B){
		for(int i = 0; i < 8; i ++){
			for(int j = 0; j < 8; j ++){
				System.out.print(B[rcToOneD(i,j)]+" ");
			}
			System.out.println();
		}
	}
	
	public void printMove(int[] m){
		System.out.println("("+m[0]+","+m[1]+")");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
