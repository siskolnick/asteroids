package ClasesAsteroides;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.util.Random;
public class Asteroide {
	private int posicion;
	private String imagen;
	private int cX;
	private int cY;
	private int ancho;
	private int alto;
	private int vel;
	private int valor;
	private int tipo;  // 1-chico 2-mediano etc
	//private Explosion exp;
	private JButton b = new JButton();
	private ImageIcon i;
	private JButton explo = new JButton();
	private ImageIcon exploI;
	
	/////////////////////////////////////////GETTERS/////////////////////////////////////

	public String getImagen(){ return imagen;	}
	public int getCX(){ return cX;	}
	public int getCY(){ return cY;	}
	public int getAncho(){ return ancho; }
	public int getAlto(){ return alto;	}
	public int getVel(){ return vel; }
	public int getValor(){ return valor; }
	public int getTipo(){ return tipo; }
	//public Explosion getExplosion(){ return exp;	}
/*	public void explosion(){
		this.Explosion.dibuja();
	}*/
	
	/////////////////////////////////////////SETTERS/////////////////////////////////////
	
	public void setImagen(String imagen){ this.imagen = imagen;	}
	public void setCX(int cX){ this.cX = cX;	}
	public void setCY(int cY){ this.cY = cY;	}
	public void setAncho(int ancho){ this.ancho = ancho; }
	public void setAlto(int alto){ this.alto = alto;	}
	public void setVel(int vel){ this.vel = vel; }
//	public void setExplosion(Explosion exp){ this.exp = exp; }
	
	/////////////////////////////////////////DIBUJA/////////////////////////////////////
	
	public void dibuja(Graphics g){
		//super.paint(g);
		i.paintIcon(b, g, cX, cY);
		if(tipo != 0){
		  g.drawString(String.valueOf(valor),cX+40,cY+40);
		}
	}
	
	/////////////////////////////////////////CONSTRUCTOR/////////////////////////////////////
	
	public Asteroide(String imagen, String explosion, int vel, int posicion,int tipo){
		this.imagen = imagen;
		this.vel = vel;
		this.posicion = posicion;
		asignateCoords();
//		exp = new Explosion(explosion, this.cX, this.cY);
		i = new ImageIcon(imagen);
		this.ancho = i.getIconWidth();
		this.tipo = tipo;
		switch(this.tipo){
			case 1: this.valor = 500; break;
			case 2: this.valor = 400; break;
			case 3: this.valor = 300; break;
			case 4: this.valor = 200; break;
			default: break;
		}
		exploI = new ImageIcon(explosion);
	}
	
	//////////////////////////////////////////////////////////////////////////////
	
		public void moveMent(){
		switch(this.posicion){
			case 1: this.cX+=this.vel; break;
			case 2: this.cX-=this.vel; break;
			case 3: this.cY+=this.vel; break;
			case 4: this.cY-=this.vel; break;
		}
		
	}
	public void changePosition(){
		Random s=new Random();
		this.posicion = s.nextInt(4)+1;
		asignateCoords();
	}
	public void asignateCoords(){
		Random n=new Random();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int largo = d.width-20;
		int ancho = d.height-20;
		//System.out.println(largo + " x " + ancho );
		switch(this.posicion){
			case 1: this.cX = 0;
					this.cY = n.nextInt(ancho);
					break;
			case 2: this.cX = largo;
					this.cY = n.nextInt(ancho);
					break;
			case 3: this.cX = n.nextInt(largo);
					this.cY = 0;
					break;
			case 4: this.cX = n.nextInt(largo);
					this.cY = ancho;
					break;
		}
	}
	public boolean isClicked(int x,int y){
		if(x>=cX && x<=cX+ancho && y>=cY && y<=cY+ancho){			
			return true;
		}
		return false;
	}
}