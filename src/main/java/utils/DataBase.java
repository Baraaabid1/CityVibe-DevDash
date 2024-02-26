
package utils;
import java.sql.*;

public class DataBase {
    private Connection conn;
    String url="jdbc:mysql://localhost:3306/cityvibe";
    String user="root";
    String pwd="";
    private static DataBase instance;
    private DataBase(){
        try {
            conn= DriverManager.getConnection(url, user, pwd);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static DataBase getInstance(){
        if(instance == null){
            instance=new DataBase();
        }
        return instance;
    }
    public Connection getconn(){
        return conn;
    }
}
