package ClasesAsteroides;

import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.event.*;


class GamePanel extends JPanel implements Runnable,MouseListener{
	private boolean end=true;
	Thread hiloAsteroides;
	private Asteroide chico;
	private Asteroide mediano;
	private Asteroide grande;
	private Asteroide masgrande;
	private ArrayList asteroids = new ArrayList();
	private ArrayList estrellas = new ArrayList();
	private int ancho;
	private int largo;
		private String name;
	
	private boolean exp1 = false;
	private boolean exp2 = false;
	private boolean exp3 = false;
	private boolean exp4 = false;
	private Explosion e4;
	private Explosion e3;
	private Explosion e2;
	private Explosion e1;

	private JButton bm = new JButton();
	
    private MenuAsteroides menu;
	private GraphicsDevice gd;
	
	////////INSTRUCCIONES/////
//	private Instruccion ins;
	private boolean instruc = false;
	
	////////tiempo DE BONUS/////
	private Asteroide tiempo;
	private Asteroide bomba;
	int reloj;
	int bonusExtra;
	boolean ultra;
	boolean ultraDestroy = false;
	int ultrabombas;
	////////TONOS//////////
	Clip inicio;
	Clip laser;
	Clip bang;
	// VARIABLES JUEGO
	boolean bonus = false;
	boolean mensajeCampeon = false;
	int bonusTiempo = 0;
	boolean suspendido = true;
	boolean startgame = false;//////////
	int restantes=13;
	int fugas=5;
	int vidas=3;
	int nivel=1;
	int total = 0;
	int[] totalChico = new int[2];
	int[] totalMediano = new int[2];
	int[] totalGrande = new int[2];
	int[] totalMasGrande = new int[2];
	long sleeping=120;
	
	boolean mensajeVida = false;
	boolean mensajeLevel = false;
	boolean mensajeFin = false;
	boolean normal = true;
	boolean paused=false;
	
	
	public GamePanel(GraphicsDevice gd,MenuAsteroides menu,String name){
		super.setBackground(Color.black);
		Random s = new Random();
		Random r = new Random();
		this.name = name;
		this.gd = gd;
		this.menu = menu;
		
		//Random n = new Random();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		largo = d.width;
		ancho = d.height;
		//////instrucciones//////
//		ins = new Instruccion( "instrucciones/instrucciones.txt" );
		for(int i = 0; i <= 100; i++){
			estrellas.add( new Estrella( r.nextInt(3), r.nextInt(largo)+1, r.nextInt(ancho)+1 ) );
		}
		chico = new Asteroide("imagenes/meteoro4.gif","exp1.gif",5,s.nextInt(4)+1,1);
		mediano = new Asteroide("imagenes/meteoro3.gif","exp2.gif",3,s.nextInt(4)+1,2);
		grande = new Asteroide("imagenes/meteoro2.gif","exp3.gif",2,s.nextInt(4)+1,3);
		masgrande = new Asteroide("imagenes/meteoro.gif","exp4.gif",1,s.nextInt(4)+1,4);
		////////////tiempo DE BONUS////////////
		tiempo = new Asteroide("imagenes/tiempo.gif","explotiempo.gif",3,s.nextInt(4)+1,0);
		bomba = new Asteroide("imagenes/bomba.gif","explotiempo.gif",3,s.nextInt(4)+1,0);
		reloj = 0;
		ultrabombas = 0;
		////////////////////////////////////////////////////////////
		e1 = new Explosion("imagenes/exp1.gif", 1, 1);
		e2 = new Explosion("imagenes/exp2.gif", 1, 1);
		e3 = new Explosion("imagenes/exp3.gif", 1, 1);
		e4 = new Explosion("imagenes/exp4.gif", 1, 1);
		
		
		hiloAsteroides = new Thread(this);
		asteroids.add(chico);
		asteroids.add(mediano);
		asteroids.add(grande);		
		asteroids.add(masgrande);
		asteroids.add(tiempo);
		asteroids.add(bomba);
		addMouseListener(this);
	}
	public void startThread(){
		this.end = true;
		hiloAsteroides.start();
	}
	public void finalizarThread(){ this.end = false; }
	public void changeSleeping(int sl){
		sleeping = sl;
	}
	
	public long returnSleeping(){
		return sleeping;
	}
	
	// COORDS & ASTEROID MOVE
	public boolean checaCoordenadas(Asteroide a){
		if(a.getCX()>this.getWidth()+20 || a.getCX()<-20 || a.getCY()<-20 || a.getCY()>this.getHeight()+20 ){
			//System.out.println(this.getWidth() + " " + this.getHeight());
			a.changePosition();
			return true;
		}
		return false;
	}
	public void restartGame(){
		this.restantes=13;
		this.nivel++;
		this.mensajeLevel = false;
		this.mensajeVida = false;
		this.normal = true;
		switch(this.nivel){
			case 2: changeSleeping(110); break;
			case 3: changeSleeping(100); break;
			case 4: changeSleeping(90); break;
			case 5: changeSleeping(80); break;
			case 6: changeSleeping(70); break;
			case 7: changeSleeping(60); break;
			case 8: changeSleeping(50); break;
			case 9: changeSleeping(44); break;
			case 10: changeSleeping(40); break;
			case 11: changeSleeping(36); break;
			case 12: changeSleeping(32); break;
			case 13: changeSleeping(28); break;
			case 14: changeSleeping(24); break;
			case 15: changeSleeping(20); break;
		}
		repaint();
	}
	
	/////////////// * * *  RUN * * * \\\\\\\\\\\\\\\\\\\\ 
	public void run(){
		Random r = new Random();
		while(end){
		
	    	if(startgame){
	    	
			chico.moveMent();
			grande.moveMent();
			mediano.moveMent();
			masgrande.moveMent();
			}
			
			if(bonus) tiempo.moveMent();
			if(ultra) bomba.moveMent();
			
			if( checaCoordenadas(tiempo) ) bonus = false;
			repaint();
	
			if( checaCoordenadas(bomba) ) ultra = false;
			//repaint();
			
			for(Object o: estrellas){
				Estrella e = (Estrella)o;
	        	e.moveMent();
	        	if( e.getY() <= 0 ){
	        		e.setY(ancho);
	        		e.setX( r.nextInt(largo) );
	        		e.setRadio( r.nextInt(3) );
	        	}	
	    	}
				if(checaCoordenadas(chico) || checaCoordenadas(mediano) || checaCoordenadas(grande) || checaCoordenadas(masgrande) ){		
	    			restantes--;
	    			fugas--;
	    			mensajeVida = false;
	    			mensajeLevel = false;
	    	
	    		}
	    		if(fugas==0){
	    			vidas--;
					fugas=4;
					mensajeVida = true;
					
	    		}
	    		if(restantes<=0){
	    			changeSleeping(800);
	    			mensajeLevel=true;
	    			normal = false;
	    			paused =true;
	    		}
	    		if(vidas==0){
	    			restantes=0;
	    			fugas = 0;
	    			
	    		//	mensajeFin=true;
	    			normal = false;
	    			Record rec = new Record();
	    			rec.generaRecord("INSERT INTO records VALUES("+total+",'"+name+"')",null,true);
	    			this.menu.showFin(total);
	    			end=false;
	    			this.gd.setFullScreenWindow(menu);
	    			
	    		}
	    		
	    		if(nivel==16){
	    			restantes=0;
	    			fugas = 0;
	    			
	    			mensajeCampeon=true;
	    			normal = false;
	    			Record rec = new Record();
	    			rec.generaRecord("INSERT INTO records VALUES("+total+",'"+name+"')",null,true);
	    			this.menu.showFin(total);
	    			end=false;
	    			this.gd.setFullScreenWindow(menu);
	    			
	    		}
			try{
				hiloAsteroides.sleep(sleeping);
			}catch(InterruptedException e){ }
		}
    }    
    
    /////////////* * *   PAINT  * * * \\\\\\\\\\\\\\\\\\
    public void paint(Graphics g){
    	super.paint(g);
    	if(startgame){
    		
    		g.setColor(Color.white);
    		g.setFont( new Font( "SansSerif", Font.BOLD,12 ) );
    		g.drawString("END GAME (S) ",largo-120,50);
	    	g.drawString("PAUSE (P) ",largo-105,70);
	    	
    	
    	if(normal){
			
			g.setFont( new Font( "SansSerif", Font.BOLD,14 ) );
			chico.dibuja(g);
	    	grande.dibuja(g);
	    	mediano.dibuja(g);
	    	masgrande.dibuja(g);
	    	
	    	if(bonus){
	    	   tiempo.dibuja(g);
	    	   
	    	}
	    	
	    	if(ultra){
	    	   bomba.dibuja(g);
	    	   
	    	}
	    	
	    	g.setColor(Color.white); 
	    	
	    	if(reloj > 0)   g.drawString("ALENTAR ASTEROIDES (" + reloj +") (U)",(largo/2)-100,ancho-40);
	    	
	    	e1.dibuja(g, exp1);
	    	e2.dibuja(g, exp2);
	    	e3.dibuja(g, exp3);
	    	e4.dibuja(g, exp4);	    	
	    	   	
	    	g.drawString("FUGAS :" + fugas,50,ancho-40);
	    	g.drawString("NIVEL :" + nivel,(largo/2)-250,ancho-40);
	    	g.drawString("RESTANTES :" + restantes,largo-150,ancho-40);
	    	g.drawString("VIDAS: " + vidas,50,50);
	    	
	    	
	    	JButton b = new JButton();
			ImageIcon a = new ImageIcon("imagenes/mirador.gif");
			int s = 120;
	    	for(int i=1; i<=vidas; i++){
	    		a.paintIcon(b,g,s,25);
	    		s+=30;
	    	}
	    	if(name==null){
	    		name="";
	    	}
	    	g.setFont( new Font( "SansSerif", Font.BOLD,14 ) );
	    	g.setColor(Color.GREEN);
	    	g.drawString("PLAYER: " + name + "  TOTAL :" + total,(largo/2)-90,40);
	    	g.setColor(Color.white);
	    	g.setFont( new Font( "SansSerif", Font.BOLD,12 ) );
	    
	    	
	    	if(bonusExtra == 31 && !mensajeVida){
	    		g.drawString("GANASTE UNA VIDA!!!",20,70);	
	    	}
	    	if(mensajeVida){
	    		g.drawString("PERDIO UNA VIDA!!!",20,70);
	    		
	    	}
	    	
	    	if(mensajeCampeon){
	    		g.setFont( new Font( "SansSerif", Font.BOLD,22 ) );
			    g.setColor(Color.white);
	    		g.drawString("ERES EL MEJOR DESTRUYENDO ASTEROIDES!!!",largo/2-200,ancho/2-200);
	    		g.drawString("GAME OVER",largo/2-100,ancho/2-150);
	    	    g.drawString("Presiona S para salir",largo/2-180,ancho/2+200);
	    	    g.setFont( new Font( "SansSerif", Font.BOLD,22 ) );
			    ////////////////////AQUI EMPIEZAN LOS CRÉDITOS//////////
			    g.setColor(Color.GREEN);
	    		g.drawString("Créditos",largo/2-50,ancho/2+50);
	    		g.drawString("DE LA FUENTE MORENO DIEGO GABRIEL 080706",largo/2-200,ancho/2+100);
	    	    g.drawString("PÉREZ COLORADO FRANCISCO JAVIER 080689",largo/2-190,ancho/2+130);
	    	    g.drawString("ROMERO IBARRA JOSÉ ALFONSO 080701",largo/2-180,ancho/2+160);
	    	}
	    }
    	else if(mensajeFin){
    		g.setFont( new Font( "SansSerif", Font.BOLD,22 ) );
			g.drawRect(0,0,largo,ancho);
			g.setColor(Color.white);
			g.drawString("PERDEDOR!!!!  Total puntos: " + total,largo/2-120,ancho/2-200);
			g.drawString("Presiona S para salir",largo/2-180,ancho/2+200);
    	    ////////////////////AQUI EMPIEZAN LOS CRÉDITOS//////////
		
    	}
		else if(mensajeLevel){
			g.setFont( new Font( "SansSerif", Font.BOLD,18 ) );
			g.drawRect(0,0,largo,ancho);
			g.setColor(Color.white);
			g.drawString("TOTAL PUNTOS:" + total,150,ancho/2);
			g.drawString("Presiona C para continuar" + total,150,ancho/2+50);
		}
		
	    for(Object o: estrellas){
				Estrella e = (Estrella)o;
	        	e.draw(g);	
	    }
	    g.drawRect(10,10,this.getWidth()-20,this.getHeight()-20);
	    exp1 = false;
		exp2 = false;
		exp3 = false;
		exp4 = false;
	}

    }
    
    public void mouseClicked (MouseEvent e){
    //////////SONIDO DEL LASER//////////
	
	try {
            
            laser = AudioSystem.getClip();
            
            laser.open(AudioSystem.getAudioInputStream(new File("audio/LASER.wav")));
            
            laser.start();
            
            laser.loop(0);
           //laser.close();
            
        } catch (Exception eventoLASER){}
        
	
	
	//////////AQUI ACABA EL LASER//////////
	
	
	if(suspendido){
		
    	for(Object o:asteroids){
    		Asteroide a=(Asteroide)o;
    		
    		int cx = e.getX();
    		int cy = e.getY();
    		if(a.isClicked(cx,cy)){
    		
    		laser.close();	
    			
   	//////////SONIDO DE LA EXPLOSION//////////
	
        	try {
            
                bang = AudioSystem.getClip();
                bang.open(AudioSystem.getAudioInputStream(new File("audio/BANG.wav")));
            
            
                 bang.start();
            
                 bang.loop(0);
                 //bang.close();
            
                 } catch (Exception eventoBANG) {}
	
	
	//////////AQUI ACABA LA EXPLOSION//////////
	
    			
    			
    			
    			restantes--;
		    	switch(a.getTipo()){
		    		case 1: this.totalChico[0]++; 
		    				exp4 = true;
							bonusExtra++;
							if(bonusExtra == 30){
								vidas++;
								bonusExtra++;
							}
							
				    		e4.setCX(cx-42);
				    		e4.setCY(cy-42);
				    	
		    				break;
		    		case 2: this.totalMediano[0]++; 
		    				exp3 = true;
				    		e3.setCX(cx-42);
				    		e3.setCY(cy-42);
		    				break;
		    		case 3: this.totalGrande[0]++; 
		    				exp2 = true;
		    				ultrabombas++;
		    				if( ultrabombas == 13 ){
		    					ultra = true;
		    					ultrabombas = 0;
		    				}
				    		e2.setCX(cx-42);
				    		e2.setCY(cy-42);
		    				break;
		    		case 4: this.totalMasGrande[0]++; 
							exp1 = true;
							bonusTiempo++;
							if(bonusTiempo == 15){;
								bonus = true;
								bonusTiempo=0;
							}
				    		e1.setCX(cx-42);
				    		e1.setCY(cy-42);
		    				break;
		    		default: if(a.getImagen() == "imagenes/tiempo.gif"){
		    			           bonus = false; reloj++;
		    				 }
		    				 if(a.getImagen() == "imagenes/bomba.gif"){
		    			           ultra = false;
		    			           ultraDestroy = true;
		    				 }
		    				 break;
    	         	}
    		this.total += a.getValor();
    			a.changePosition();
    			repaint();
    			break;
    	
    	}
    		
    }
    
   
    
    if(ultraDestroy ){
    	for(Object o:asteroids){
    		Asteroide a=(Asteroide)o;
    		exp1 = true;
    		exp2 = true;
    		exp3 = true;
    		exp4 = true;
    		a.changePosition();
    		this.total += a.getValor();
    	}
    	restantes -= 4;
    	ultraDestroy = false;
    }
    
    }
    
   /* if(!startgame && e.getX()> 0 && e.getX()<largo && e.getY()> 0 && e.getY()<ancho){
    	startgame = true;
    }*/
    }
    public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}