package server;

import ressources.Abonne;
import ressources.DVD;
import ressources.Mediatheque;

import java.io.*;
import java.net.Socket;

public class ServiceReservation implements Runnable{
    private Socket socket;

    public ServiceReservation(Socket accept) {
        socket = accept;
    }
    @Override
    public void run() {
        try {
            System.out.println("Connexion à ServiceReservation");

            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);

            String request = socketIn.readLine();
            System.out.println("Requête reçue : " + request);

            //Traitement de la requête a faire ici !!!!
            int idDvd, idAbo;

            Mediatheque mediatheque=  Mediatheque.getInstance();
            //mediatheque.getDVD(idDvd).reservation(mediatheque.getAbonne(idAbo));

            socketOut.println("Retour confirmé : " + request);


            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
