package Experiment6;

import java.io.*;
import java.util.*;
import java.net.*;

public class server {
    public static void main(String[] args) throws IOException{
        DatagramSocket socket = new DatagramSocket(1234);
        byte[] recieveData = new byte[1024];

        System.out.println("Server is running...");
        DatagramPacket recievePacket = new DatagramPacket(recieveData, recieveData.length);

        socket.receive(recievePacket);

        String sentence = new String(recievePacket.getData(), 0, recievePacket.getLength());
        System.out.println("Received sentence: " + sentence);

        sentence = sentence.replace("hello", "hola");
        sentence = sentence.replace("tbh", "to be honest");
        sentence = sentence.replace("idk", "I don't know");
        sentence = sentence.replace("brb", "be right back");
        sentence = sentence.replace("omg", "oh my god");
        System.out.println("Modified sentence: " + sentence);

        byte[] sendData = sentence.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, recievePacket.getAddress(), recievePacket.getPort());
        socket.send(sendPacket);
        System.out.println("Modified sentence sent back to client.");

        socket.close();
    }
}
