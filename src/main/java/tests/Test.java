
       package tests;

        import models.categorieP;
        import models.page;
        import models.publication;
        import models.typeP;
        import services.pageService;
        import services.publicationService;
        import utils.DataBase;

        import java.sql.SQLException;
        import java.time.LocalDate;
        import java.time.LocalTime;

        public class Test {

    public static void main(String [] args) {

        DataBase bd = DataBase.getInstance();

        pageService ps = new pageService();
        publicationService pub = new publicationService();
    /*    try {
            LocalTime heure = LocalTime.of(8, 0);
            ps.ajouter(new page("zayneb", 12345678, categorieP.Alimentation, "tunisie", "mauvais", heure, "https://www.google.com/search?sca_esv=32ce626ede8b9655&q=ima&tbm=isch&source=lnms&sa=X&ved=2ahUKEwiRs9nxkLuEAxU3R_EDHUNUAWYQ0pQJegQIFBAB&biw=1536&bih=738&dpr=1.25#imgrc=4GhqKTIqd_vOkM",
                    "https://www.google.com/search?sca_esv=32ce626ede8b9655&q=ima&tbm=isch&source=lnms&sa=X&ved=2ahUKEwiRs9nxkLuEAxU3R_EDHUNUAWYQ0pQJegQIFBAB&biw=1536&bih=738&dpr=1.25#imgrc=4GhqKTIqd_vOkM"));
            System.out.println("page.java ajoutée!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        try {
            LocalTime heure = LocalTime.of(9, 30);
            ps.ajouter(new page("zayneb", 12345678, categorieP.Alimentation, "tunisie", "mauvais", heure, "https://www.google.com/search?sca_esv=32ce626ede8b9655&q=ima&tbm=isch&source=lnms&sa=X&ved=2ahUKEwiRs9nxkLuEAxU3R_EDHUNUAWYQ0pQJegQIFBAB&biw=1536&bih=738&dpr=1.25#imgrc=4GhqKTIqd_vOkM",
                    "https://www.google.com/search?sca_esv=32ce626ede8b9655&q=ima&tbm=isch&source=lnms&sa=X&ved=2ahUKEwiRs9nxkLuEAxU3R_EDHUNUAWYQ0pQJegQIFBAB&biw=1536&bih=738&dpr=1.25#imgrc=4GhqKTIqd_vOkM"));
            System.out.println("page.java modifiée!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            ps.supprimer(3);
            System.out.println("page.java supprimée!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(ps.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ////////
        try {
            LocalDate date = LocalDate.of(2024, 12, 5);
            pub.ajouter(new publication("trés mauvais", date));
            System.out.println("publication.java ajoutée!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            LocalDate date = LocalDate.of(2024, 5, 25);
            pub.modifier(new publication("description", date));
            System.out.println("publication.java modifiée!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            pub.supprimer(2);
            System.out.println("publication.java supprimée!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
*/
        try {
            System.out.println(ps.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(ps.afficherP(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }}

