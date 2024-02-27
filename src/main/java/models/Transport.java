package models;

import java.sql.Time;

public class Transport {
    private int idT;
    private typeT typeT;
    private String station_depart;
    private String station_arrive;
    private Time temp_depart;
    private Time temp_arrive;
    private AvisTransport avisTransport;
    private String avis;
    private int idAvis;

    // Default constructor
    public Transport() {

    }
    public static void uneMethodeStatique() {
        Transport transport = new Transport();
        transport.setAvis("Votre avis ici");
    }

    // Parameterized constructor with id
    public Transport(int idT, models.typeT typeT, String station_depart, String station_arrive, Time temp_depart, Time temp_arrive) {
        this.idT = idT;
        this.typeT = typeT;
        this.station_depart = station_depart;
        this.station_arrive = station_arrive;
        this.temp_depart = temp_depart;
        this.temp_arrive = temp_arrive;
    }

    // Parameterized constructor without id
    public Transport(models.typeT typeT, String station_depart, String station_arrive, Time temp_depart, Time temp_arrive) {
        this.typeT = typeT;
        this.station_depart = station_depart;
        this.station_arrive = station_arrive;
        this.temp_depart = temp_depart;
        this.temp_arrive = temp_arrive;
    }

    // Getters and Setters for avis
    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    // Getters for other attributes
    public int getIdT() {
        return idT;
    }

    public typeT getTypeT() {
        return typeT;
    }

    public String getStation_depart() {
        return station_depart;
    }

    public String getStation_arrive() {
        return station_arrive;
    }

    public Time getTemp_depart() {
        return temp_depart;
    }

    public Time getTemp_arrive() {
        return temp_arrive;
    }

    // Setters for other attributes
    public void setIdT(int idT) {
        this.idT = idT;
    }

    public void setTypeT(models.typeT typeT) {
        this.typeT = typeT;
    }



    public void setStation_depart(String station_depart) {
        this.station_depart = station_depart;
    }

    public void setStation_arrive(String station_arrive) {
        this.station_arrive = station_arrive;
    }

    public void setTemp_depart(Time temp_depart) {
        this.temp_depart = temp_depart;
    }

    public void setTemp_arrive(Time temp_arrive) {
        this.temp_arrive = temp_arrive;
    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    // toString method
    @Override
    public String toString() {
        return "Transport{" +
                "idT=" + idT +
                ", typeT=" + typeT +
                ", station_depart='" + station_depart + '\'' +
                ", station_arrive='" + station_arrive + '\'' +
                ", temp_depart=" + temp_depart +
                ", temp_arrive=" + temp_arrive +
                '}';
    }
}
