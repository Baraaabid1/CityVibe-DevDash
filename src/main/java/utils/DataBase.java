package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DataBase {
        private Connection conn;
        public void setConn(Connection conn){
            this.conn = conn;
        }

        public Connection getConn(){
            return conn;
        }
        String url = "jdbc:mysql://localhost:3306/pidev";
        String user = "root";
        String pwd = "";
        public static DataBase instance;

        public DataBase(){

            try {
                conn = DriverManager.getConnection(url, user, pwd);
                System.out.println("connected");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        public static DataBase getInstance(){
            if(instance==null) {
                instance = new DataBase();
            }
            return instance;
        }
    }

