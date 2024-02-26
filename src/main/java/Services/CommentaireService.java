package Services;

import Models.Commentaire;
import Utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService {
    Connection cnx;

    public CommentaireService() {
        cnx = MyDataBase.getInstance().getconn();
    }


    public void ajouter(Commentaire t) {
        try {
            String req = "insert into commentaire(Contenu, idE, idU, DateCreation) values (?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getContenu());
            ps.setInt(2, t.getIdE());
            ps.setInt(3, t.getIdU());
            ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));            ps.executeUpdate();
            System.out.println("Commentaire ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du commentaire : " + ex.getMessage());
        }
    }

    public void modifier(Commentaire t) {
        try {
            String req = "update commentaire set contenu=?, DateCreation=? where idC=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getContenu());
            ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));            ps.setInt(3, t.getIdC());
            ps.executeUpdate();
            System.out.println("Commentaire modifié avec succès");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du commentaire : " + ex.getMessage());
        }
    }




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


    public List<Commentaire> recuperer(int ide) {
        List<Commentaire> comments = new ArrayList<>();
        try {
            String req = "select * from commentaire where idE=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, ide);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Commentaire c = new Commentaire();
                c.setIdC(rs.getInt("idC"));
                c.setContenu(rs.getString("contenu"));
                c.setIdE(rs.getInt("idE"));
                c.setIdU(rs.getInt("idU"));
                c.setDate(rs.getDate("DateCreation"));
                comments.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des commentaires : " + ex.getMessage());
        }
        return comments;
    }

    public Commentaire detail(int id) {
        Commentaire c = new Commentaire();
        try {
            String req = "select * from commentaire where idC=?";
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
    }
    }

/*public boolean userPost(int idC, int idU) {
    List<Commentaire> posts = new ArrayList<>();
    try {
        String req = "select * from commentaire where idU=? and idC=?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idU);
        ps.setInt(2, idC);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Commentaire p = new Commentaire();
            p.setIdC(rs.getInt("idC"));
            posts.add(p);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la vérification du post de l'utilisateur : " + ex.getMessage());
    }
    return !posts.isEmpty();
}

    /*public User OneUser(int idu) {
        User u = new User();
        try {
            String req = "select * from user where id= "+idu;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                u.setCIN(rs.getInt("cin"));
                u.setAdresse(rs.getString("adresse"));
                u.setEmail(rs.getString("email"));
                u.setId(idu);
                u.setNumero(rs.getInt("numero"));
                u.setRoles(rs.getString("roles"));
                u.setUserName(rs.getString("user_name"));
                System.out.println(u);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u ;
    }*/





