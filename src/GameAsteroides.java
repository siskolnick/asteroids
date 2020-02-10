package ClasesAsteroides;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.event.*;
public class GameAsteroides {
	public static void main(String[]args){
		
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice gd = ge.getDefaultScreenDevice();
		MenuAsteroides ventanaSecundaria = new MenuAsteroides(new JFrame("Asteroides 2.0"),"Asteroides 2.0",gd);
		ventanaSecundaria.pack();
		ventanaSecundaria.setSize(680 , 480);
        if(!gd.isFullScreenSupported()){
        	System.out.println("WARNING: No hay soporte.\n");
			ventanaSecundaria.setSize(680 , 480);
			ventanaSecundaria.setVisible(true);
        }
        try {		
			gd.setFullScreenWindow(ventanaSecundaria);
		}catch(Exception ex){
		 	System.out.println(ex);
		}	
		
	}
}