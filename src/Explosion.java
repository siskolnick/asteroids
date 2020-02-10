package ClasesAsteroides;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

public class Explosion{
	
	private String imagen;
	private int cX;
	private int cY;
//	private int ancho;
//	private int alto;
	private JButton b = new JButton();
	private ImageIcon i;
	
	/////////////////////////////////////////GETTERS/////////////////////////////////////

	public String getImagen(){ return imagen;	}
	public int getCX(){ return cX;	}
	public int getCY(){ return cY;	}
//	public int getAncho(){ return ancho; }
//	public int getAlto(){ return alto;	}
	
	/////////////////////////////////////////SETTERS/////////////////////////////////////
	
	public void setImagen(String imagen){ this.imagen = imagen;	}
	public void setCX(int cX){ this.cX = cX;	}
	public void setCY(int cY){ this.cY = cY;	}
//	public void setAncho(int ancho){ this.ancho = ancho; }
//	public void setAlto(int alto){ this.alto = alto;	}

	/////////////////////////////////////////DIBUJA/////////////////////////////////////
	
	public void dibuja(Graphics g, boolean exp){
		if(exp){
			i.paintIcon(b, g, cX, cY);
		}
	}
	
	/////////////////////////////////////////CONSTRUCTOR/////////////////////////////////////
	
	public Explosion(String imagen, int cX, int cY){
		this.imagen = imagen;
		this.cX = cX;
		this.cY = cY;
		i = new ImageIcon(imagen);		
	}
	
	//////////////////////////////////////////////////////////////////////////////
}