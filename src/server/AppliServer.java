package server;

import ressources.Mediatheque;

import java.util.HashMap;
import java.util.Map;

public class AppliServer {
    private final static Map<Integer, Class> PORT = Map.of(3000, ServiceEmprunt.class, 4000, ServiceReservation.class, 5000, ServiceRetour.class);
    public static void main(String[] args) {
        try {
            for (Map.Entry<Integer, Class> entry: PORT.entrySet()) {
                new Thread(new Server(entry.getKey(), entry.getValue())).start();
                System.out.println("Connexion établie sur le port : " + entry.getKey());
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion : " + e);
        }
        //System.out.println(Mediatheque.getInstance().toString());
    }
}