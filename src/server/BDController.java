package server;

import ressources.Abonne;
import ressources.DVD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BDController {
    private Connection connect;

    private final static String Driver = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/mediathèque";
    private final static String USER = "Matuvu";
    private final static String PASSWORD = "M@tT1305";

    public BDController(){
        try{
        Class.forName(Driver);
        connect = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connexion à la base de donnée réussie");
        }catch(Exception e){
            System.out.println("Erreur lors de la connexion à la base de donnée: " + e);
        }
    }

    public List<DVD> getDVDs(){
        List<DVD> DVDs = new ArrayList<>();
        try {
            Statement req = connect.createStatement();
            ResultSet res = req.executeQuery("Select * from dvds");
            while(res.next()){
                DVDs.add(new DVD(res.getInt("id"), res.getString("titre"), res.getBoolean("adulte")));
            }
            return DVDs;
        } catch (Exception e) {
            System.out.println("Erreur lors de la requête à la base de donnée: " + e);
        }
        return null;
    }
    public List<Abonne> getAbonnes(){
        List<Abonne> abonnes = new ArrayList<>();
        try {
            Statement req = connect.createStatement();
            ResultSet res = req.executeQuery("Select * from abonnés");
            while(res.next()){
                abonnes.add(new Abonne(res.getInt("id"), res.getString("Nom"), res.getDate("DateNaissance")));
            }
            return abonnes;
        } catch (Exception e) {
            System.out.println("Erreur lors de la requête à la base de donnée: " + e);
        }
        return null;
    }

}
