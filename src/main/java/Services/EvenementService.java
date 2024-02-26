package Services;

import Models.CategorieE;
import Models.Evenement;
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
        String req = "INSERT INTO evenement (nom, categorieE, date, heure,page,  description, nbrPlaces, Photo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, evenement.getNom());
        preparedStatement.setString(2, evenement.getCategorie().toString());
        preparedStatement.setDate(3, java.sql.Date.valueOf(evenement.getDate()));
        preparedStatement.setTime(4, java.sql.Time.valueOf(evenement.getHeure()));
        preparedStatement.setString(5, evenement.getPage());
        preparedStatement.setString(6, evenement.getDescription());  // Utilisation de setObject pour LocalDateTime
        preparedStatement.setInt(7, evenement.getNbrPlaces());
        preparedStatement.setString(8, evenement.getPhoto());

        preparedStatement.executeUpdate();
    }
    @Override
    public void modifier(Evenement evenement ) throws SQLException {
        String sql = "update evenement set nom= ?, CategorieE = ?,  date = ?, heure = ?,page = ?,description = ?,nbrPlaces = ? ,Photo = ? where idE = ?";

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
            String req = "select * from evenement";
            List<Evenement> evenements = new ArrayList<>();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()){
                Evenement e = new Evenement();
                e.setIdE(rs.getInt("idE"));
                e.setNom(rs.getString("nom"));
                e.setCategorie(CategorieE.valueOf(rs.getString("categorieE")));
                e.setDate(rs.getDate("date").toLocalDate());
                e.setHeure(rs.getTime("heure").toLocalTime());
                e.setPage(rs.getString("page"));
                e.setDescription(rs.getString("description"));
                e.setNbrPlaces(rs.getInt("nbrPlaces"));
                e.setPhoto(rs.getString("Photo"));
                evenements.add(e);
            }
            return evenements;
        }

}
