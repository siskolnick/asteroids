package ClasesAsteroides;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MenuAsteroides extends JDialog implements ActionListener{
	JScrollPane scrollPane;
	ImageIcon icon;
	Instruccion instruct = new Instruccion("Instrucciones/instrucciones.txt");
	GraphicsDevice gd;
	Image image;
	JPanel panel;
	JPanel contenedor;
	JButton jugar;
	JButton creditos;
	JButton salir;
	JButton instrucc;
	JButton regresar;
	JButton records;
	boolean ins=false;
	boolean cred=false;
	private boolean recs = false;
	boolean msjFin = false;
	int puntaje;
	Clip inicio;
 	public MenuAsteroides(JFrame j,String title,GraphicsDevice gd){
 		super(j,title);
 		
 		
		icon = new ImageIcon("imagenes/asteroidesv2_2.jpg");
		this.gd = gd;
		panel = new JPanel()
		{
			protected void paintComponent(Graphics g)
			{
				Dimension d = getSize();
				g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
				super.paintComponent(g);
				g.setFont( new Font( "SansSerif", Font.BOLD,22 ) );
				if(cred){
					
					g.setColor(Color.GREEN);
					g.drawString("Créditos",d.width/2-50,d.height/2-100);
	               	g.drawString("DE LA FUENTE MORENO DIEGO GABRIEL 080706",d.width/2-200,d.height/2-50);
	            	g.drawString("PÉREZ COLORADO FRANCISCO JAVIER 080689",d.width/2-190,d.height/2-20);
	             	g.drawString("ROMERO IBARRA JOSÉ ALFONSO 080701",d.width/2-180,d.height/2+10);
					
				}
				
				if(ins){
					g.setFont( new Font( "SansSerif", Font.BOLD,11 ) );
					g.setColor(Color.RED);
				//	g.drawString("AQUI VA TODO EL CHORO DE LAS INSTRUCCIONES",400,300);
					instruct.escribe(g);
				}
				if(recs){
					Record rec= new Record();
					rec.generaRecord("SELECT * FROM records ORDER BY Puntos DESC",g,false);
					if(msjFin){
						g.setFont( new Font( "SansSerif", Font.BOLD,22 ) );
						g.drawString("FIN DEL JUEGO!!!!!!!! su puntuacion:" + puntaje,400,600);
					}
				}
			}
		};
		panel.setOpaque( false );
		panel.setPreferredSize( new Dimension(400, 400) );
		scrollPane = new JScrollPane( panel );
		getContentPane().add( scrollPane );
 		
 		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int largo = d.width;
		int ancho = d.height;
 		// Para poner en vertical
 		contenedor = new JPanel();
 		contenedor.setBackground(Color.black);
 		contenedor.setOpaque(false);
		contenedor.setLayout(new BoxLayout(contenedor,BoxLayout.Y_AXIS));
		
		jugar = new JButton( "JUGAR" );
		instrucc = new JButton( "INSTRUCCIONES" );
		salir = new JButton( "SALIR" );
		records = new JButton( "RECORDS" );
		regresar = new JButton( "REGRESAR" );
		creditos = new JButton( "CRÉDITOS" );
		
		Dimension minSize = new Dimension(5, ancho/3);
		Dimension prefSize = new Dimension(5, ancho/3);
		Dimension maxSize = new Dimension(Short.MAX_VALUE, ancho/3);
		contenedor.add(new Box.Filler(minSize, prefSize, maxSize));
		contenedor.add(jugar);
		
		minSize = new Dimension(5, 50);
		prefSize = new Dimension(5, 50);
		maxSize = new Dimension(Short.MAX_VALUE, 50);
		contenedor.add( new Box.Filler(minSize, prefSize, maxSize) );
		contenedor.add( creditos );
		contenedor.add(new Box.Filler(minSize, prefSize, maxSize));
		contenedor.add(instrucc);
		contenedor.add(new Box.Filler(minSize, prefSize, maxSize));
		contenedor.add(records);
		contenedor.add(new Box.Filler(minSize, prefSize, maxSize));
		contenedor.add(salir);
		contenedor.add(new Box.Filler(minSize, prefSize, maxSize));
	//	contenedor.add(new Box.Filler(minSize, prefSize, maxSize));
	//	contenedor.add(new Box.Filler(minSize, prefSize, maxSize));
		contenedor.add(regresar);
		panel.add(contenedor);

		jugar.addActionListener(this);
		instrucc.addActionListener(this);
		salir.addActionListener(this);
		regresar.addActionListener(this);
		records.addActionListener(this);
		regresar.setVisible(false);
		creditos.addActionListener(this);
		
		try {
            
        
             
            inicio = AudioSystem.getClip();
		
            inicio.open(AudioSystem.getAudioInputStream(new File("audio/INICIO.wav")));
            inicio.start();
            
            inicio.loop(Clip.LOOP_CONTINUOUSLY);
           //
          //  Thread.sleep(10000);
          
        } catch (Exception eventoINICIO){}
        
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==jugar){
			//String name = JOptionPane.showInputDialog ("Nombre de jugador"); 
			String name = JOptionPane.showInputDialog(this,"Nombre del jugador",JOptionPane.QUESTION_MESSAGE);
			GameWindow parentWindow = new GameWindow("ASTERIX 1.0.1",gd,this,name);
		    parentWindow.setUndecorated(true);
		    parentWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			parentWindow.setResizable(false);
			this.gd.setFullScreenWindow(parentWindow);
			inicio.close();
			
		}
		else if(e.getSource()==instrucc){
			ins = true;
			ocultaBotons(false);
			panel.repaint();
		}
		else if(e.getSource()==records){
			recs = true;
			ocultaBotons(false);
			panel.repaint();
		}
		else if(e.getSource()==regresar){
			msjFin = false;
			ocultaBotons(true);
			panel.repaint();
		}
		else if(e.getSource()==salir){
			windowClosed();
		}
		else if(e.getSource()== creditos){
			cred = true;
			ocultaBotons(false);
			panel.repaint();
		}
		}
	public void ocultaBotons(boolean o){
		jugar.setVisible(o);
		salir.setVisible(o);
		instrucc.setVisible(o);
		records.setVisible(o);
		creditos.setVisible(o);
		if(o){
			regresar.setVisible(false);
			ins = false;
			recs = false;
			cred = false;
		}
		else{
			regresar.setVisible(true);
		}
	}
	public void showFin(int puntajefinal){
		this.msjFin = true;
		this.recs = true;
		this.puntaje = puntajefinal;
		ocultaBotons(false);
	}
	protected void windowClosed()  {
		System.exit(0);
	}
}