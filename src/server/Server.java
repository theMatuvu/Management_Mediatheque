package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private Socket socket;
    private ServerSocket monServeur;
    public Server(int PORT){
        try {
            monServeur = new ServerSocket (PORT);
        } catch (IOException e) {
            System.out.println("erreur lors de l'initialisation du serveur : "+ e);
        }

    }

    @Override
    public void run() {
        try {
            while(true){
                switch(socket.getPort()){
                    case 3000:
                        new Thread(new ServiceReservation(socket)).start();
                        break;
                    case 4000:
                        new Thread(new ServiceEmprunt(socket)).start();
                        break;
                    case 5000:
                        new Thread(new ServiceRetour(socket)).start();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
