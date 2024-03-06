
package services;
import models.page;
import models.publication;
import utils.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class publicationService implements IService<publication>{
    private Connection connection;

    public publicationService(){
        connection= DataBase.getInstance().getconn();
    }


    @Override
    public void ajouter(publication publication) throws SQLException {
        String req = "INSERT INTO publication (idPP ,description ,image ,nomP) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, publication.getPage().getIdP());
        preparedStatement.setString(2, publication.getDescription());
        preparedStatement.setString(3, publication.getImage());
        preparedStatement.setString(4, publication.getNom());
        preparedStatement.executeUpdate();
    }


    @Override
    public void modifier(publication publication) throws SQLException {
        String sql = "update publication set   description = ? , image  = ? , nomP = ? where id_P = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,(publication.getDescription()));
        preparedStatement.setString(2,(publication.getImage()));
        preparedStatement.setString(3,(publication.getNom()));
        preparedStatement.setInt(4, (publication.getIdPub()));

        preparedStatement.executeUpdate();
    }


//    @Override
//    public void supprimer(int id_P) throws SQLException {
//        String req = "DELETE FROM `publication` WHERE id_P=?";
//        PreparedStatement preparedStatement = connection.prepareStatement(req);
//        preparedStatement.setInt(1, id_P);
//        preparedStatement.executeUpdate();
//    }
    @Override
    public void supprimer(int id_P) throws SQLException {
        String req = "DELETE p FROM publication p INNER JOIN page pg ON p.id_P = pg.IdP WHERE p.id_P = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, id_P);
        preparedStatement.executeUpdate();
    }



    @Override

//    public List<publication> afficher() throws SQLException {
//
//        String req = "SELECT * FROM `publication`";
//        List<publication> publication = new ArrayList<>();
//        Statement st = connection.createStatement();
//        ResultSet rs = st.executeQuery(req);
//        while (rs.next()){
//            publication p = new publication();
//            p.setIdPub(rs.getInt("id_P"));
//            p.setDescription(rs.getString("description"));
//            p.setImage(rs.getString("image"));
//            p.setNom(rs.getString("nom"));
//            publication.add(p);
//        }
//        return publication;
//    }
//    public publication afficher(int id_P) throws SQLException {
//        String req = "select * from publication where id_P = ?";
//        publication p = null;
//        PreparedStatement ps = connection.prepareStatement(req);
//        ps.setInt(1, id_P);
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            p = new publication();
//            p.setIdPub(rs.getInt("id_P"));
//            p.setDescription(rs.getString("description"));
//            p.setImage(rs.getString("image"));
//            p.setNom(rs.getString("nom"));
//        }
//        return p;
//    }
       public List<publication> afficher() throws SQLException {
           String req = "select pu.*, p.nom ,p.IdP from publication pu INNER JOIN page p ON pu.idPP = p.IdP ";
           List<publication>publications=new ArrayList<>();
           PreparedStatement ps = connection.prepareStatement(req);
           ResultSet rs = ps.executeQuery();
           while (rs.next()) {
               publication pu = new publication();
               pu.setIdPub(rs.getInt("id_P"));
               page page= new page (rs.getInt("IdP"), rs.getString("nom"));
               pu.setPage(page);
               pu.setDescription(rs.getString("description"));
               pu.setImage(rs.getString("image"));
               pu.setNom(rs.getString("nomP"));
               publications.add(pu);}
           return publications;


       }

    public publication getPublicationById(int id_P) throws SQLException {
        String query = "SELECT * FROM publication WHERE id_P = ?";
        publication p = null;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id_P);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            p = new publication();
            p.setIdPub(resultSet.getInt("id_P"));
            p.setDescription(resultSet.getString("description"));
            p.setImage(resultSet.getString("image"));
            p.setNom(resultSet.getString("nomP"));
        }
        return p;
    }
    public List<publication> search(String query) throws SQLException {
        String sql = "SELECT * FROM publication WHERE description LIKE ? OR nomP LIKE ?";
        List<publication> searchResults = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + query + "%");
        preparedStatement.setString(2, "%" + query + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            publication p = new publication();
            p.setIdPub(resultSet.getInt("id_P"));
            p.setDescription(resultSet.getString("description"));
            p.setImage(resultSet.getString("image"));
            p.setNom(resultSet.getString("nomP"));
            searchResults.add(p);
        }
        return searchResults;
    }

}

