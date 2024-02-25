package models;

import java.sql.*;

public class Reclamation {
private int idR , idu ;
private String titre, contenu, type , apropo ;
private Timestamp temp;

    public Reclamation() {
    }

    public Reclamation(int idR, int idu, String titre, String contenu, String type, String apropo, Timestamp temp) {
        this.idR = idR;
        this.idu = idu;
        this.titre = titre;
        this.contenu = contenu;
        this.type = type;
        this.apropo = apropo;
        this.temp = temp;
    }

    public Reclamation(int idu, String titre, String contenu, String type, String apropo, Timestamp temp) {
        this.idu = idu;
        this.titre = titre;
        this.contenu = contenu;
        this.type = type;
        this.apropo = apropo;
        this.temp = temp;
    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApropo() {
        return apropo;
    }

    public void setApropo(String apropo) {
        this.apropo = apropo;
    }

    public Timestamp getTemp() {
        return temp;
    }

    public void setTemp(Timestamp temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "idR=" + idR +
                ", idu=" + idu +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", type='" + type + '\'' +
                ", apropo='" + apropo + '\'' +
                ", temp=" + temp +
                '}';
    }
}


