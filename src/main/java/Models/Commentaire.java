package Models;


import java.util.Date;

public class Commentaire {
    private int idC, idE, idU;
    private String contenu;
    private Date date;

    public Commentaire() {
    }

    public Commentaire(int idC, int idE, int idU, String contenu, Date date) {
        this.idC = idC;
        this.idE = idE;
        this.idU = idU;
        this.contenu = contenu;
        this.date=date;
    }

    public Commentaire(String contenu) {
        this.contenu = contenu;
    }

    public Commentaire(int idE, int idU, String contenu , Date date) {
        this.idE = idE;
        this.idU = idU;
        this.contenu = contenu;
        this.date=date;
    }

    public Commentaire(int idC, String contenu) {
        this.idC = idC;
        this.contenu = contenu;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public int getIdE() {
        return idE;
    }

    public void setIdE(int idE) {
        this.idE = idE;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "idC=" + idC +
                ", idE=" + idE +
                ", idU=" + idU +
                ", contenu='" + contenu + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
