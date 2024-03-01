
package services;
import models.categorieP;
import models.page;
import models.publication;
import utils.DataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

    public  class publicationService implements IService<publication>{
        private Connection connection;

        public publicationService(){
            connection= DataBase.getInstance().getconn();
        }


        @Override
        public void ajouter(publication publication) throws SQLException {
            String req = "INSERT INTO publication (description ,image ,nom) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setString(1, publication.getDescription());
            preparedStatement.setString(2, publication.getImage());
            preparedStatement.setString(3, publication.getNom());
            preparedStatement.executeUpdate();
        }


        @Override
        public void modifier(publication publication) throws SQLException {
            String sql = "update publication set   description = ? , image  = ? , nom  = ? where id_P = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,(publication.getDescription()));
            preparedStatement.setString(2,(publication.getImage()));
            preparedStatement.setString(3,(publication.getNom()));
            preparedStatement.setInt(4, (publication.getidP()));

            preparedStatement.executeUpdate();
        }


        @Override
        public void supprimer(int idP) throws SQLException {
<<<<<<< HEAD
            String req = "DELETE FROM `publication` WHERE id_P=?";
=======
            String req = "DELETE FROM `publication` WHERE IdP=?";
>>>>>>> 3c6b76b146eaa79534c1fc586dc86674cb956c96
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, idP);
            preparedStatement.executeUpdate();
        }
        @Override

        public List<publication> afficher() throws SQLException {

            String req = "SELECT * FROM `publication`";
            List<publication> publication = new ArrayList<>();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()){
                publication p = new publication();
                p.setidP(rs.getInt("id_P"));
                p.setDescription(rs.getString("description"));
                p.setImage(rs.getString("image"));
                p.setNom(rs.getString("nom"));
                publication.add(p);
            }
            return publication;
        }
        public publication afficher(int id) throws SQLException {
            String req = "select * from publication where idP = ?";
            publication p = null;
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new publication();
                p.setidP(rs.getInt("idP"));
                p.setDescription(rs.getString("description"));
                p.setImage(rs.getString("image"));
                p.setNom(rs.getString("nom"));
            }
            return p;
        }


        public publication getPublicationById(int id) throws SQLException {
            String query = "SELECT * FROM publication WHERE idP = ?";
            publication p = null;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                p = new publication();
                p.setidP(resultSet.getInt("idP"));
                p.setDescription(resultSet.getString("description"));
                p.setImage(resultSet.getString("image"));
                p.setNom(resultSet.getString("nom"));
            }
            return p;
        }



    }

