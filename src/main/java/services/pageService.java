package services;

import models.categorieP;
import models.page;
import utils.DataBase;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class pageService implements IService<page> {
    private Connection connection;

    public pageService() {
        connection = DataBase.getInstance().getconn();
    }

    public page getPageById(int id) throws SQLException {
        String query = "SELECT * FROM pages WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    page page = new page();
                    page.setIdP(resultSet.getInt("IdP"));
                    page.setNom(resultSet.getString("nom"));
                    page.setContact(resultSet.getInt("contact"));
                    page.setCategorie(categorieP.valueOf(resultSet.getString("categorieP")));
                    page.setLocalisation(resultSet.getString("localisation"));
                    page.setDescription(resultSet.getString("description"));
                    page.setOuverture(resultSet.getTime("ouverture").toLocalTime());
                    page.setImage(resultSet.getString("image"));
                    page.setLogo(resultSet.getString("logo"));
                    return page;
                }
            }
        }
        return null;
    }


    @Override
    public void ajouter(page page) throws SQLException {
        String req = "INSERT INTO page (nom,contact ,categorieP,localisation , description,ouverture , image , logo) " +
                "VALUES (?, ?, ?, ?, ?, ?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, page.getNom());
        preparedStatement.setInt(2, page.getContact());
        preparedStatement.setString(3, page.getCategorie().toString());
        preparedStatement.setString(4, page.getLocalisation());
        preparedStatement.setString(5, page.getDescription());
        preparedStatement.setTime(6, java.sql.Time.valueOf(page.getOuverture()));
        preparedStatement.setString(7, page.getImage());
        preparedStatement.setString(8, page.getLogo());
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(page page) throws SQLException {
        String sql = "UPDATE page SET nom = ?, contact = ?, categorieP = ?, localisation = ?, description = ?, ouverture = ?, image = ?, logo = ? WHERE IdP = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        LocalTime ouvertureTime = page.getOuverture();
        preparedStatement.setString(1, page.getNom());
        preparedStatement.setInt(2, page.getContact());
        preparedStatement.setString(3, page.getCategorie().toString());
        preparedStatement.setString(4, page.getLocalisation());
        preparedStatement.setString(5, page.getDescription());
        preparedStatement.setObject(6, ouvertureTime);
        preparedStatement.setString(7, page.getImage());
        preparedStatement.setString(8, page.getLogo());
        preparedStatement.setInt(9, page.getIdP());

        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int IdP) throws SQLException {
        String req = "DELETE FROM `page` WHERE IdP=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, IdP);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<page> afficher() throws SQLException {
        String req = "select * from page";
        List<page> pages = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            page p = new page();
            p.setIdP(rs.getInt("IdP"));
            p.setNom(rs.getString("nom"));
            p.setContact(rs.getInt("contact"));
            p.setCategorie(categorieP.valueOf(rs.getString("categorieP")));
            p.setLocalisation(rs.getString("localisation"));
            p.setDescription(rs.getString("description"));
            p.setOuverture(rs.getTime("ouverture").toLocalTime());
            p.setImage(rs.getString("image"));
            p.setLogo(rs.getString("logo"));
            pages.add(p);
        }
        return pages;
    }

    public List<page> getAllPages() throws SQLException {
        String query = "SELECT * FROM page";
        List<page> pages = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                page page = new page();
                page.setIdP(resultSet.getInt("IdP"));
                page.setNom(resultSet.getString("nom"));
                page.setContact(resultSet.getInt("contact"));
                page.setCategorie(categorieP.valueOf(resultSet.getString("categorieP")));
                page.setLocalisation(resultSet.getString("localisation"));
                page.setDescription(resultSet.getString("description"));
                page.setOuverture(resultSet.getTime("ouverture").toLocalTime());
                page.setImage(resultSet.getString("image"));
                page.setLogo(resultSet.getString("logo"));
                pages.add(page);
            }
        }
        return pages;
    }

    public page afficherP(int id) throws SQLException {
        String req = "select * from page where IdP = ?";
        page p = null;
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            p = new page();
            p.setIdP(rs.getInt("IdP"));
            p.setNom(rs.getString("nom"));
            p.setContact(rs.getInt("contact"));
            p.setCategorie(categorieP.valueOf(rs.getString("categorieP")));
            p.setLocalisation(rs.getString("localisation"));
            p.setDescription(rs.getString("description"));
            p.setOuverture(rs.getTime("ouverture").toLocalTime());
            p.setImage(rs.getString("image"));
            p.setLogo(rs.getString("logo"));
        }
        return p;
    }
}
