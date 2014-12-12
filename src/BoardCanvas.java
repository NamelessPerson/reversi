import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BoardCanvas extends JPanel{
	public void paintComponent(Graphics g){
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		
		int i = this.getWidth()/8;
		
		g.drawLine(i, 	0, i, 	600);
		g.drawLine(i*2, 0, i*2, 600);
		g.drawLine(i*3, 0, i*3, 600);
		g.drawLine(i*4, 0, i*4, 600);
		g.drawLine(i*5, 0, i*5, 600);
		g.drawLine(i*6, 0, i*6, 600);
		g.drawLine(i*7, 0, i*7, 600);
		
		i = this.getHeight()/8;
		
		g.drawLine(0, i,   600, i);
		g.drawLine(0, i*2, 600, i*2);
		g.drawLine(0, i*3, 600, i*3);
		g.drawLine(0, i*4, 600, i*4);
		g.drawLine(0, i*5, 600, i*5);
		g.drawLine(0, i*6, 600, i*6);
		g.drawLine(0, i*7, 600, i*7);
		
		paintPieces(g, Reversi.board);
	}

	private void paintPieces(Graphics g, Board board) {
		ArrayList<Piece> list = board.getPieces();
		for(Piece p : list){
			
			if(p.color) g.setColor(Color.WHITE);
			else g.setColor(Color.BLACK);
			
			int i = this.getWidth()/8;
			int j = this.getHeight()/8;
			
			g.fillOval((p.x*i)+3, (p.y*j)+3, i-6, j-6);
		}
		
	}
}
