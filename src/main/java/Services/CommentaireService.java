package Services;

import Models.Commentaire;
import Models.Evenement;
import Models.Utilisateur;
import Utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService implements IService<Commentaire> {
    Connection cnx;

    public CommentaireService() {
        cnx = MyDataBase.getInstance().getconn();
    }

    @Override
    public void ajouter(Commentaire t) {
        try {
            String req = "INSERT INTO commentaire(Contenu, idE, idU, DateCreation) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getContenu());
            ps.setInt(2, t.getEvenement().getIdE());
            ps.setInt(3, t.getUser().getIdu());
            ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            ps.executeUpdate();
            System.out.println("Commentaire ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du commentaire : " + ex.getMessage());
        }
    }

    @Override
    public void modifier(Commentaire t) {
        try {
            String req = "UPDATE commentaire SET contenu=?, DateCreation=? WHERE idC=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getContenu());
            ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            ps.setInt(3, t.getIdC());
            ps.executeUpdate();
            System.out.println("Commentaire modifié avec succès");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du commentaire : " + ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM commentaire WHERE idC=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Commentaire supprimé avec succès");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du commentaire : " + ex.getMessage());
        }
    }

    @Override
    public List<Commentaire> afficher() {
        return new ArrayList<>();
    }

    public List<Commentaire> recuperer(int ide) {
        List<Commentaire> comments = new ArrayList<>();
        try {
            String req = "SELECT * FROM commentaire c INNER JOIN utilisateur u ON c.idU = u.idU WHERE idE=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, ide);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Commentaire c = new Commentaire();
                c.setIdC(rs.getInt("idC"));
                c.setContenu(rs.getString("contenu"));
                c.setDate(rs.getDate("DateCreation"));

                Utilisateur user= new Utilisateur(5);
                user.setIdu(rs.getInt("idU"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                c.setUser(user);

                Evenement evenement = new Evenement();
                evenement.setIdE(rs.getInt("idE"));
                c.setEvenement(evenement);

                comments.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des commentaires : " + ex.getMessage());
        }
        return comments;
    }
    public String getNomUtilisateurById(int idU) {
        String nomUtilisateur = "";
        try {
            String req = "SELECT nom FROM utilisateur WHERE idU=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idU);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nomUtilisateur = rs.getString("nom");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération du nom de l'utilisateur : " + ex.getMessage());
        }
        return nomUtilisateur;
    }
    public String getNomEvenementById(int idE) {
        String nomEvenement = "";
        try {
            String req = "SELECT nom FROM evenement WHERE idE=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idE);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nomEvenement = rs.getString("nom");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération du nom de l'événement : " + ex.getMessage());
        }
        return nomEvenement;
    }

    /*public Commentaire detail(int id) {
        Commentaire c = new Commentaire();
        try {
            String req = "SELECT * FROM commentaire WHERE idC=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c.setIdC(rs.getInt("idC"));
                c.setContenu(rs.getString("contenu"));
                c.setIdE(rs.getInt("idE"));
                c.setIdU(rs.getInt("idU"));
                c.setDate(rs.getDate("DateCreation"));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération du commentaire : " + ex.getMessage());
        }
        return c;
    }*/
}
