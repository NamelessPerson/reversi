import java.util.ArrayList;


public class Board {
	short[] boardState;
	public Board(String boardState){
		this.boardState = new short[64];
		for(int i = 1; i < 64; i++)
			this.boardState[i] = Short.decode(boardState.substring(i, i));
	}
	
	public ArrayList<Piece> getPieces(){
		ArrayList<Piece> rtn = new ArrayList<Piece>();
		rtn.add(new Piece(3,3,true));
		rtn.add(new Piece(3,4,false));
		rtn.add(new Piece(4,3, false));
		rtn.add(new Piece(4,4, true));
		return rtn;
	}
	
	//True is white, False is black
	public class Piece{
		int x,y;
		boolean color;
		public Piece(int x, int y, boolean color){
			this.x = x;
			this.y = y;
			this.color = color;
		}
	}
}
