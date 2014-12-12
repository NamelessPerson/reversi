import java.io.*;

import javax.swing.*;

public class Reversi {
	
	public static Board board;
	JPanel canvas;
	
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
		//String i = JOptionPane.showInputDialog(canvas, "Online or local?", JOptionPane.PLAIN_MESSAGE);
		String[] options = {"Yes","No"};
		String[] options2 = {"Human","AI"};
		while(true){
			int i = JOptionPane.showOptionDialog(canvas, "Would you like to play online?", "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "");
			System.out.println(i);
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
		while(board.isPlayable()){
			
		}
		System.exit(0);
	}

	private void localGameHuman() {
		System.exit(0);
	}

	public static void main(String[] args) throws InterruptedException {
		Reversi r = new Reversi();
		r.run();
	}

}