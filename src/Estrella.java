package ClasesAsteroides;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Estrella
{
    private int x;
    private int y;
    private int radio;
    
    public int getX(){  return x;    }
    public void setX(int x){ this.x = x;    }
    
    public int getY(){  return y;    }
    public void setY(int y){ this.y = y;    }
    
    public int getRadio(){  return radio;    }
    public void setRadio(int radio){ this.radio = radio;    }
    
    
 	public Estrella(int r, int x, int y){
 		this.x = x;
 		this.y = y;
 		this.radio = r;
 	}
 	
 	public void draw(Graphics g){
 		g.setColor(Color.WHITE);
		g.drawOval(x, y, radio, radio);
 	}
 		
 	public void moveMent(){
			y -= 5;
 	}
 }