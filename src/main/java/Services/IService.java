package Services;

import Models.Evenement;

import java.sql.SQLException;
import java.util.List;

public interface IService <T> {
        public void ajouter(T t)throws SQLException;
        public void modifier(T t)throws SQLException;
        public void supprimer(int id)throws SQLException;
        List<Evenement> afficher() throws SQLException;
}
