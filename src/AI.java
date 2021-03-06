import java.util.*;

public class AI {
	
	boolean color;

	public AI(boolean color){
		this.color = color;
	}
	
	/*This function takes in a board and a color and returns a number corresponding to the heuristic
	 * value for that board.
	 * If the board is a winning board for you it returns TMax.
	 */
	public int evaluateBoard(Board b, boolean color){
		int mobility = 0;
		int tileValues = 0;
		int nPieces = 0;
		int totalPieces = 0;
		
		
		/* 1 = white, 2 = black. true = white, false = black*/
		for(int i = 0; i < 8; i ++){
			for(int j = 0; j < 8; j ++){
				int thisPiece = b.getPosition(i,j);
				
				if((color == true && thisPiece == 1) || (color == false && thisPiece == 2)){
					/*This is a piece of my color. Count it.*/
					nPieces++;
					tileValues += tileValue(b,i,j);
				}
				
				if(thisPiece != 0)
					totalPieces++;
			}
		}
		
		/*Check win conditions, losing board means -1*/
		
		/*Just straight up count how many legal moves there are*/
		mobility = (b.findAllLegalMoves(color)).size();
		
		/* Zero legal moves, is this win or lose?*/
		if(mobility == 0){
			if(b.findAllLegalMoves(!color).size() >0)
				return -1; //This is a losing board.
			else{
				//Check who won.
				if(2*nPieces > totalPieces) // More of ours than theirs
					return 1000000;
				else
					return -1;
			}
				
		}
		
		return mobility + tileValues + 20*(nPieces/totalPieces);
	}
	
	/*Returns the value of a given tile on a given board.*/
	public int tileValue(Board b, int x, int y){
		/*Region 1*/
		if(x>= 2 && x<= 5 && y>=2 && y<=5)
			return 1;
		
		/*Region 2*/
		if( (((y>1 && y<6) && (x==1 || x== 6)) ||
			((x>1 && x<6) && (y==1 || y==6)) ))
			return -10;
		
		/*Region 3*/
		if( ((y>1 && y < 6) && (x==0 || x == 7)) || 
				((x>1 && x < 6) && (y==0 || y == 7)))
				return 10;
		
		/*Region 4*/
		if(x+y == 1 ||
		  ((x ==y) && (x==1 || x==6))||
		  ((x == 0) && ( y == 1 || y == 6))||
		  ((x == 1) && ( y == 0 || y == 7))||
		  ((x == 6) && (y == 0 || y == 7))||
		  ((x == 7) && (y == 1 || y == 6))||
		  ((x == 6) && (y==1)) || ((x==1) && (y==6))
		 ){
			int cornerX, cornerY; //Find nearest corner.
			
			if(x+y<= 2){
				cornerX = 0;
				cornerY = 0;
			}
			else if(x+y >= 12){
				cornerX = 7;
				cornerY =7;
			}
			else if(x<=1 && y>= 6){
				cornerX = 0;
				cornerY = 7;
			}
			else{
				cornerX = 7;
				cornerY = 0;
			}
			
			/*Favor region 4 if the corner is taken*/
				if(b.getPosition(cornerX, cornerY) == 0)
					return -50;
				else
					return 100;
		}
		/*Region 5*/
		if( ((x==y) && (x==0 || x ==7))
				|| (x==0 && y==7) ||(x==7 && y==0))
			return 100;
		
		return 0;
	}
	
	/*This recursively extends a move until it hits the search depth or minimum happiness*/
	public int extendMoves(Piece p, Board b, boolean color, boolean origColor, long startTime, int minHappiness){
		
		b.makeMove("M"+String.valueOf(p.x)+String.valueOf(p.y), color);
		
		/*Base case, if this is the bottom, stop*/
		int currentHappiness = evaluateBoard(b, origColor);
		long timeNow = System.currentTimeMillis();
		
		if(timeNow - startTime > 8000 || currentHappiness < minHappiness)
			return currentHappiness;
		
		ArrayList<Piece> newBoardMoves = b.findAllLegalMoves(!color); //Find every possible next
		//move that the opponent might make.
		
		/*If there are no legal moves, return -1*/
		if(newBoardMoves.size() == 0)
			return -1;
		
		int[] newBoardScores = new int[newBoardMoves.size()];
		
		int max = 0;
		int min = 0;
		
		/*For each possible next move, recursively call this function.*/
		for(int i = 0; i < newBoardMoves.size(); i ++){
			Board newBoard = new Board(b.boardState);
			newBoardScores[i] = extendMoves(newBoardMoves.get(i), newBoard, !color, origColor, startTime, minHappiness);
			if(color == origColor)
				if(max < newBoardScores[i])
					max = newBoardScores[i];
		}
	
		
		
		if(color != origColor){
			min = newBoardScores[0];
			for(int i = 1; i < newBoardMoves.size(); i ++)
				if( min > newBoardScores[i])
					min = newBoardScores[i];
		}
			
		if(color == origColor)
			return max;
		else
			return min;
	}
	
	/* This function returns the move that you ought to make
	 * It does not do the recursive step in computing the move. 
	 *
	 **/
	public Piece selectMove(ArrayList<Piece> availableMoves, Board b, boolean color){
		int[] boardScores = new int[availableMoves.size()];

		int max = 0;
		int indexOfMax = 0;
		
		for(int i = 0; i< availableMoves.size(); i ++){
			Board newBoard = new Board(b.boardState);
			boardScores[i] = extendMoves(availableMoves.get(i), newBoard, !color, color, 5, 10);
			if(boardScores[i] > max){
				max = boardScores[i];
				indexOfMax = i;
			}
		}
				
		return availableMoves.get(indexOfMax);
	}

	public String makeMove(Board b, boolean color){
		Piece p = b.findAllLegalMoves(color).get(0);
		/*Find all legal moves*/
		ArrayList<Piece> moves = b.findAllLegalMoves(color);
		
		p = selectMove(moves, b, color);
		
		return "M"+String.valueOf(p.x)+String.valueOf(p.y);
	}
	public String makeMove(Board b){
		return makeMove(b, this.color);
	}

}