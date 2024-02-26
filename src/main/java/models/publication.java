package models;

//import jdk.vm.ci.meta.Local;

public class publication {
    private int idP;
    private String description;
    private String image;
    private String nom;


    public publication() {
    }

    public publication(int idP, String description , String image , String nom) {
        this.idP = idP;
        this.description = description;
        this.image = image;
        this.nom = nom;
    }

    public publication( String description , String image, String nom) {
        this.description = description;
        this.image = image;
        this.nom = nom;
    }

    public int getidP() {
        return idP;
    }
    public void setidP(int idP) {
        this.idP = idP;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image=image;
    }


    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {this.nom=nom;}

    @Override
    public String toString() {
        return "publication{" +
                "idP=" + idP +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }



}
