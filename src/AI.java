import java.util.*;

public class AI {
	
	boolean color;

	public AI(boolean color){
		this.color = color;
	}
	
	/*Flood fills from position (3,3) to find all spaces where a legal move could even happen*/
	public static ArrayList<Piece> findAllLegalMoves(Board b, boolean color){
		ArrayList<Piece> rtn = new ArrayList<Piece>();
		for(int x = 0; x < 8; x++)
			for(int y = 0; y <8; y++)
				if(isLegalMove(x, y, color, b))rtn.add(new Piece(x, y, color));
		return rtn;
	}
	
	/*Not sure where we want this function, 
	 * writing it here, we can move it later.
	 */
	/* Takes in a move as a location and color and checks whether it is legal on the current board.
	 * 
	 */
	public static boolean isLegalMove(int x, int y, boolean color, Board b){
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(b.getPosition(x+i,y+j) > 0){
					if(b.getColor(x+i, y+j) != color){
						if(!(i == 0 && j == 0) && isLegalMoveHelper(x+i,y+j,i,j,color,b))return true;
					}
				}
			}
		}
		return false;
	}
	
	
	private static boolean isLegalMoveHelper(int x, int y, int i, int j,
			boolean color, Board b) {
		if(b.getPosition(x+i, y+j) < 1) return false;
		if(b.getColor(x+i, y+j) == color) return true;
		else return isLegalMoveHelper(x+i,y+j,i,j,color,b);
	}

	public String makeMove(Board b, boolean color){
		Piece p = findAllLegalMoves(b, color).get(0);
		return "M"+String.valueOf(p.x)+String.valueOf(p.y);
	}
	public String makeMove(Board b){
		return makeMove(b, this.color);
	}

    public static void main(String[] args) {
		AI r = new AI(true);
		Board b = new Board("B0000000000000000000000000002100000012000000000000000000000000000");
		
		for(Piece p : r.findAllLegalMoves(b, false))
			System.out.println(p.x+" "+p.y+" "+p.color);
		

	}

}