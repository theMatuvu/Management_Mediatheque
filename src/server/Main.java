package server;

import ressources.Mediatheque;

public class Main {
    private final static int[] PORT = {3000, 4000, 5000};
    public static void main(String[] args) {
        /*try {
            for (int port: PORT) {
                new Thread(new Server(port)).start();
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion : " + e);
        }*/
        Mediatheque.getInstance().toString();
    }
}