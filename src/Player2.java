import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

public class Player2 extends Rectangle{
	
	public static int count = 0;


/*public boolean right,left,up,down;
private int speed = 2;*/

	public Player2(int x, int y) {
		setBounds(x,y,32,32);
	}



	public void tick() throws IOException{
		/*if(right && canMove(x+speed,y)){
			x+=speed;
			
		}
		if(left && canMove(x-speed,y)){
			x-=speed;
			
		}
		if(up && canMove(x,y-speed)){
			y-=speed;
			
		}
		if(down && canMove(x,y+speed)){
			y+=speed;
			
		}*/
		
		int nextx = 0;
		int nexty = 0;
		
		/*String s = Game.inPutStream.readUTF();
		String [] arr = s.split(",");
		System.out.println("Client Values: " + Integer.parseInt(arr[0]) + "," + Integer.parseInt(arr[1]));
		Game.player2.setLocation(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
		*/
		
		 
		
		Level level = Game.level;
		
		for(int i = 0; i < level.apples.size();i++){
			
			if(this.intersects(level.apples.get(i))){
				count++;
				level.apples.remove(i);
				
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
