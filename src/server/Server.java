package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private Socket socket;
    private ServerSocket monServeur;
    private Class<? extends Runnable> service;
    public Server(int PORT, Class service) throws IOException {
        try {
            monServeur = new ServerSocket (PORT);
            this.service = service;
        } catch (IOException e) {
            System.out.println("erreur lors de l'initialisation du serveur : "+ e);
        }

    }

    @Override
    public void run() {
        try {
            while(true){
                Socket socket = monServeur.accept();
                new Thread(service.getConstructor(Socket.class).newInstance(socket)).start();
                System.out.println("Connexion établie sur le port : " + monServeur.getLocalPort());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
