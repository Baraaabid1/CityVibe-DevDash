package models;

//import jdk.vm.ci.meta.Local;

public class publication {
    //id publication
    private int id_P;

    private String description;
    private String image;
    private String nom;






    public publication() {
    }



    // le constructeur el dydy ( ajout)
//    public publication(int idPP, String description , String image , String nom) {
//        this.idPP = idPP;
//        this.description = description;
//        this.image = image;
//        this.nom = nom;
//    }
//modif /supp
//    public publication(int idP , int idPP, String description , String image , String nom) {
//        this.idP = idP;
//        this.idPP = idPP;
//        this.description = description;
//        this.image = image;
//        this.nom = nom;
//    }


    public publication(int id_P, String description , String image , String nom) {
        this.id_P = id_P;
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
        return id_P;
    }
    public void setidP(int id_P) {
        this.id_P = id_P;
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
                "id_P=" + id_P +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }



}
