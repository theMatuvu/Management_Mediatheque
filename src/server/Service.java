package server;

import java.io.*;
import java.net.Socket;

public class Service implements Runnable{
    private Socket socket;

    public Service(Socket accept) {
        socket = accept;
    }

    @Override
    public void run() {
        try {
            System.out.println("connexion");

            BufferedReader socketIn = new BufferedReader (new InputStreamReader(socket.getInputStream ()));
            PrintWriter socketOut = new PrintWriter (socket.getOutputStream(), true);
            System.setOut(new PrintStream(socket.getOutputStream()));
            String response = socketIn.readLine();

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
