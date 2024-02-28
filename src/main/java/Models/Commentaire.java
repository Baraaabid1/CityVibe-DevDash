package Models;


import java.util.Date;

public class Commentaire {
    private int idC;
    private String contenu;
    private Date date;
    private Utilisateur user;
    private Evenement evenement;

    public Commentaire() {
    }

    public Commentaire(String contenu) {
        this.contenu = contenu;
    }

    public Commentaire(int idC, String contenu, Date date, Utilisateur user, Evenement evenement) {
        this.idC = idC;
        this.contenu = contenu;
        this.date = date;
        this.user = user;
        this.evenement = evenement;
    }

    public Commentaire(String contenu, Date date, Utilisateur user, Evenement evenement) {
        this.contenu = contenu;
        this.date = date;
        this.user = user;
        this.evenement = evenement;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
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

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "idC=" + idC +
                ", contenu='" + contenu + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", evenement=" + evenement +
                '}';
    }
}
