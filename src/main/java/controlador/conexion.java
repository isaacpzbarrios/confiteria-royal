/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author isaac
 */
public class conexion {
    Connection con;
    
    public conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://batsnwuvxlaybyok6s9u-mysql.services.clever-cloud.com/batsnwuvxlaybyok6s9u", "usrspr27zjgjto2d", "fewUHaOnfmU9xEYZfC3H");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public Connection getConnection(){
        return con;
    }
    
}
