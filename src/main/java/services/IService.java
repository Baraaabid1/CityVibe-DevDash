package services;

import models.Preference;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IService<T>{
    public void ajouter(T t) throws SQLException, IOException, MessagingException;

    public void modifier(T t) throws SQLException;

    public void supprimer(int id) throws SQLException;

    public List<T> afficher() throws SQLException;

}
