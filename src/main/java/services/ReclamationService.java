package services;

import models.Reclamation;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService implements IService<Reclamation> {

    private Connection connection;

    public ReclamationService(){

        connection= MyDataBase.getInstance().getConn();
    }
    @Override
    public void ajouter(Reclamation reclamation) throws SQLException {
        String req = "INSERT INTO reclamation (idR, idu, temp, titreR, contenu, typeR, apropo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, reclamation.getIdR());
        preparedStatement.setInt(2, reclamation.getIdu());
        preparedStatement.setTimestamp(3, reclamation.getTemp());
        preparedStatement.setString(4, reclamation.getTitre());
        preparedStatement.setString(5, reclamation.getContenu());
        preparedStatement.setString(6, reclamation.getType());
        preparedStatement.setString(7, reclamation.getApropo());

        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Reclamation reclamation) throws SQLException {
        String req = "UPDATE reclamation SET idu=?, temp=?, titreR=?, contenu=?, typeR=?, apropo=? WHERE idR=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, reclamation.getIdu());
        ps.setTimestamp(2, reclamation.getTemp());
        ps.setString(3, reclamation.getTitre());
        ps.setString(4, reclamation.getContenu());
        ps.setString(5, reclamation.getType());
        ps.setString(6, reclamation.getApropo());
        ps.setInt(7, reclamation.getIdR());
        ps.executeUpdate();
        ps.close();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM reclamation WHERE idR=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();

    }

    @Override
    public List<Reclamation> afficher() throws SQLException {
        String req = "SELECT * FROM reclamation";
        List<Reclamation> reclamations = new ArrayList<>();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Reclamation reclamation = new Reclamation();
            reclamation.setIdR(rs.getInt("idR"));
            reclamation.setIdu(rs.getInt("idu"));
            reclamation.setTemp(rs.getTimestamp("temp"));
            reclamation.setTitre(rs.getString("titreR"));
            reclamation.setContenu(rs.getString("contenu"));
            reclamation.setType(rs.getString("typeR"));
            reclamation.setApropo(rs.getString("apropo"));

            reclamations.add(reclamation);
        }
        rs.close();
        st.close();
        return reclamations;
    }

    public Reclamation afficherR(int id) throws SQLException {
        String sql = "SELECT idu, temp, titreR, contenu, typeR, apropo FROM reclamation WHERE idR = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int idu = resultSet.getInt("idu");
            Timestamp temp = resultSet.getTimestamp("temp");
            String titre = resultSet.getString("titreR");
            String contenu = resultSet.getString("contenu");
            String type = resultSet.getString("typeR");
            String apropo = resultSet.getString("apropo");
            return new Reclamation(id, idu, titre, contenu, type, apropo, temp);
        }
        resultSet.close();
        statement.close();

        return null;
    }
    public List<Reclamation> MesReclamations(int idu) throws SQLException {
        String req = "SELECT * FROM reclamation WHERE idu = ?";
        List<Reclamation> reclamations = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, idu);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Reclamation reclamation = new Reclamation();
            reclamation.setIdR(rs.getInt("idR"));
            reclamation.setIdu(rs.getInt("idu"));
            reclamation.setTemp(rs.getTimestamp("temp"));
            reclamation.setTitre(rs.getString("titreR"));
            reclamation.setContenu(rs.getString("contenu"));
            reclamation.setType(rs.getString("typeR"));
            reclamation.setApropo(rs.getString("apropo"));

            reclamations.add(reclamation);
        }
        rs.close();
        ps.close();
        return reclamations;
    }


}
