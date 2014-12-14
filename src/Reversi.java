import java.io.*;

import javax.swing.*;

public class Reversi {
	
	public static Board board;
	public static int inputX;
	public static int inputY;
	BoardCanvas canvas;
	
	Reversi(){
		board = new Board("B0000000000000000000000000002100000012000000000000000000000000000");
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(600, 600);
		window.setLocationRelativeTo(null);
		
		canvas = new BoardCanvas();
		canvas.setSize(20, 20);
		canvas.setVisible(true);
		
		window.add(canvas);
		window.setVisible(true);
	}
	
	void run() throws InterruptedException{
		String[] options = {"Online","Local"};
		String[] options2 = {"Human","AI"};
		while(true){
			int i = JOptionPane.showOptionDialog(canvas, "Would you like to play online or locally?", "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "");
			if(i == 0){
				
			}
			else if(i == 1){
				i = JOptionPane.showOptionDialog(canvas, "Would you like to play against a Human or the AI?", "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, options2[0]);
				if(i == 0) localGameHuman();
				if(i == 1) localGameAI();
			}
			
			Thread.sleep(1);
		}
	}
	
	private void localGameAI() {
		AI ai = new AI(false);
		board = new Board("B0000000000000000000000000002100000012000000000000000000000000000");
		while(board.isPlayable()){
			if(board.hasMove(true)){
				while(!board.makeMove(getInput(), true));
			}
			if(board.hasMove(false)) board.makeMove(ai.makeMove(board), false);
			canvas.repaint();
		}
		System.exit(0);
	}

	private String getInput() {
		canvas.getInput();
		while(inputX < 0 && inputY < 0){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "M"+String.valueOf(inputX)+String.valueOf(inputY);
	}

	private void localGameHuman() {
		System.exit(0);
	}

	public static void main(String[] args) throws InterruptedException {
		Reversi r = new Reversi();
		r.run();
	}

}