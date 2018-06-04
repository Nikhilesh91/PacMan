import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class Level {
	
	public int height;
	public int width;
	
	int [][] maze = 
		{ {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			  {1,10,1,0,1,0,1,0,0,0,0,0,0,0,1},
			  {1,0,1,0,0,0,1,0,1,1,1,0,0,1,1},
			  {1,0,0,0,1,1,1,0,0,0,0,0,0,0,1},
			  {1,0,1,0,0,0,0,0,1,1,1,0,1,0,1},
			  {1,0,1,0,1,1,1,0,1,0,0,0,1,0,1},
			  {1,0,1,0,1,0,0,0,1,1,1,0,1,0,1},
			  {1,0,1,0,1,1,1,0,1,0,1,0,0,0,1},
			  {1,0,0,0,0,0,0,0,0,0,1,9,0,0,1},
			  {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			  {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			  {1,0,1,0,1,0,1,0,0,0,0,0,0,0,1},
			  {1,0,1,0,0,0,1,0,1,1,1,0,0,0,1},
			  {1,0,0,0,1,1,1,0,0,0,0,0,0,0,1},
			  {1,0,1,0,0,0,0,0,1,1,1,0,0,0,1},
			  {1,0,1,0,1,1,1,0,1,0,0,0,0,0,1},
			  {1,0,1,0,1,0,0,0,1,1,1,0,0,0,1},
			  {1,0,1,0,1,1,1,0,1,0,0,0,0,0,1},
			  {1,0,0,0,0,0,0,0,0,0,1,0,0,9,1},
			  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
		};
	
	public Tile[][] tiles;
	
	public List<Apple> apples;
	
	public Level(){
		
		apples = new ArrayList<>();
		
		this.width = maze.length;
		this.height = maze[0].length;
		int[] pixels = new int[width * height];
		tiles = new Tile[width][height];
		
		for(int xx=0;xx<maze.length;xx++){
			for(int yy=0;yy<maze[xx].length;yy++){
				
				if(maze[xx][yy] == 1){
					//Tile
					tiles[xx][yy] = new Tile(xx*32,yy*32);
				}
				else if(maze[xx][yy] == 9){
					
					Game.player1.x = xx*32;
					Game.player1.y = yy*32;
				}
				else if(maze[xx][yy] == 10){
					
					Game.player2.x = xx*32;
					Game.player2.y = yy*32;
				}
				else{
					apples.add(new Apple(xx*32,yy*32));
				}
			}
		}
	}
	
	public Level(String s){
		
		apples = new ArrayList<>();
		
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(s));
			this.width = map.getWidth();
			this.height = map.getHeight();
			int[] pixels = new int[width*height];
			tiles = new Tile[width][height];
			map.getRGB(0, 0, width, height, pixels, 0,width);
			
			for(int xx=0;xx<width;xx++){
				for(int yy=0;yy<height;yy++){
					int val = pixels[xx + (yy*width)];
					
					if(val == 0xFF000000){
						//Tile
						tiles[xx][yy] = new Tile(xx*32,yy*32);
					}
					else if(val == 0xFF0026FF){
						
						Game.player1.x = xx*32;
						Game.player1.y = yy*32;
					}
					else{
						apples.add(new Apple(xx*32,yy*32));
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void render(Graphics g){
		
		for(int x=0;x<width;x++){
			for(int y =0;y<height;y++){
				if(tiles[x][y] != null)
				tiles[x][y].render(g);
			}
		}
		
		for(int i = 0; i<apples.size();i++){
			apples.get(i).render(g);
		}
		
	}

}
