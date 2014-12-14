import java.util.*;

public class AI {
	
	boolean color;

	public AI(boolean color){
		this.color = color;
	}

	public String makeMove(Board b, boolean color){
		Piece p = b.findAllLegalMoves(color).get(0);
		return "M"+String.valueOf(p.x)+String.valueOf(p.y);
	}
	public String makeMove(Board b){
		return makeMove(b, this.color);
	}

    /*public static void main(String[] args) {
		AI r = new AI(true);
		Board b = new Board("B0000000000000000000000000002100000012000000000000000000000000000");
		
		for(Piece p : r.findAllLegalMoves(b, false))
			System.out.println(p.x+" "+p.y+" "+p.color);
		

	}*/

}