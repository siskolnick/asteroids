package ClasesAsteroides;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
public class GameWindow extends JFrame implements KeyListener{
	GraphicsDevice gd;
	MenuAsteroides menu;
	String name;
	private GamePanel pGame;
	private boolean switchStartEnd=true;
	Image cursorImage = new ImageIcon("imagenes/mirador.gif").getImage();
    Point hotspot = new Point(10, 10);
  //  boolean suspend=true;
    public GameWindow(String title, GraphicsDevice gd,MenuAsteroides menu,String name){
    	super(title);
    	this.gd = gd;
    	this.name = name;
    	this.menu = menu;
		pGame = new GamePanel(gd,menu,name);
	    pGame.setCursor(getToolkit().createCustomCursor(cursorImage, hotspot, ""));
	    pGame.startThread();
	    getContentPane().add(pGame);
	    addKeyListener(this);
	    pGame.startgame = true;
	}
   
   public void keyTyped(KeyEvent e){ }
   
	public void keyPressed(KeyEvent ev){
		char letra = ev.getKeyChar();
		if(letra=='p' || letra=='P'){
			if(pGame.suspendido){
				pGame.hiloAsteroides.suspend();
				pGame.suspendido = false;
			}
			else{
				pGame.hiloAsteroides.resume();
				pGame.suspendido = true;
			}
		}
		if((letra=='C' || letra=='c') && pGame.paused==true && pGame.mensajeLevel == true){
			pGame.restartGame();
		}
		if(letra=='s' || letra=='S'){
			Record rec = new Record();
	    	rec.generaRecord("INSERT INTO records VALUES("+pGame.total+",'"+name+"')",null,true);
	    	this.menu.showFin(pGame.total);
			this.gd.setFullScreenWindow(menu);
			
		}
	
		
		if(letra=='u' || letra=='U'){
		  
		       if(pGame.reloj>0){
				  pGame.reloj--;
				 // long a = pGame.returnSleeping();
				  pGame.changeSleeping(80);
				
				  
				  //pGame.changeSleeping( (int)a );
				}
			   
	    }	   
	
		
	}
	public void keyReleased(KeyEvent e){ }
	
	protected void windowClosed()  {
		System.exit(0);
	}
}