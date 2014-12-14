import java.util.ArrayList;


public class Board {
	short[] boardState;
	public Board(String boardState){
		this.boardState = new short[64];
		for(int i = 0; i < 64; i++){
			this.boardState[i] = Short.decode(boardState.substring(i+1, i+2));
		}
	}
	
	public ArrayList<Piece> getPieces(){
		ArrayList<Piece> rtn = new ArrayList<Piece>();
		for(int i = 0; i<64; i++){
			if(boardState[i] == 1) rtn.add(new Piece(i/8, i%8, true));
			if(boardState[i] == 2) rtn.add(new Piece(i/8, i%8, false));
		}
		return rtn;
	}

	public boolean isPlayable() {
		// TODO Auto-generated method stub
		return true;
	}

	public int getPosition(int x, int y) {
		if(x >= 0 && x < 8 && y >= 0 && y <8 ){
			return boardState[8*x + y];
		}
		return -1;
	}
	
	public boolean getColor(int x, int y) {
		if(x > 0 && x < 8 && y > 0 && y <8 ){
			if(boardState[8*x + y] == 1) return true;
		}
		return false;
	}
	public boolean makeMove(int x, int y, boolean color){
		if(isLegalMove(x, y, color)) return false;
		
		int c = 2;
		if(color) c = 1;
		
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(getPosition(x+i,y+j) > 0){
					if(getColor(x+i, y+j) != color){
						if(!(i == 0 && j == 0) && isLegalMoveHelper(x+i,y+j,i,j,color))return true;
					}
				}
			}
		}
		
		return true;
	}

	public boolean hasMove(boolean color) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean makeMove(String input, boolean color) {
		System.out.println(input);
		return makeMove(Integer.valueOf(input.substring(1,2)),Integer.valueOf(input.substring(2,3)),color);
	}
	
	public Board makeMoveInNewBoard(int x, int y, boolean color){
		Board rtn = null;
		try {
			rtn = (Board) this.clone();
			rtn.makeMove(x, y, color);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rtn;
	}
	
	public ArrayList<Piece> findAllLegalMoves(boolean color){
		ArrayList<Piece> rtn = new ArrayList<Piece>();
		for(int x = 0; x < 8; x++)
			for(int y = 0; y <8; y++)
				if(isLegalMove(x, y, color))rtn.add(new Piece(x, y, color));
		return rtn;
	}
	
	public boolean isLegalMove(int x, int y, boolean color){
		if(getPosition(x,y) != 0 ) return false;
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(getPosition(x+i,y+j) > 0){
					if(getColor(x+i, y+j) != color){
						if(!(i == 0 && j == 0) && isLegalMoveHelper(x+i,y+j,i,j,color))return true;
					}
				}
			}
		}
		return false;
	}
	
	
	private boolean isLegalMoveHelper(int x, int y, int i, int j,
			boolean color) {
		if(getPosition(x+i, y+j) < 1) return false;
		if(getColor(x+i, y+j) == color) return true;
		else return isLegalMoveHelper(x+i,y+j,i,j,color);
	}
}
