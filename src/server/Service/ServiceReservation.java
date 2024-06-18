package server;

import ressources.*;

import java.io.*;
import java.net.Socket;

public class ServiceReservation implements Runnable{
    private Socket socket;
    private Abonne abonneEnCours;
    private Document documentEnCours;

    public ServiceReservation(Socket accept) {
        socket = accept;
    }
    @Override
    public void run() {
        try {
            System.out.println("Connexion à ServiceReservation");

            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);


                StringBuilder output = new StringBuilder("Catalogue des documents :\n");
                for (Document d : Mediatheque.getInstance().getDocuments()) {
                    output.append(d.toString()).append("\n");
                }
                output.append("\nVeuillez renseigner votre numero\n");
                socketOut.println(output.toString().replaceAll("\n", "##"));
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
                    documentEnCours.reservation(abonneEnCours);
                    socketOut.println("Reservation confirmé : " + abonneEnCours.getNom() + " a reservé " + documentEnCours + " ##Fin Session");

                } catch (ReservationException e) {
                    socketOut.println("Erreur lors de la reservation : " + e.getMessage() + " ##Fin Session");
                }
                request = socketIn.readLine();

                socket.shutdownInput();
                socket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
