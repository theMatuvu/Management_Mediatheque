package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader consoleInput;

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 4000);
            String message, serverMessage;
            System.out.println(serverMessage=client.receiveMessage());
            while(true){
                if(client.isSocketAlive()) {

                    while ((message = client.readFromConsole()) != null) {

                        if (message.equals("exit")) {
                            System.out.println("Fermeture de la connexion");
                            client.close();
                            break;
                        }

                        client.sendMessage(message);
                        if(!client.isSocketAlive()){
                            System.out.println("Fermeture de la connexion");
                            client.close();
                            break;
                        }
                        System.out.println(serverMessage=client.receiveMessage());

                    }
                }
                else
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        consoleInput = new BufferedReader(new InputStreamReader(System.in));
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws IOException {
        String serverMessage = in.readLine().replaceAll("##", "\n");
        if (serverMessage.contains("Fin Session")){
            System.out.println("stooop de la connexion");
            this.close();
        }
        return serverMessage;
    }

    public String readFromConsole() throws IOException {
        String consoleMessage = consoleInput.readLine();
        return consoleMessage;
    }
    public boolean isSocketAlive() {
        return socket != null && socket.isConnected() && !socket.isClosed() && !socket.isInputShutdown() && !socket.isOutputShutdown();
    }

    public void close() throws IOException {
        socket.close();
    }
}