package ClasesAsteroides;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class Record{
	public void generaRecord(String query,Graphics g,boolean update){
		String[] modelo;
		Object[] obs;
		String cad;
		Connection connection;
		Statement statement;
		ResultSetMetaData metaData;
		ResultSet resultSet;
	//	Dimension d = new Dimension();
	//	d = getSize();
	//	int largo = d.width;
	//	int ancho = d.height;
		try{
			cad="sun.jdbc.odbc.JdbcOdbcDriver";
			Class.forName(cad);//.newInstance();
			
			// AQUI PON UNA VARIABLE PA LA RUTA
			cad="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=D:/records.mdb";

			connection=DriverManager.getConnection(cad,"","");
			statement = connection.createStatement();
			
			cad=query;
			if(!update) {
				resultSet = statement.executeQuery(cad); 
				metaData = resultSet.getMetaData();
				int sizeCol = metaData.getColumnCount();
				int i;
				obs = new Object[sizeCol];
				g.setFont( new Font( "SansSerif", Font.BOLD,22 ) );
				g.setColor(Color.white);
				int xcor = 500-200;
				for(i=1; i<=sizeCol; i++){
					g.drawString(metaData.getColumnName(i),xcor,250);
					xcor+=180;
				}
				
				g.setFont( new Font( "SansSerif", Font.BOLD,20 ) );
				int ycor = 300;
				int n=1;
				while(resultSet.next()){								
	    				xcor = 500-200;
	    				g.drawString(""+n+".",xcor,ycor);
	    				xcor+=20;
	    				for(i=0; i<sizeCol; i++){
	        				g.drawString(resultSet.getString(i+1),xcor,ycor);
	        				xcor+=150;
	        			}
	        			ycor+=50;
	        			n++;
	        			if(n>5){
	        				break;
	        			}
	 			}     
	 			resultSet.close();
	 		
	 		}
			else{
					statement.executeUpdate(cad);  // PARA SELECCION
					//resultSet = null;
			}

			statement.close();
			connection.close();
			
		}catch( ClassNotFoundException e ) { e.printStackTrace();  }
		 catch (SQLException e) { e.printStackTrace();  }		
	}
}