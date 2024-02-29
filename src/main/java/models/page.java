
package models;

import java.time.LocalTime;

public class page {

    private int IdP , contact;
    private String nom,localisation,description , image , logo;
    private LocalTime ouverture ;
    private categorieP categorie;

    public page() {}
    public page (int IdP,String nom ,int contact , categorieP categorie, String localisation ,String description,LocalTime ouverture, String image,String logo ) {
        this.IdP = IdP;
        this.nom = nom;
        this.contact = contact;
        this.categorie = categorie;
        this.localisation = localisation;
        this.description = description;
        this.ouverture=ouverture;
        this.image=image;
        this.logo=logo;

    }

    public page( String nom,int contact , categorieP categorie ,String localisation ,String description ,LocalTime ouverture, String image,String logo  )  {
        this.nom = nom;
        this.contact = contact;
        this.categorie = categorie;
        this.localisation = localisation;
        this.description = description;
        this.ouverture=ouverture;
        this.image=image;
        this.logo=logo;

    }

    public page(int idP, String nom) {
        this.IdP=idP;
        this.nom=nom;
    }

    public int getIdP() {
        return IdP;
    }

    public void setIdP(int IdP) {
        this.IdP = IdP;
    }
    public String getNom() {
        return  nom;
    }

    public void setNom(String nom) {
        this.nom=nom;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact=contact;
    }
    public categorieP getCategorie() {
        return  categorie;
    }

    public void setCategorie(categorieP categorie) {
        this.categorie =  categorie;
    }
    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public LocalTime getOuverture() {
        return ouverture;
    }

    public void setOuverture(LocalTime ouverture) {
        this.ouverture=ouverture;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image=image;
    }

    public String getLogo() {
        return  logo;
    }

    public void setLogo(String logo) {
        this.logo=logo;
    }


    @Override
    public String toString() {
        return "page{" +
                "IdP=" + IdP +
                ", nom=" + nom +
                ", contacte='" + contact + '\'' +
                ", localisation='" + localisation + '\'' +
                ", description='" + description + '\'' +
                ", categorie='" + categorie + '\'' +
                ", ouverture='" + ouverture + '\'' +
                ", logo='" +  logo+ '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    }

