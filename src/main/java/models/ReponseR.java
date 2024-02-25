package models;

import java.sql.Timestamp;

public class ReponseR {
    private int idRR, idR,idU ;
    private String textR;
    private Timestamp date_repR;

    public ReponseR() {
    }

    public ReponseR(int idRR, int idR, int idU, String textR, Timestamp date_repR) {
        this.idRR = idRR;
        this.idR = idR;
        this.idU = idU;
        this.textR = textR;
        this.date_repR = date_repR;
    }

    public ReponseR(int idR, int idU, String textR, Timestamp date_repR) {
        this.idR = idR;
        this.idU = idU;
        this.textR = textR;
        this.date_repR = date_repR;
    }

    public int getIdRR() {
        return idRR;
    }

    public void setIdRR(int idRR) {
        this.idRR = idRR;
    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getTextR() {
        return textR;
    }

    public void setTextR(String textR) {
        this.textR = textR;
    }

    public Timestamp getDate_repR() {
        return date_repR;
    }

    public void setDate_repR(Timestamp date_repR) {
        this.date_repR = date_repR;
    }
}
