import java.io.*;
import java.net.Socket;

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
		String[] options1 = {"Human","AI"};
		String[] options2 = {"Human","AI", "Simulated"};
		String[] options3 = {"Yes","no"};
		while(true){
			int i = JOptionPane.showOptionDialog(canvas, "Would you like to play online or locally?", "Setup", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "");
			if(i == 0){
				i = JOptionPane.showOptionDialog(canvas, "Would you like to play as a Human or the AI?", "Setup", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, options2[0]);
				if(i == 0) serverGameHuman();
				if(i == 1) serverGameAI();
			}
			else if(i == 1){
				i = JOptionPane.showOptionDialog(canvas, "Would you like to play against a Human or the AI? Or run a Simulation?", "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, options2[0]);
				if(i == 0) localGameHuman();
				if(i == 1) localGameAI();
				if(i == 2) localGameAIAI();
			
				Piece p = board.winner();
				String winner;
				if(p.x > p.y) winner = "White Wins ";
				else if (p.x < p.y) winner = "Black Wins ";
				else winner = "It's a Draw ";
				i = JOptionPane.showOptionDialog(canvas, winner+p.x+" to "+p.y+"\nWould you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options3, options3[1]);
				if(i == 1) System.exit(0);
			}
			else if(i == -1) System.exit(0);
			
			Thread.sleep(1);
		}
	}
	
	private void localGameAI() throws InterruptedException {
		AI ai = new AI(false);
		board = new Board("B0000000000000000000000000002100000012000000000000000000000000000");
		canvas.repaint();
		while(board.isPlayable()){
			if(board.hasMove(true)){
				while(!board.makeMove(getInput(), true));
			}
			canvas.repaint();
			Thread.sleep(1000);
			if(board.hasMove(false)) board.makeMove(ai.makeMove(board), false);
			canvas.repaint();
		}
	}

	private void localGameHuman() throws InterruptedException {
		board = new Board("B0000000000000000000000000002100000012000000000000000000000000000");
		canvas.repaint();
		while(board.isPlayable()){
			if(board.hasMove(true)){
				while(!board.makeMove(getInput(), true));
			}
			canvas.repaint();
			Thread.sleep(1000);
			if(board.hasMove(false)){
				while(!board.makeMove(getInput(), false));
			}
			canvas.repaint();
		}
	}
	
	private void localGameAIAI() throws InterruptedException {
		AI ai1 = new AI(true);
		AI ai2 = new AI(false);
		board = new Board("B0000000000000000000000000002100000012000000000000000000000000000");
		canvas.repaint();
		while(board.isPlayable()){
			if(board.hasMove(true)) board.makeMove(ai1.makeMove(board), true);
			canvas.repaint();
			Thread.sleep(1000);
			if(board.hasMove(false)) board.makeMove(ai2.makeMove(board), false);
			canvas.repaint();
			Thread.sleep(1000);
		}
	}
	
	private void serverGameAI(){
		AI ai = null;
		Socket s;
		BufferedReader in;
		PrintWriter out;
		String msg = "";
		int i=0;
		
		String IP = JOptionPane.showInputDialog(canvas, "Please input the IP", "IP", JOptionPane.PLAIN_MESSAGE);
		int port = Integer.valueOf(JOptionPane.showInputDialog(canvas, "Please input the Port number", "Port", JOptionPane.PLAIN_MESSAGE));
		String team = JOptionPane.showInputDialog(canvas, "Please input your team name", "Team", JOptionPane.PLAIN_MESSAGE);
		System.out.println("Connecting to "+IP+":"+port);
		
		try{
			s = new Socket(IP,port);
			//socket input
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			//socket output
		   	out = new PrintWriter(s.getOutputStream());
		   	out.println("N"+team);
		   	out.flush();
		   	
		   	
		   	do{
		   		msg = in.readLine();
		   		System.out.println(msg);
		   		if(!msg.isEmpty()){
		   			if(msg.equals("U0"))ai = new AI(true);
		   			else if(msg.equals("U1"))ai = new AI(false);
		   			else if(msg.equals("W1"))i = 1;
		   			else if(msg.equals("W0"))i = 0;
		   			else if(msg.equals("W2"))i = 2;
		   			else if(msg.substring(0,1).equals("B")){
		   				board.updateBoard(msg);
			   			canvas.repaint();
			   			out.println(ai.makeMove(board));
			   			out.flush();
		   			}
		   			else if(!msg.equals("G")) System.out.println("Unrecognized message :"+msg);
		   		}
		   	}while(!msg.equals("G"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(i == 1) msg = "Win";
		if(i == 0) msg = "Lose";
		if(i == 2) msg = "Draw";
		
		String[] options = {"Yes","No"};
		i = JOptionPane.showOptionDialog(canvas, "You "+msg+"! Would you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if(i == 1 || i == -1) System.exit(0);
	}
	
	private void serverGameHuman(){
		boolean color = true;
		Socket s;
		BufferedReader in;
		PrintWriter out;
		String msg = "";
		int i=-1;
		
		String IP = JOptionPane.showInputDialog(canvas, "Please input the IP", "IP", JOptionPane.PLAIN_MESSAGE);
		int port = Integer.valueOf(JOptionPane.showInputDialog(canvas, "Please input the Port number", "Port", JOptionPane.PLAIN_MESSAGE));
		String team = JOptionPane.showInputDialog(canvas, "Please input your team name", "Team", JOptionPane.PLAIN_MESSAGE);
		System.out.println("Connecting to "+IP+":"+port);
		
		try{
			s = new Socket(IP,port);
			//socket input
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			//socket output
		   	out = new PrintWriter(s.getOutputStream());
		   	out.println("N"+team);
		   	out.flush();
		   	
		   	
		   	do{
		   		msg = in.readLine();
		   		System.out.println(msg);
		   		if(!msg.isEmpty()){
		   			if(msg.equals("U0"))color = true;
		   			else if(msg.equals("U1"))color = false;
		   			else if(msg.equals("W1"))i = 1;
		   			else if(msg.equals("W0"))i = 0;
		   			else if(msg.equals("W2"))i = 2;
		   			else if(msg.substring(0,1).equals("B")){
		   				board.updateBoard(msg);
			   			canvas.repaint();
			   			String move = getInput();
			   			while(!board.isLegalMove(move, color)) move = getInput();
			   			board.makeMove(move, color);
			   			out.println(getInput());
			   			out.flush();
		   			}
		   			else if(!msg.equals("G")) System.out.println("Unrecognized message :"+msg);
		   		}
		   	}while(!msg.equals("G"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(i == 1) msg = "Win";
		if(i == 0) msg = "Lose";
		if(i == 2) msg = "Draw";
		if(i == -1) msg = "Lost Connection!";
		
		String[] options = {"Yes","No"};
		i = JOptionPane.showOptionDialog(canvas, "You "+msg+"! Would you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if(i == 1 || i == -1) System.exit(0);
	}
	
	private String getInput() throws InterruptedException {
		canvas.getInput();
		while(inputX < 0 && inputY < 0){
			Thread.sleep(1);
		}
		return "M"+String.valueOf(inputX)+String.valueOf(inputY);
	}

	public static void main(String[] args) throws InterruptedException {
		Reversi r = new Reversi();
		r.run();
	}

}