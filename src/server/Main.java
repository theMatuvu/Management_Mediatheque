package server;

public class Main {
    private final static int PORT = 1234;
    public static void main(String[] args) {
        try {
            new Thread(new Server(PORT)).start();
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion : " + e);
        }
    }
}