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
			if(boardState[i] == 1) rtn.add(new Piece(i%8, i/8, true));
			if(boardState[i] == 2) rtn.add(new Piece(i%8, i/8, false));
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
}
