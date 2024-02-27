package services;

import models.AvisTransport;
import models.Transport;

import java.sql.SQLException;
import java.util.List;

public interface IService<T>{


    public void ajouter(Transport transport) throws SQLException;

    public void modifier(Transport transport) throws SQLException;

    public void supprimer(int id) throws SQLException;

    public List<Transport> afficher() throws SQLException;
    public List<Transport> getTransportsWithAvis(String stationDepart, String stationArrivee) throws SQLException;
    public void ajouterAvisTransport(int idTransport, String avis, int note) throws SQLException;


}