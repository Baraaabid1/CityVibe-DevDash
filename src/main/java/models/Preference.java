package models;

import utiles.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Preference {
    int idP;
    String type;

    public Preference() {
    }

    public Preference(int idP, String type) {
        this.idP = idP;
        this.type = type;
    }

    public Preference(String type) {
        this.type = type;
    }

    public int getIdP() {
        return idP;
    }

    public String getType() {
        return type;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Preference{" +
                "idP=" + idP +
                ", type='" + type + '\'' +
                '}';
    }

}
