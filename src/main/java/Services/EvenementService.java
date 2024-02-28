package Services;

import Models.CategorieE;
import Models.Evenement;
import Models.Page;
import Utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements IService<Evenement> {

    private Connection connection;

    public EvenementService(){
        connection= MyDataBase.getInstance().getconn();
    }

    @Override
    public void ajouter(Evenement evenement) throws SQLException {
        String req = "INSERT INTO evenement (nomE, categorieE, date, heure,page,  description, nbrPlaces, Photo, IdP) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, evenement.getNom());
        preparedStatement.setString(2, evenement.getCategorie().toString());
        preparedStatement.setDate(3, java.sql.Date.valueOf(evenement.getDate()));
        preparedStatement.setTime(4, java.sql.Time.valueOf(evenement.getHeure()));
        preparedStatement.setString(5, evenement.getPage());
        preparedStatement.setString(6, evenement.getDescription());  // Utilisation de setObject pour LocalDateTime
        preparedStatement.setInt(7, evenement.getNbrPlaces());
        preparedStatement.setString(8, evenement.getPhoto());
        preparedStatement.setInt(9, evenement.getPage1().getIdP());
        preparedStatement.executeUpdate();
    }
    @Override
    public void modifier(Evenement evenement ) throws SQLException {
        String sql = "update evenement set nomE= ?, CategorieE = ?,  date = ?, heure = ?,page = ?,description = ?,nbrPlaces = ? ,Photo = ? where idE = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, evenement.getNom());
        preparedStatement.setString(2, evenement.getCategorie().toString());
        preparedStatement.setDate(3, java.sql.Date.valueOf(evenement.getDate()));
        preparedStatement.setTime(4, java.sql.Time.valueOf(evenement.getHeure()));
        preparedStatement.setString(5, evenement.getPage());
        preparedStatement.setString(6, evenement.getDescription());  // Utilisation de setObject pour LocalDateTime
        preparedStatement.setInt(7, evenement.getNbrPlaces());
        preparedStatement.setString(8, evenement.getPhoto());
        preparedStatement.setInt(9, evenement.getIdE());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int idE) throws SQLException {

        String req = "DELETE FROM `evenement` WHERE idE=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, idE);
        preparedStatement.executeUpdate();
    }
    @Override
    public List<Evenement> afficher() throws SQLException {
        String req = "SELECT e.* ,p.nom , p.IdP FROM evenement e INNER JOIN page p ON e.IdP = p.IdP";
        List<Evenement> evenements = new ArrayList<>();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Evenement e = new Evenement();
            e.setIdE(rs.getInt("idE"));
            e.setNom(rs.getString("nomE"));
            e.setCategorie(CategorieE.valueOf(rs.getString("categorieE")));
            e.setDate(rs.getDate("date").toLocalDate());
            e.setHeure(rs.getTime("heure").toLocalTime());
            e.setPage(rs.getString("page"));
            e.setDescription(rs.getString("description"));
            e.setNbrPlaces(rs.getInt("nbrPlaces"));
            e.setPhoto(rs.getString("Photo"));
            Page page = new Page(rs.getInt("IdP"), rs.getString("nom") );
            e.setPage1(page);
            evenements.add(e);
        }
        return evenements;
    }
    public String getNomPageById(int idE) throws SQLException {
        String nomPage = null;
        String req = "SELECT page.nom FROM evenement INNER JOIN page ON evenement.IdP = page.IdP WHERE evenement.idE = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, idE);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            nomPage = rs.getString("nom");
        }

        return nomPage;
    }


}
