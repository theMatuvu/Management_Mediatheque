package server;

import ressources.*;

import java.io.*;
import java.net.Socket;

public class ServiceEmprunt implements Runnable{
    private Socket socket;
    private Abonne abonneEnCours;
    private Document documentEnCours;

    public ServiceEmprunt(Socket accept) {
        socket = accept;
    }

    @Override
    public void run() {
            try {
                System.out.println("Connexion à ServiceEmprunt");
                BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);

                socketOut.println("Veuillez renseigner votre numero");
                String request = socketIn.readLine();
                while (abonneEnCours == null) {
                    abonneEnCours = Mediatheque.getInstance().getAbonne(Integer.parseInt(request));
                    if (abonneEnCours == null) {
                        socketOut.println("Abonné inconnu, veuillez réessayer");
                        request = socketIn.readLine();
                    } else
                        socketOut.println("Abonné trouvé : " + abonneEnCours.getNom() + " Veuillez renseigner le numero du document à emprunter");
                }
                request = socketIn.readLine();
                while (documentEnCours == null) {
                    documentEnCours = Mediatheque.getInstance().getDocument(Integer.parseInt(request));
                    if (documentEnCours == null) {
                        socketOut.println("Document inconnu, veuillez réessayer");
                        request = socketIn.readLine();
                    }
                }
                try {
                    documentEnCours.emprunt(abonneEnCours);
                    socketOut.println("Emprunt confirmé : " + abonneEnCours.getNom() + " a emprunté " + documentEnCours + " ##Fin Session");

                } catch (EmpruntException e) {
                    socketOut.println("Erreur lors de l'emprunt : " + e.getMessage() + " ##Fin Session");
                }

                socket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }
}
