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
		
		/*Just straight up count how many legal moves there are*/
		mobility = (b.findAllLegalMoves(color)).size();
		
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
	public int extendMoves(Piece p, Board b, boolean color, boolean origColor, int depth, int minHappiness){
		//Board newBoard = b.makeMoveInNewBoard(p.x, p.y, color);
		
		return 0;
	}
	
	/* This function returns the move that you ought to make
	 * It does not do the recursive step in computing the move. 
	 *
	 **/
	public Piece selectMove(ArrayList<Piece> availableMoves, Board b, boolean color){
		int[] boardScores = new int[availableMoves.size()];

		for(int i = 0; i< availableMoves.size(); i ++){
			boardScores[i] = extendMoves(availableMoves.get(i), b, color, !color, 5, 5);
		}
		
		return availableMoves.get(0);
	}

	public String makeMove(Board b, boolean color){
<<<<<<< HEAD
		Piece p = b.findAllLegalMoves(color).get(0);
=======
		/*Find all legal moves*/
		ArrayList<Piece> moves = b.findAllLegalMoves(color);
		
		Piece p = selectMove(moves, b, color);
		
>>>>>>> f8f6e4bb2910da5c868a6dc77f5e70c48b64ab2d
		return "M"+String.valueOf(p.x)+String.valueOf(p.y);
	}
	public String makeMove(Board b){
		return makeMove(b, this.color);
	}

   /* public static void main(String[] args) {
		AI r = new AI(true);
		Board b = new Board("B0000000000000000000000000002100000012000000000000000000000000000");
		
		//for(Piece p : r.findAllLegalMoves(b, false))
		//	System.out.println(p.x+" "+p.y+" "+p.color);
		System.out.println(r.evaluateBoard(b, true));
		

	}*/

}