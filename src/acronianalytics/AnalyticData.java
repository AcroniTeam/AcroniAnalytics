package acronianalytics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException; 
import javax.swing.JOptionPane;


public class AnalyticData {
    java.sql.Statement stmt;
    Connection con;
    
    
    public void entBanco(){
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
          //  JOptionPane.showMessageDialog(null,"Driver correto");    
        }
        catch(java.lang.ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,"Falha no driver");   
        }
        try{
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/exemplo", "acroni", "acroni7");//IMPORTANTE!!!!!!!!!
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
          //  JOptionPane.showMessageDialog(null,"Conexão feita com sucesso"); 
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro na conexão");
        }
    
    }
}
