package services;

import models.AvisTransport;
import models.Transport;
import models.typeT;
import utils.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportService implements IService <Transport> {
    private Connection connection = DataBase.getInstance().getConn();



    public TransportService() {
    }

    @Override
    public void ajouter(Transport transport) throws SQLException {
        String transportreq = "INSERT INTO transport (typeT, station_depart, station_arrive, temps_depart, temps_arrive) " +
                "VALUES('" + transport.getTypeT() + "','" + transport.getStation_depart() + "','" +
                transport.getStation_arrive() + "','" + transport.getTemp_depart() + "','" + transport.getTemp_arrive() + "')";

        Statement ste = connection.createStatement();
        ste.executeUpdate(transportreq);
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `transport` WHERE idT=?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(req);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }


    @Override
    public void modifier(Transport transport) throws SQLException {
        String transportReq = "UPDATE transport SET typeT=?, station_depart=?, station_arrive=?, temps_depart=?, temps_arrive=? WHERE idT=?";
        PreparedStatement ps = connection.prepareStatement(transportReq);
        ps.setString(1, String.valueOf(transport.getTypeT()));
        ps.setString(2, transport.getStation_depart());
        ps.setString(3, transport.getStation_arrive());
        ps.setTime(4, transport.getTemp_depart());
        ps.setTime(5, transport.getTemp_arrive());
        ps.setInt(6, transport.getIdT());
        ps.executeUpdate();
    }


    @Override
    public List<Transport> afficher() throws SQLException {
        String req = "SELECT * FROM transport";
        List<Transport> transports = new ArrayList<>();

        try (Statement st = this.connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Transport ts = new Transport();
                ts.setIdT(rs.getInt("idT"));
                ts.setTypeT(typeT.valueOf(rs.getString("typeT")));
                ts.setStation_depart(rs.getString("station_depart"));
                ts.setStation_arrive(rs.getString("station_arrive"));
                ts.setTemp_depart(rs.getTime("temps_depart"));
                ts.setTemp_arrive(rs.getTime("temps_arrive"));

                transports.add(ts);  // Ajoutez ts, pas transport
            }
        }

        return transports;
    }

    public Transport getTransportDetails(int id) throws SQLException {
        String req = "SELECT * FROM transport WHERE idT=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Transport ts = new Transport();
                    ts.setIdT(rs.getInt("idT"));
                    ts.setTypeT(typeT.valueOf(rs.getString("typeT")));
                    ts.setStation_depart(rs.getString("station_depart"));
                    ts.setStation_arrive(rs.getString("station_arrive"));
                    ts.setTemp_depart(rs.getTime("temps_depart"));
                    ts.setTemp_arrive(rs.getTime("temps_arrive"));
                    return ts;
                }
            }
        }
        return null; // Retourne null si le transport n'est pas trouvé
    }


    public List<Transport> getTransportsWithAvis(String stationDepart, String stationArrivee) throws SQLException {
        String req = "SELECT transport.*, avistransport.avis " +
                "FROM transport " +
                "LEFT JOIN avistransport ON transport.idAvis = avistransport.idAvis " +
                "WHERE transport.station_depart = ? AND transport.station_arrive = ?";

        List<Transport> transports = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, stationDepart);
            preparedStatement.setString(2, stationArrivee);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Transport transport = new Transport();
                    transport.setIdT(rs.getInt("idT"));
                    transport.setTypeT(typeT.valueOf(rs.getString("typeT")));
                    transport.setStation_depart(rs.getString("station_depart"));
                    transport.setStation_arrive(rs.getString("station_arrive"));
                    transport.setTemp_depart(rs.getTime("temps_depart"));
                    transport.setTemp_arrive(rs.getTime("temps_arrive"));

                    // Utilisez la méthode setAvis sur l'instance de transport
                    transport.setAvis(rs.getString("avis"));

                    transports.add(transport);
                }
            }
        }

        return transports;
    }
    public void ajouterAvisTransport(int idTransport, String avis, int note) throws SQLException {
        // Requête SQL pour ajouter un avis avec une référence à un transport spécifique
        String insertAvisQuery = "INSERT INTO avistransport (idTransport, avis, note) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertAvisQuery)) {
            preparedStatement.setInt(1, idTransport);
            preparedStatement.setString(2, avis);
            preparedStatement.setInt(3, note);

            // Exécutez la requête
            int rowsAffected = preparedStatement.executeUpdate();

            // Ajoutez des informations de débogage
            System.out.println("Rows affected: " + rowsAffected);
            System.out.println("Avis ajouté avec succès !");
        } catch (SQLException e) {
            // Ajoutez des informations de débogage pour l'exception
            e.printStackTrace();
            throw e; // Réthrow l'exception pour qu'elle soit gérée à un niveau supérieur si nécessaire
        }
    }










}


