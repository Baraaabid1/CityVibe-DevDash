package models;

//import jdk.vm.ci.meta.Local;

public class publication {
    //id publication
    private int idPub;
    private page page;
    private String description;
    private String image;
    private String nom;

    public publication() {
    }

    public publication(int idPub, models.page page, String description, String image, String nom) {
        this.idPub = idPub;
        this.page = page;
        this.description = description;
        this.image = image;
        this.nom = nom;

    }

    public publication(models.page page, String description, String image, String nom) {
        this.page = page;
        this.description = description;
        this.image = image;
        this.nom = nom;

    }



    public publication(int idPub, String description , String image , String nom) {
        this.idPub = idPub;
        this.description = description;
        this.image = image;
        this.nom = nom;
    }

    public publication( String description , String image, String nom) {
        this.description = description;
        this.image = image;
        this.nom = nom;
    }

    public int getIdPub() {
        return idPub;
    }

    public void setIdPub(int idPub) {
        this.idPub = idPub;
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



    public models.page getPage() {
        return page;
    }

    public void setPage(models.page page) {
        this.page = page;
    }



        // Constructor, getters, setters, and other methods...






    @Override
    public String toString() {
        return "publication{" +
                "idPub=" + idPub +
                "page=" + page +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }


    public String getTitle() {
        return nom;
    }


}
