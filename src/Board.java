import java.util.ArrayList;


public class Board implements Cloneable{
	short[] boardState;
	public Board(String boardState){
		this.boardState = new short[64];
		for(int i = 0; i < 64; i++){
			this.boardState[i] = Short.decode(boardState.substring(i+1, i+2));
		}
	}
	
	public Board(short[] boardState){
		this.boardState = new short[64];
		for(int i = 0; i < 64; i++){
			this.boardState[i] = boardState[i];
		}
	}
	
	public void updateBoard(String boardState) {
		if(boardState.substring(0,1) != "B"){
			System.out.println("Incorrect board format: "+boardState);
			return;
		}
		
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
		return !(findAllLegalMoves(true).isEmpty() && findAllLegalMoves(false).isEmpty());
	}

	public int getPosition(int x, int y) {
		if(x >= 0 && x < 8 && y >= 0 && y <8 ){
			return boardState[8*x + y];
		}
		return -1;
	}
	
	public boolean getColor(int x, int y) {
		if(x >= 0 && x < 8 && y >= 0 && y <8 ){
			if(boardState[8*x + y] == 1) return true;
		}
		return false;
	}
	
	public boolean makeMove(String input, boolean color) {
		System.out.println(input);
		return makeMove(Integer.valueOf(input.substring(1,2)),Integer.valueOf(input.substring(2,3)),color);
	}
	
	public boolean makeMove(int x, int y, boolean color){
		if(!isLegalMove(x, y, color)) return false;
		System.out.println("("+x+","+y+") Is Legal Move");				
		
		short c = 2;
		if(color) c = 1;
		
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(getPosition(x+i,y+j) > 0 && getPosition(x+i,y+j) != c && !(i == 0 && j == 0)){
					if(makeMoveHelper(x+i,y+j,i,j,c))boardState[8*x + y] = c;
				}
			}
		}
		
		return true;
	}
	
	private boolean makeMoveHelper(int x, int y, int i, int j,
			short color) {
		boolean b;
		if(getPosition(x+i, y+j) < 1) return false;
		if(getPosition(x+i, y+j) == color){
			boardState[8*x + y] = color;
			return true;
		}
		b = makeMoveHelper(x+i,y+j,i,j,color);
		if(b) boardState[8*x + y] = color;
		return b;
	}

	public boolean hasMove(boolean color) {
		return !findAllLegalMoves(color).isEmpty();
	}

	/*
	public Board makeMoveInNewBoard(int x, int y, boolean color){
		Board rtn = null;
		try {
			rtn = (Board) this.clone();
			rtn.makeMove(x, y, color);
			System.out.println("Making move: "+x+","+y+" in new board");
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rtn;
	}*/
	
	public Board makeMoveInNewBoard(int x, int y, boolean color){
		Board returnMe = new Board(this.boardState);
		returnMe.makeMove(x,y,color);
		return returnMe;
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
		if(getPosition(x+i, y+j) < 1)return false;
		if(getColor(x+i, y+j) == color)return true;
		else return isLegalMoveHelper(x+i,y+j,i,j,color);
	}

	public Piece winner() {
		Piece rtn = new Piece(0,0,true);
		for(Piece p : getPieces()){
			if(p.color) rtn.x++;
			else rtn.y++;
		}
		return rtn;
	}

}
