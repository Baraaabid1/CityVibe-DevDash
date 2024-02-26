package services;

import models.ReponseR;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseRService implements IService<ReponseR>{

    private Connection connection;

    public ReponseRService() {
        connection= MyDataBase.getInstance().getConn();
    }

    @Override
    public void ajouter(ReponseR reponseR) throws SQLException {
        String query = "INSERT INTO reponser (idR, idU, textR, date_repR) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reponseR.getIdR());
            preparedStatement.setInt(2, reponseR.getIdU());
            preparedStatement.setString(3, reponseR.getTextR());
            preparedStatement.setTimestamp(4, reponseR.getDate_repR());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void modifier(ReponseR reponseR) throws SQLException {
        String query = "UPDATE reponser SET idR = ?, idU = ?, textR = ?, date_repR = ? WHERE idRR = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reponseR.getIdR());
            preparedStatement.setInt(2, reponseR.getIdU());
            preparedStatement.setString(3, reponseR.getTextR());
            preparedStatement.setTimestamp(4, reponseR.getDate_repR());
            preparedStatement.setInt(5, reponseR.getIdRR());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM reponser WHERE idRR = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<ReponseR> afficher() throws SQLException {
        List<ReponseR> reponseRs = new ArrayList<>();
        String query = "SELECT idRR, idR, idU, textR, date_repR FROM reponser";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                ReponseR reponseR = new ReponseR();
                reponseR.setIdRR(resultSet.getInt("idRR"));
                reponseR.setIdR(resultSet.getInt("idR"));
                reponseR.setIdU(resultSet.getInt("idU"));
                reponseR.setTextR(resultSet.getString("textR"));
                reponseR.setDate_repR(resultSet.getTimestamp("date_repR"));
                reponseRs.add(reponseR);
            }
        }
        return reponseRs;
    }
}