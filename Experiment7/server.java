package Experiment7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class server {
    public static Vector<ClientHandler> clients = new Vector<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Chat Server Started!!");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }     
}

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out; 
    private String name;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            name = in.readLine();
            if (name == null || name.trim().isEmpty()) {
                return;
            }
            
            System.out.println(name + " has joined the chat.");
            server.clients.add(this);
            broadcast(name + " has joined the chat.");

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                broadcast(name + ": " + message);                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void broadcast(String message) {
        for (ClientHandler client : server.clients) {
            if (client != this) {
                client.out.println(message);
            }
        }
    }

    private void cleanup() {
        try {
            server.clients.remove(this);
            if (name != null) {
                System.out.println(name + " has left the chat.");
                broadcast(name + " has left the chat.");
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}