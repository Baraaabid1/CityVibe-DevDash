package Models;



import java.time.LocalDate;

    public class Utilisateur {
        private int idu,num_tel;
        private String nom,prenom,password,role,email,localisation;
        private LocalDate dateNaissance;
        private byte img;
        public Utilisateur(int idu){
this.idu = idu;
        }

        public Utilisateur(int idu, String nom, String prenom) {
            this.idu = idu;
            this.nom = nom;
            this.prenom = prenom;
        }

        public Utilisateur(int idu, int num_tel, String nom, String prenom, String password, String email, String localisation, LocalDate dateNaissance, String role, byte img) {
            this.idu = idu;
            this.num_tel = num_tel;
            this.nom = nom;
            this.prenom = prenom;
            this.password = password;
            this.email = email;
            this.localisation = localisation;
            this.dateNaissance = dateNaissance;
            this.role = role;
            this.img= img;
        }
        public Utilisateur(int idu, int num_tel, String nom, String prenom, String password, String email, String localisation, LocalDate dateNaissance, String role) {
            this.idu = idu;
            this.num_tel = num_tel;
            this.nom = nom;
            this.prenom = prenom;
            this.password = password;
            this.email = email;
            this.localisation = localisation;
            this.dateNaissance = dateNaissance;
            this.role = role;
        }


        public Utilisateur(int num_tel, String nom, String prenom, String password, String email, String localisation, LocalDate dateNaissance ) {
            this.num_tel = num_tel;
            this.nom = nom;
            this.prenom = prenom;
            this.password = password;
            this.email = email;
            this.localisation = localisation;
            this.dateNaissance = dateNaissance;
        }
        public Utilisateur(int num_tel, String nom, String prenom, String password, String email, String localisation, LocalDate dateNaissance, String role, byte img ) {
            this.num_tel = num_tel;
            this.nom = nom;
            this.prenom = prenom;
            this.password = password;
            this.email = email;
            this.localisation = localisation;
            this.dateNaissance = dateNaissance;
            this.role = role;
            this.img=img;
        }

        public int getIdu() {
            return idu;
        }

        public int getNum_tel() {
            return num_tel;
        }

        public String getNom() {
            return nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }

        public String getEmail() {
            return email;
        }

        public String getLocalisation() {
            return localisation;
        }

        public LocalDate getDateNaissance() {
            return LocalDate.from(dateNaissance.atStartOfDay());
        }

        public void setIdu(int idu) {
            this.idu = idu;
        }

        public void setNum_tel(int num_tel) {
            this.num_tel = num_tel;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public void setLocalisation(String localisation) {
            this.localisation = localisation;
        }

        public void setDateNaissance(LocalDate dateNaissance) {
            this.dateNaissance = dateNaissance;
        }

        @Override
        public String toString() {
            return "Utilisateur{" +
                    "idu=" + idu +
                    ", num_tel=" + num_tel +
                    ", nom='" + nom + '\'' +
                    ", prenom='" + prenom + '\'' +
                    ", password='" + password + '\'' +
                    ", email='" + email + '\'' +
                    ", localisation='" + localisation + '\'' +
                    ", dateNaissance=" + dateNaissance +'\'' +
                    ", role='" + role +
                    '}';
        }

        public byte getImg() {
            return img;
        }

        public void setImg(byte img) {
            this.img = img;
        }
    }


