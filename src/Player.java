import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

public class Player extends Rectangle{
	
	public static int count = 0;


public boolean right,left,up,down;
private int speed = 2;

	public Player(int x, int y) {
		setBounds(x,y,32,32);
	}



	public void tick() throws IOException, InterruptedException{
		if(right && canMove(x+speed,y)){
			x+=speed;
			String s = Integer.toString(x)+","+Integer.toString(y);
			System.out.println("Server Value : " + s);
			Game.outPutStream.writeUTF(s);
			Game.outPutStream.flush();
			//Game.myThread.sleep(100);
			
			}
		if(left && canMove(x-speed,y)){
			x-=speed;
			String s = Integer.toString(x)+","+Integer.toString(y);
			System.out.println("Server Value : " + s);
			Game.outPutStream.writeUTF(s);
			Game.outPutStream.flush();
			
			//Game._outDataString = Integer.toString(x)+ ","+Integer.toString(y);
		}
		if(up && canMove(x,y-speed)){
			y-=speed;
			String s = Integer.toString(x)+","+Integer.toString(y);
			System.out.println("Server Value : " + s);
			Game.outPutStream.writeUTF(s);
			Game.outPutStream.flush();
			
		}
		if(down && canMove(x,y+speed)){
			y+=speed;
			String s = Integer.toString(x)+","+Integer.toString(y);
			System.out.println("Server Value : " + s);
			Game.outPutStream.writeUTF(s);
			Game.outPutStream.flush();
			
		}
		
		Level level = Game.level;
		
		for(int i = 0; i < level.apples.size();i++){
			
			if(this.intersects(level.apples.get(i))){
				count++;
				//Game._outApples.writeUTF(Integer.toString(i));
				//Game.outPutStream.writeUTF(Integer.toString(i));
				level.apples.remove(i);
				//Game.outPutStream.writeUTF(Integer.toString(i));
				//Game.outPutStream.flush();
				if(level.apples.isEmpty())
					System.out.println("Number of apples " + count);
					//level = new Level("/map/map.png");
					//System.out.println("GAME FINISHED !!!");
				break;
				
			}
		}
		
		}
	
	private boolean canMove(int nextx, int nexty){
		
		Rectangle bounds = new Rectangle(nextx,nexty,width,height);
		Level level = Game.level;
		for(int xx = 0;xx < level.tiles.length; xx++){
			for(int yy = 0;yy < level.tiles[0].length;yy++){
				if(level.tiles[xx][yy] != null){
					if(bounds.intersects(level.tiles[xx][yy])){
						return false;
					}
					
				}
			}
		}
		
		return true;
	}
	
	
	
	public void render(Graphics g){
		
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
}
