package utiles;

import java.sql.*;

public class MyDataBase {
    private Connection conn ;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    String  url = "jdbc:mysql://localhost:3306/workshop";
    String  user = "root";
    String  pwd  = "";
    public static MyDataBase instance;
    private MyDataBase()  {
        try {
            conn  = DriverManager.getConnection(url,  user, pwd);
            System.out.println("Connected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static MyDataBase getInstance(){
        if(instance==null) {
            instance = new MyDataBase();
        }
        return instance ;
    }
}