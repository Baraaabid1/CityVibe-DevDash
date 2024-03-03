package services;

import models.Reclamation;
import models.ReponseR;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Reclamation> trierReclamationsParDateAscendante() throws SQLException {
        String req = "SELECT * FROM reclamation ORDER BY temp ASC";
        List<Reclamation> reclamations = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(req);

            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setIdR(resultSet.getInt("idR"));
                reclamation.setIdu(resultSet.getInt("idu"));
                reclamation.setTemp(resultSet.getTimestamp("temp"));
                reclamation.setTitre(resultSet.getString("titreR"));
                reclamation.setContenu(resultSet.getString("contenu"));
                reclamation.setType(resultSet.getString("typeR"));
                reclamation.setApropo(resultSet.getString("apropo"));

                reclamations.add(reclamation);
            }

        return reclamations;
    }
    public List<Reclamation> trierReclamationsParDateDescendante() throws SQLException {
        String req = "SELECT * FROM reclamation ORDER BY temp DESC";
        List<Reclamation> reclamations = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(req);

            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setIdR(resultSet.getInt("idR"));
                reclamation.setIdu(resultSet.getInt("idu"));
                reclamation.setTemp(resultSet.getTimestamp("temp"));
                reclamation.setTitre(resultSet.getString("titreR"));
                reclamation.setContenu(resultSet.getString("contenu"));
                reclamation.setType(resultSet.getString("typeR"));
                reclamation.setApropo(resultSet.getString("apropo"));

                reclamations.add(reclamation);
            }

        return reclamations;
    }
    public List<Reclamation> trierReclamationsParType(String typeReclamation) throws SQLException {
        String req = "SELECT * FROM reclamation WHERE typeR = '" + typeReclamation + "'";
        List<Reclamation> reclamations = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(req);

            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setIdR(resultSet.getInt("idR"));
                reclamation.setIdu(resultSet.getInt("idu"));
                reclamation.setTemp(resultSet.getTimestamp("temp"));
                reclamation.setTitre(resultSet.getString("titreR"));
                reclamation.setContenu(resultSet.getString("contenu"));
                reclamation.setType(resultSet.getString("typeR"));
                reclamation.setApropo(resultSet.getString("apropo"));

                reclamations.add(reclamation);
            }

        return reclamations;
    }

    public Map<String, Integer> getTypeFrequency() throws SQLException {
        String req = "SELECT typeR, COUNT(*) AS count FROM reclamation GROUP BY typeR";
        Map<String, Integer> typeFrequency = new HashMap<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                String type = rs.getString("typeR");
                int count = rs.getInt("count");
                typeFrequency.put(type, count);
            }
        }

        return typeFrequency;
    }

    public Map<String, Integer> getApropoFrequency() throws SQLException {
        String req = "SELECT apropo, COUNT(*) AS count FROM reclamation GROUP BY apropo";
        Map<String, Integer> apropoFrequency = new HashMap<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                String apropo = rs.getString("apropo");
                String category;
                if ("Autre".equals(apropo)) {
                    category = "Autre";
                } else if (apropo.startsWith("E_")) {
                    category = "Evenement";
                } else if (apropo.startsWith("P_")) {
                    category = "Page";
                } else {
                    continue;
                }

                int count = rs.getInt("count");
                apropoFrequency.put(category, apropoFrequency.getOrDefault(category, 0) + count);
            }
        }

        return apropoFrequency;
    }
    public Map<String, Integer> getEvenementFrequency() throws SQLException {
        String req = "SELECT SUBSTRING(apropo, 3) AS evenement_name, COUNT(*) AS count FROM reclamation WHERE apropo LIKE 'E\\_%' GROUP BY evenement_name";
        Map<String, Integer> evenementFrequency = new HashMap<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                String evenementName = rs.getString("evenement_name");
                int count = rs.getInt("count");
                evenementFrequency.put(evenementName, count);
            }
        }

        return evenementFrequency;
    }
    public Map<String, Integer> getPageFrequency() throws SQLException {
        String req = "SELECT SUBSTRING(apropo, 3) AS page_name, COUNT(*) AS count FROM reclamation WHERE apropo LIKE 'P\\_%' GROUP BY SUBSTRING(apropo, 3)";
        Map<String, Integer> pageFrequency = new HashMap<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                String pageName = rs.getString("page_name");
                int count = rs.getInt("count");
                pageFrequency.put(pageName, count);
            }
        }

        return pageFrequency;
    }
}




