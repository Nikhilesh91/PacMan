import java.awt.*;
import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;


public class Game extends Canvas implements Runnable,KeyListener
{
	
	
	private boolean isRunning = false;
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final String TITLE = "Pac-Man SERVER";
	public static String ipAddress= "localhost";
	public static int _intPortNumber = 5557;
	public static ServerSocket serverSocket;
	public static Socket socket;
	public static DataOutputStream outPutStream;
	public static DataInputStream inPutStream;
	
	
	
	
	public  Thread myThread;
	
	
	public static Player player1;
	public static Player2 player2;
	
	public  static Level level;
	
	public static int scorePlayer1;
	
	
	
	public synchronized void start(){
		if(isRunning)return;
		isRunning = true;
		myThread = new Thread(this);
		myThread.start();
		
	}
	
	public synchronized void stop(){
		if(!isRunning)return;
		isRunning = false;
		try {
			myThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	Game(){
		
		Dimension dimension = new Dimension(Game.WIDTH,Game.HEIGHT);
		setPreferredSize(dimension);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
		
		addKeyListener(this);
		//player1 = new Player(608,448);
		
		
		player1 = new Player(Game.WIDTH/2,Game.HEIGHT/2);
		player2 = new Player2(Game.WIDTH/2,Game.HEIGHT/2);
		//level = new Level("/map/map.png");
		level = new Level();
		
		
		
	}
	private void render() {
		
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		player1.render(g);
		player2.render(g);
		level.render(g);
		g.dispose();
		bs.show();
		
	}

	private void tick() throws IOException, InterruptedException {
		//System.out.println("In tick");
		player1.tick();
		player2.tick();
	}
	
	
	
	@Override
	public void run() {
		requestFocus();
		System.out.println("In run method");
		int fps = 0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double targetTick = 60.0;
		double delta = 0;
		double ns = 1000000000/targetTick;
		
		while(isRunning){
			
			long now = System.nanoTime();
			delta+=(now - lastTime)/ns;
			lastTime = now;
					
		//System.out.println(delta);
			
		while(delta >= 1){
			
			try {
				tick();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			render();
			fps++;
			delta--;
		}
		
		
		
		if(System.currentTimeMillis()-timer >= 1000){
			System.out.println(fps);
			fps = 0;
			timer+= 1000;
			}
		}
		
		stop();
		
		
	}
	
	public static void main(String args[]) throws IOException{
		
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setTitle(Game.TITLE);
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// connection code
		//_outDataString = new StringBuilder();
		serverSocket = new ServerSocket(_intPortNumber);
		System.out.println("Server has started");
		socket = serverSocket.accept();
		inPutStream = new DataInputStream(socket.getInputStream());
		System.out.println("Client connected to server");
		outPutStream = new DataOutputStream(socket.getOutputStream());
		
		
		
		
		//_outApples = new DataOutputStream(socket.getOutputStream());
		System.out.println("Output stream created");
		
		game.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		System.out.println("In keypressed event");
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			player1.right = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) 
			player1.left = true;
		if(e.getKeyCode() == KeyEvent.VK_UP) 
			player1.up = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) 
			player1.down = true;
		
		}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
			player1.right = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) 
			player1.left = false;
		if(e.getKeyCode() == KeyEvent.VK_UP) 
			player1.up = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) 
			player1.down = false;
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	

}
