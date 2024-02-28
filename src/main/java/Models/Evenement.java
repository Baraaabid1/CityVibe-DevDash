package Models;
import java.time.LocalDate;
import java.time.LocalTime;


public class Evenement {
    private int idE, nbrPlaces;
    private String nom , page, description;
    private LocalDate date;
    private LocalTime heure;

    private CategorieE categorie;
    private String photo;
    private Page page1;
    public Evenement() {
    }

    public Evenement(int idE) {

    }

    public Evenement(int idE, int nbrPlaces, String nom, String page, String description, LocalDate date, LocalTime heure, CategorieE categorie, String photo, Models.Page page1) {
        this.idE = idE;
        this.nbrPlaces = nbrPlaces;
        this.nom = nom;
        this.page = page;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.categorie = categorie;
        this.photo = photo;
        this.page1 = page1;
    }

    public Evenement(int nbrPlaces, String nom, String page, String description, LocalDate date, LocalTime heure, CategorieE categorie, String photo, Page page1) {
        this.nbrPlaces = nbrPlaces;
        this.nom = nom;
        this.page = page;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.categorie = categorie;
        this.photo = photo;
        this.page1 = page1;
    }

    public Evenement(int idE, int nbrPlaces, String nom, String page, String description, LocalDate date, LocalTime heure, CategorieE categorie, String photo) {
        this.idE = idE;
        this.nbrPlaces = nbrPlaces;
        this.nom = nom;
        this.page = page;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.categorie = categorie;
        this.photo=photo;
    }


    public Evenement(int nbrPlaces, String nom, String page, String description, LocalDate date, LocalTime heure, CategorieE categorie, String photo) {
        this.nbrPlaces = nbrPlaces;
        this.nom = nom;
        this.page = page;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.categorie = categorie;
        this.photo=photo;
    }

    public int getIdE() {
        return idE;
    }

    public void setIdE(int idE) {
        this.idE = idE;
    }

    public int getNbrPlaces() {
        return nbrPlaces;
    }

    public void setNbrPlaces(int nbrPlaces) {
        this.nbrPlaces = nbrPlaces;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public CategorieE getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieE categorie) {
        this.categorie = categorie;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Page getPage1() {
        return page1;
    }

    public void setPage1(Page page1) {
        this.page1 = page1;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "idE=" + idE +
                ", nbrPlaces=" + nbrPlaces +
                ", nom='" + nom + '\'' +
                ", page='" + page + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", heure=" + heure +
                ", categorie=" + categorie +
                ", photo='" + photo + '\'' +
                '}';
    }
}
