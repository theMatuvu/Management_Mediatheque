package server.Service;

import ressources.*;
import ressources.Documents.Document;
import ressources.Documents.RetourException;

import java.io.*;
import java.net.Socket;

public class ServiceRetour implements Runnable{
    private Socket socket;
    private Document documentEnCours;

    public ServiceRetour(Socket accept) {
        socket = accept;
    }
    @Override
    public void run() {
        try {
            System.out.println("Connexion à ServiceRetour");

            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);


            socketOut.println("Veuillez renseigner le numero du document à retourner");
            String request = socketIn.readLine();
            while (documentEnCours == null) {
                documentEnCours = Mediatheque.getInstance().getDocument(Integer.parseInt(request));
                if (documentEnCours == null) {
                    socketOut.println("Document inconnu, veuillez réessayer");
                    request = socketIn.readLine();
                }

            }
            try {
                documentEnCours.retour();
                socketOut.println("Retour confirmé : " + documentEnCours + " a été retourné" + " ##Fin Session");

            } catch (RetourException e) {
                socketOut.println("Erreur lors du retour : " + e.getMessage() + " ##Fin Session");
            }

            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
