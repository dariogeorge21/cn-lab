package Experiment6;

import java.io.*;
import java.util.*;
import java.net.*;

public class client {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a sentence: ");
        String sentence = sc.nextLine();

        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("localhost");

        byte[] data = sentence.getBytes();

        DatagramPacket packet = new DatagramPacket(data, data.length, ip, 1234);
        socket.send(packet);

        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        String translatedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

        System.out.println("Translated sentence: " + translatedSentence);

        socket.close();
        sc.close();
    }
}
