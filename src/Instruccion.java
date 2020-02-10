package ClasesAsteroides;

import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;


public class Instruccion {
	
    private File archivo = null;
    private FileReader fr = null;
    private BufferedReader br = null;
    private int cx;   
    private int cy;
  
      
   public Instruccion(String file){
         archivo = new File (file);      
   		 cx =20;
   		 cy = 20;
   		
   }
   
   public void escribe(Graphics g){
   	  try {
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);
         String linea = "";
         
         while(  ( linea = br.readLine() ) != null){
         	
         		g.drawString(linea,cx,cy);
         
         	cy += 16;
         }
      
            
        
         
        //    cy += 1;
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
   
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
   	
   }
   
 
}
