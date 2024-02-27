package models;

public class AvisTransport {
    private int idAvis;
    private int idTransport;
    private String avis;
    private int note;


    public AvisTransport(int idAvis, int idTransport, String avis, int note) {
        this.idAvis = idAvis;
        this.idTransport = idTransport;
        this.avis = avis;
        this.note = note;
    }

    public AvisTransport() {
    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public int getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(int idTransport) {
        this.idTransport = idTransport;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "AvisTransport{" +
                "idAvis=" + idAvis +
                ", idTransport=" + idTransport +
                ", avis='" + avis + '\'' +
                ", note=" + note +
                '}';
    }
}
