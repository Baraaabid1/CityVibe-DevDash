package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Preference;
import utiles.MyDataBase;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PreferenceService implements IService<Preference> {
    Connection connection;

    public PreferenceService(){
        connection= MyDataBase.getInstance().getConn();}

    @Override
    public void ajouter(Preference preference) throws SQLException, IOException, MessagingException {
        String insertQuery = "INSERT INTO preferences (idu, types) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setInt(1, preference.getIdP());
            pstmt.setString(2, preference.getType());
            pstmt.executeUpdate();
        }

    }


    @Override
    public void modifier(Preference preference) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String preferencesReq = "DELETE FROM preferences WHERE idP=? ";
        try (PreparedStatement prefStmt = connection.prepareStatement(preferencesReq)) {
            prefStmt.setInt(1, id);
            prefStmt.executeUpdate();
        }
    }

    public int getPreferenceId(String preferenceName, int idu) throws SQLException {
        int preferenceId = -1;
        String query = "SELECT idP FROM preferences WHERE types = ? and idU= ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, preferenceName);
            pstmt.setString(2, String.valueOf(idu));

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    preferenceId = resultSet.getInt("idP");
                }
            }
        }
        return preferenceId;
    }


    public ObservableList<String> afficheParUser(int idU) throws SQLException {
        String prefQuery = "SELECT types FROM preferences WHERE idu=?";
        try (PreparedStatement prefStatement = connection.prepareStatement(prefQuery)) {
            prefStatement.setInt(1, idU);
            try (ResultSet prefResultSet = prefStatement.executeQuery()) {
                // Créer une liste pour stocker les préférences
                ObservableList<String> preferences = FXCollections.observableArrayList();
                // Ajouter les préférences à la liste
                while (prefResultSet.next()) {
                    String preference = prefResultSet.getString("types");
                    preferences.add(preference);
                }
                return preferences;
            }
        }
    }


    @Override
            public List afficher (){
                return null;
            }
        }

