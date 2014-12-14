import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BoardCanvas extends JPanel{
	private boolean input;
	
	public BoardCanvas(){
		this.addMouseListener(new CanvasInputListener(this));
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		
		int i = this.getWidth()/8;
		
		g.drawLine(i, 	0, i, 	this.getHeight());
		g.drawLine(i*2, 0, i*2, this.getHeight());
		g.drawLine(i*3, 0, i*3, this.getHeight());
		g.drawLine(i*4, 0, i*4, this.getHeight());
		g.drawLine(i*5, 0, i*5, this.getHeight());
		g.drawLine(i*6, 0, i*6, this.getHeight());
		g.drawLine(i*7, 0, i*7, this.getHeight());
		
		i = this.getHeight()/8;
		
		g.drawLine(0, i,   this.getWidth(), i);
		g.drawLine(0, i*2, this.getWidth(), i*2);
		g.drawLine(0, i*3, this.getWidth(), i*3);
		g.drawLine(0, i*4, this.getWidth(), i*4);
		g.drawLine(0, i*5, this.getWidth(), i*5);
		g.drawLine(0, i*6, this.getWidth(), i*6);
		g.drawLine(0, i*7, this.getWidth(), i*7);
		
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
	public void getInput(){
		input = true;
		Reversi.inputX = -1;
		Reversi.inputY = -1;
		
	}
	private class CanvasInputListener implements MouseListener{
		//Java has pointers WHAAT??? Yeah.
		BoardCanvas ptr;
		
		CanvasInputListener(BoardCanvas ptr){
			this.ptr = ptr;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(input){
				input = false;
				Reversi.inputX = e.getX() / (ptr.getWidth()/8);
				Reversi.inputY = e.getY() / (ptr.getHeight()/8);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
